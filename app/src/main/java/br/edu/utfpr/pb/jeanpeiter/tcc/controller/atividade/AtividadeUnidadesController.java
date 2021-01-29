package br.edu.utfpr.pb.jeanpeiter.tcc.controller.atividade;

import com.digidemic.unitof.UnitOf;

import java.math.BigDecimal;

import br.edu.utfpr.pb.jeanpeiter.tcc.consts.UnidadeMetabolica;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.BigDecimalUtils;

public class AtividadeUnidadesController {

    private BigDecimalUtils bgUtils = new BigDecimalUtils();
    private UnidadeMetabolica unidadeMetabolica = new UnidadeMetabolica();
    private UnitOf.Speed conversorVelocidade = new UnitOf.Speed();
    private UnitOf.Length conversorDistancia = new UnitOf.Length();
    private UnitOf.Time conversorTempo = new UnitOf.Time();

    public Double distancia(Double distanciaMetros) {
        double m = distanciaMetros != null ? distanciaMetros : 0;
        double distancia = m < 1000 ? m : conversorDistancia.fromMeters(m).toKilometers();
        return bgUtils.arredondado(distancia, 2).doubleValue();
    }

    public Double velocidadeEmKmH(Double distanciaMetros, Long duracaoMilis) {
        double m = conversorDistancia.fromMeters(distanciaMetros).toKilometers();
        double s = conversorTempo.fromMilliseconds(duracaoMilis).toSeconds();
        double kmH = conversorVelocidade.fromMetersPerSecond(m / s).toKilometersPerHour();
        return bgUtils.arredondado(kmH, 2).doubleValue();
    }

    public Double ritmo(Double distanciaMetros, Long duracaoMilis) {
        int[] minutoSegundo = ritmoMinutoSegundo(distanciaMetros, duracaoMilis);
        BigDecimal bg = bgUtils.getDecimalDeIteiroEDecimal(minutoSegundo[0], minutoSegundo[1]);
        return bgUtils.arredondado(bg.doubleValue(), 2).doubleValue();
    }

    public int[] ritmoMinutoSegundo(Double distanciaMetros, Long duracaoMilis) {
        double min = conversorTempo.fromMilliseconds(duracaoMilis).toMinutes();
        double km = conversorDistancia.fromMeters(distanciaMetros).toKilometers();
        if (min > 0 && km > 0) {
            double tempo = min / km;
            int minutos = (int) tempo;
            double segundos = tempo - minutos;
            segundos = segundos * 60 / 100;
            int sec = (int) segundos;
            return new int[]{minutos, sec};
        }
        return new int[]{0, 0};
    }

    public double calorias(Double pesoKg, Double distanciaMetros, Long duracaoMilis) {
        Double velocidadeKmh = velocidadeEmKmH(distanciaMetros, duracaoMilis);
        double minutos = conversorTempo.fromMilliseconds(duracaoMilis).toMinutes();
        Double mets = unidadeMetabolica.getMet(velocidadeKmh);
        return calorias(mets, pesoKg, minutos);
    }

    private double calorias(double mets, double pesoKg, double minutos) {
        double mlMin = mets * UnidadeMetabolica.V_O2 * pesoKg;
        double lMin = mlMin / 1000;
        double calMin = lMin * UnidadeMetabolica.CAL_1L_O2;
        double gasto = calMin * minutos;
        return bgUtils.arredondado(gasto, 2).doubleValue();
    }

}

