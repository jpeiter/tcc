package br.edu.utfpr.pb.jeanpeiter.tcc.controller.atividade;

import com.digidemic.unitof.UnitOf;

import br.edu.utfpr.pb.jeanpeiter.tcc.utils.BigDecimalUtils;

public class AtividadeUnidadesController {

    private BigDecimalUtils bgUtils = new BigDecimalUtils();
    private UnitOf.Speed conversorVelocidade = new UnitOf.Speed();
    private UnitOf.Length conversorDistancia = new UnitOf.Length();
    private UnitOf.Time conversorTempo = new UnitOf.Time();

    public Double distancia(Double distanciaMetros) {
        double m = distanciaMetros != null ? distanciaMetros : 0;
        double distancia = m < 1000 ? m : conversorDistancia.fromMeters(m).toKilometers();
        return bgUtils.arredondado(distancia, 2).doubleValue();
    }

    public Double velocidade(Double distanciaMetros, Long duracaoMilis) {
        double m = conversorDistancia.fromMeters(distanciaMetros).toKilometers();
        double s = conversorTempo.fromMilliseconds(duracaoMilis).toSeconds();
        double kmH = conversorVelocidade.fromMetersPerSecond(m / s).toKilometersPerHour();
        return bgUtils.arredondado(kmH, 2).doubleValue();
    }

    public Double ritmo(Double distanciaMetros, Long duracaoMilis) {
        double min = conversorTempo.fromMilliseconds(duracaoMilis).toMinutes();
        double km = conversorDistancia.fromMeters(distanciaMetros).toKilometers();
        double pace = 0;
        if (min > 0 && km > 0) {
            pace = min / km;
        }
        return bgUtils.arredondado(pace, 2).doubleValue();
    }
}

