package br.edu.utfpr.pb.jeanpeiter.tcc.controller.atividade;

import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.digidemic.unitof.UnitOf;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import br.edu.utfpr.pb.jeanpeiter.tcc.controller.firebase.FirebaseUserController;
import br.edu.utfpr.pb.jeanpeiter.tcc.controller.nivelprogresso.NivelProgressoController;
import br.edu.utfpr.pb.jeanpeiter.tcc.helper.NivelProgressoHelper;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.database.atividade.AtividadeDatabase;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.database.nivelprogresso.UsuarioNivelProgressoDatabase;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.Atividade;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.enums.AtividadeEstado;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.enums.AtividadeTipo;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.posicao.AtividadePosicao;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.usuario.Usuario;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.sharedpreferences.AppSharedPreferences;
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
    private Usuario usuario;

    private boolean permiteFinalizar;

    public AtividadeController(UUID atividadeUuid, AtividadeTipo tipo, Context context) {
        this.usuario = new AppSharedPreferences(context).getUsuario();
        this.locationUtils = new LocationUtils();
        this.bgUtils = new BigDecimalUtils();
        this.unidadesController = new AtividadeUnidadesController();

        this.atividade = new Atividade();
        this.atividade.set_id(atividadeUuid.toString());
        this.atividade.setUsuarioUid(FirebaseUserController.getUser().getUid());
        this.atividade.setTipo(tipo);
        this.atividade.setPosicoes(new ArrayList<>());
        this.atividade.setDistancia(0.0);
        this.atividade.setEstado(AtividadeEstado.EM_ANDAMENTO);
        this.atividade.setInicio(System.currentTimeMillis());
    }

    public Atividade mudarEstado(AtividadeEstado estado) {
        this.atividade.setEstado(estado);
        return this.atividade;
    }

    public Atividade atualizar(LocationObservedData data) {
        atividade.getPosicoes().add(novaPosicao(atividade.getPosicoes().size(), data));
        atividade.setDistancia(distanciaEmAndamento(atividade.getDistancia(), atividade.getPosicoes()));
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

    private Double velocidade(Double distanciaEmMetros, Long duracaoMillis) {
        return unidadesController.velocidadeEmKmH(distanciaEmMetros, duracaoMillis);
    }

    public Atividade finalizar(long termino, long duracaoMillis) {
        mudarEstado(AtividadeEstado.FINALIZADA);
        this.atividade.setTermino(termino);
        Double distancia = atividade.getDistancia();
        this.atividade.setDuracao((long) new UnitOf.Time().fromMilliseconds(duracaoMillis).toSeconds());
        this.atividade.setVelocidade(velocidade(distancia, duracaoMillis));
        this.atividade.setCalorias(unidadesController.calorias(usuario.getPeso(), distancia, duracaoMillis));
        this.atividade.setRitmo(unidadesController.ritmo(distancia, duracaoMillis));
        this.atividade.setPontos(getPontuacaoTotal(atividade));
        this.permiteFinalizar = true;
        return atividade;
    }

    private Long getPontuacaoTotal(Atividade atividade) {
        Long duracao = atividade.getDuracao();
        Double distancia = atividade.getDistancia();
        Long calorias = atividade.getCalorias();
        AtividadeTipo tipo = atividade.getTipo();
        return new NivelProgressoHelper().calcular(duracao, distancia, calorias, tipo);
    }

    public void salvar(Atividade atividade, Context context, Runnable acaoOk, Runnable acaoErro) throws Exception {
        if (!permiteFinalizar) throw new Exception("A atividade não foi finalizada ainda!");
        permiteFinalizar = false;

        AtividadeDatabase atividadeDb = new AtividadeDatabase(context);
        AsyncTask.execute(() -> {
            try {
                atividadeDb.save(atividade);
                new NivelProgressoController(context).save(atividade.getUsuarioUid(), atividade.getPontos());
                acaoOk.run();
            } catch (Exception e) {
                if (acaoErro != null) {
                    new Handler(Looper.getMainLooper()).post(acaoErro);
                }
            }
        });
    }

}
