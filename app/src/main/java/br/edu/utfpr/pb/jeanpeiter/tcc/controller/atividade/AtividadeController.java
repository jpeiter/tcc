package br.edu.utfpr.pb.jeanpeiter.tcc.controller.atividade;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import java.util.List;
import java.util.UUID;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.database.AppDatabase;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.Atividade;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.enums.AtividadeEstado;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.enums.AtividadeTipo;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.posicao.AtividadePosicao;
import br.edu.utfpr.pb.jeanpeiter.tcc.sensor.localizacao.data.LocationObservedData;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.BigDecimalUtils;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.LocationUtils;
import lombok.Getter;

public class AtividadeController {

    @Getter
    private Atividade atividade;

    private LocationUtils locationUtils;
    private AtividadeUnidadesController unidadesController;
    private BigDecimalUtils bgUtils;

    private boolean permiteFinalizar;

    public AtividadeController(UUID atividadeUuid) {
        this.locationUtils = new LocationUtils();
        this.bgUtils = new BigDecimalUtils();
        this.unidadesController = new AtividadeUnidadesController();
        this.atividade = new Atividade();
        long currentTimeMillis = System.currentTimeMillis();
        this.atividade.set_id(atividadeUuid.toString());
        this.atividade.setInicio(currentTimeMillis);
        this.atividade.setDistancia(0.0);
        this.atividade.setEstado(AtividadeEstado.EM_ANDAMENTO);
    }

    public void setTipo(AtividadeTipo tipo) {
        this.atividade.setTipo(tipo);
    }

    public Atividade mudarEstado(AtividadeEstado estado) {
        this.atividade.setEstado(estado);
        return this.atividade;
    }

    public Atividade atualizar(LocationObservedData data) {
        atividade.getPosicoes().add(novaPosicao(this.atividade.getPosicoes().size(), data));
        atividade.setDistancia(distanciaEmAndamento(atividade.getDistancia(), this.atividade.getPosicoes()));
        return this.atividade;
    }

    private AtividadePosicao novaPosicao(int qtdePosicoes, LocationObservedData data) {
        Long ordem = qtdePosicoes + 1L;
        return new AtividadePosicao(ordem, data.getLocation());
    }

    private Double distanciaEmAndamento(Double distanciaTotal, List<AtividadePosicao> posicoes) {
        int qtde = posicoes.size();
        if (qtde <= 1) {
            return 0.0;
        }

        AtividadePosicao penultimaPosicao = posicoes.get(qtde - 2);
        Location penultimaLocalizacao = locationUtils.toLocation(penultimaPosicao);

        AtividadePosicao ultimaPosicao = posicoes.get(qtde - 1);
        Location ultimaLocalizacao = locationUtils.toLocation(ultimaPosicao);

        Float distanciaAtual = penultimaLocalizacao.distanceTo(ultimaLocalizacao);
        Double totalNovo = distanciaTotal + distanciaAtual;

        return bgUtils.arredondado(totalNovo, 2).doubleValue();
    }

    private Double velocidade(Double distanciaEmMetros, Long duracao) {
        return unidadesController.velocidade(distanciaEmMetros,duracao);
    }

    public Atividade finalizar(long termino, long duracaoMillis) {
        mudarEstado(AtividadeEstado.FINALIZADA);
        atividade.setTermino(termino);
        atividade.setDuracao(duracaoMillis / 1000);
        atividade.setVelocidade(velocidade(atividade.getDistancia(), atividade.getDuracao()));
        permiteFinalizar = true;
        return atividade;
    }

    public void salvar(Atividade atividade, Context context, Runnable acaoOk, Runnable acaoErro) throws Exception {
        if (!permiteFinalizar) throw new Exception("A atividade não foi finalizada ainda!");
        permiteFinalizar = false;

        AppDatabase db = AppDatabase.getInstance(context);
        AsyncTask.execute(() -> {
            try {
                db.save(atividade);
                acaoOk.run();
            } catch (Exception e) {
                if (acaoErro != null) {
                    new Handler(Looper.getMainLooper()).post(acaoErro);
                }
            }
        });

    }

}
