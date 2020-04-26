package br.edu.utfpr.pb.jeanpeiter.tcc.controller.atividade;

import android.location.Location;

import java.util.List;

import br.edu.utfpr.pb.jeanpeiter.tcc.modelo.atividade.Atividade;
import br.edu.utfpr.pb.jeanpeiter.tcc.modelo.atividade.posicao.AtividadePosicao;
import br.edu.utfpr.pb.jeanpeiter.tcc.modelo.data.LocationObservedData;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.BigDecimalUtils;

public class AtividadeController {

    private Atividade atividade;

    public AtividadeController() {
        this.atividade = new Atividade();
        this.atividade.setInicio(System.currentTimeMillis());
    }

    public Atividade atualizarAtividade(LocationObservedData data) {
        atividade.getPosicoes().add(novaPosicao(this.atividade.getPosicoes().size(), data));
        atividade.setDistancia(distanciaTotal(atividade.getDistancia(), this.atividade.getPosicoes()));
        atividade.setVelocidade(velocidade(data.getLocation()));
        return this.atividade;
    }


    private AtividadePosicao novaPosicao(int qtdePosicoes, LocationObservedData data) {
        Long ordem = qtdePosicoes + 1L;
        return new AtividadePosicao(atividade.getId(), ordem, data.getLocation());
    }

    private Double distanciaTotal(Double distanciaTotal, List<AtividadePosicao> posicoes) {
        int qtde = posicoes.size();
        if (qtde <= 1) {
            return 0.0;
        }

        AtividadePosicao penultimaPosicao = posicoes.get(qtde - 2);
        Location penultimaLocalizacao = new Location("");
        penultimaLocalizacao.setLatitude(penultimaPosicao.getLatitude());
        penultimaLocalizacao.setLongitude(penultimaPosicao.getLongitude());

        AtividadePosicao ultimaPosicao = posicoes.get(qtde - 1);
        Location ultimaLocalizacao = new Location("");
        ultimaLocalizacao.setLatitude(ultimaPosicao.getLatitude());
        ultimaLocalizacao.setLongitude(ultimaPosicao.getLongitude());

        Float distanciaAtual = penultimaLocalizacao.distanceTo(ultimaLocalizacao);
        Double totalNovo = distanciaTotal + distanciaAtual;

        return new BigDecimalUtils().arredondado(totalNovo, 2).doubleValue();

    }

    private Double velocidade(Location location) {
        Float velocidade = location.getSpeed();
        return new BigDecimalUtils().arredondado(velocidade.doubleValue(), 2).doubleValue();
    }

}
