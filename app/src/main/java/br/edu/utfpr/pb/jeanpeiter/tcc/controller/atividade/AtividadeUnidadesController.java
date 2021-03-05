package br.edu.utfpr.pb.jeanpeiter.tcc.controller.atividade;

import com.digidemic.unitof.UnitOf;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import br.edu.utfpr.pb.jeanpeiter.tcc.consts.UnidadeMetabolica;
import br.edu.utfpr.pb.jeanpeiter.tcc.utils.BigDecimalUtils;

public class AtividadeUnidadesController {

    private BigDecimalUtils bgUtils = new BigDecimalUtils();
    private UnidadeMetabolica unidadeMetabolica = new UnidadeMetabolica();
    private UnitOf.Speed conversorVelocidade = new UnitOf.Speed();
    private UnitOf.Length conversorDistancia = new UnitOf.Length();
    private UnitOf.Time conversorTempo = new UnitOf.Time();

    public Double distancia(Double distanciaMetros) {
        double m = distanciaMetros != null ? distanciaMetros : 0.0;
        double distancia = m < 1000 ? m : conversorDistancia.fromMeters(m).toKilometers();
        return bgUtils.arredondado(distancia, 2).doubleValue();
    }

    public Double velocidadeEmKmH(Double distanciaMetros, Long duracaoMilis) {
        double m = distanciaMetros;
        double s = conversorTempo.fromMilliseconds(duracaoMilis).toSeconds();
        double kmH = conversorVelocidade.fromMetersPerSecond(m / s).toKilometersPerHour();
        return bgUtils.arredondado(kmH, 2).doubleValue();
    }

    public int ritmo(Double distanciaMetros, Long duracaoMilis) {
        Duration duration = ritmoMinutoSegundo(distanciaMetros, duracaoMilis);
        return (int) duration.get(ChronoUnit.SECONDS);
    }

    public Duration ritmoMinutoSegundo(Double distanciaMetros, Long duracaoMilis) {
        double min = conversorTempo.fromMilliseconds(duracaoMilis).toMinutes();
        double km = conversorDistancia.fromMeters(distanciaMetros).toKilometers();
        int minutos = 0;
        int sec = 0;

        if (min > 0 && km > 0) {
            double ritmo = min / km;
            minutos = (int) ritmo;
            double segundos = ritmo - minutos;
            segundos = segundos * 60;
            sec = (int) segundos;
        }
        return Duration.ofMinutes(minutos).plusSeconds(sec);
    }

    public Long calorias(Double pesoKg, Double distanciaMetros, Long duracaoMilis) {
        Double velocidadeKmh = velocidadeEmKmH(distanciaMetros, duracaoMilis);
        double minutos = conversorTempo.fromMilliseconds(duracaoMilis).toMinutes();
        Double mets = unidadeMetabolica.getMet(velocidadeKmh);
        return calorias(mets, pesoKg, minutos);
    }

    private Long calorias(double mets, double pesoKg, double minutos) {
        double mililitroPorMinuto = mets * UnidadeMetabolica.V_O2 * pesoKg;
        double litroPorMinuto = mililitroPorMinuto / 1000;
        double caloriasPorMinuto = litroPorMinuto * UnidadeMetabolica.CAL_1L_O2;
        double gastoTotal = caloriasPorMinuto * minutos;
        return Double.valueOf(gastoTotal).longValue();
    }

}

