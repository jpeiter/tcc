package br.edu.utfpr.pb.jeanpeiter.tcc.controller.atividade;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;

import java.util.List;
import java.util.UUID;

import br.edu.utfpr.pb.jeanpeiter.tcc.connectivity.info.NetworkInformation;
import br.edu.utfpr.pb.jeanpeiter.tcc.controller.firebase.FirebaseAtividadeController;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.database.AppDatabase;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.Atividade;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.posicao.AtividadePosicao;
import br.edu.utfpr.pb.jeanpeiter.tcc.sensor.localizacao.data.LocationObservedData;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.BigDecimalUtils;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.LocationUtils;

public class AtividadeController {

    private Atividade atividade;
    private LocationUtils locationUtils = new LocationUtils();
    private boolean permiteFinalizar;

    public AtividadeController() {
        this.atividade = new Atividade();
        long currentTimeMillis = System.currentTimeMillis();
        this.atividade.set_id(UUID.randomUUID().toString());
        this.atividade.setInicio(currentTimeMillis);
        this.atividade.setDistancia(0.0);
    }

    public Atividade atualizarAtividade(LocationObservedData data) {
        atividade.getPosicoes().add(novaPosicao(this.atividade.getPosicoes().size(), data));
        atividade.setDistancia(distanciaEmAndamento(atividade.getDistancia(), this.atividade.getPosicoes()));
        atividade.setVelocidade(velocidade(data.getLocation()));
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

        return new BigDecimalUtils().arredondado(totalNovo, 2).doubleValue();
    }

    private Double velocidade(Location location) {
        Float velocidade = location.getSpeed();
        return new BigDecimalUtils().arredondado(velocidade.doubleValue(), 2).doubleValue();
    }

    private Double velocidadeFinal(Atividade atividade) {
        return new BigDecimalUtils()
                .arredondado(atividade.getDistancia() / atividade.getDuracao(), 2)
                .doubleValue();
    }

    public Atividade finalizar(long termino, long duracaoMillis) {
        atividade.setTermino(termino);
        atividade.setDuracao(duracaoMillis / 1000);
        atividade.setVelocidade(velocidadeFinal(atividade));
        permiteFinalizar = true;
        return atividade;
    }

    public void salvar(Atividade atividade, Context context, Runnable acaoOk, Runnable acaoErro) throws Exception {
        if (!permiteFinalizar) throw new Exception("A atividade não foi finalizada ainda!");
        permiteFinalizar = false;

        boolean isOnline = NetworkInformation.isNetworkAvailable(context);

        if (isOnline) {
            FirebaseAtividadeController.save(atividade, acaoOk, acaoErro);
        } else {
            AppDatabase db = AppDatabase.getInstance(context);
            AsyncTask.execute(() -> {
                try {
                    db.save(atividade);
                    acaoOk.run();
                } catch (Exception e) {
                    if (acaoErro != null) acaoErro.run();
                }

            });
        }
    }


}
