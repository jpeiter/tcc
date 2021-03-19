package br.edu.utfpr.pb.jeanpeiter.tcc.controller.atividade;

import com.digidemic.unitof.UnitOf;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.Atividade;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.enums.AtividadeTipo;

public class AtividadePontuacao {

    public Long calcular(Atividade atividade) {
        double duracaoMinutos = new UnitOf.Time().fromSeconds(atividade.getDuracao()).toMinutes();
        double distanciaKm = new UnitOf.Length().fromMeters(atividade.getDistancia()).toKilometers();
        Long calorias = atividade.getCalorias();

        double distanciaTempo = duracaoMinutos * distanciaKm;
        Long pontuacao = (long) (distanciaTempo + calorias);
        Long multiplicador = AtividadeTipo.DUPLA.equals(atividade.getTipo()) && duracaoMinutos >= 15 ? 2L : 1L;
        return pontuacao * multiplicador;
    }


}
