package br.edu.utfpr.pb.jeanpeiter.tcc.consts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UnidadeMetabolica {

    public final Map<Double, Double> velocidadesMets = getVelocidadesMets();
    public static final double V_O2 = 3.5;
    public static final int CAL_1L_O2 = 5;

    // Key: velocidade em km/h. Value: MET
    private Map<Double, Double> getVelocidadesMets() {
        Map<Double, Double> map = new HashMap<>();
        map.put(6.44, 6.0);
        map.put(8.05, 8.3);
        map.put(8.37, 9.0);
        map.put(9.66, 9.8);
        map.put(10.78, 10.5);
        map.put(11.27, 11.0);
        map.put(12.07, 11.5);
        map.put(12.87, 11.8);
        map.put(13.84, 12.3);
        map.put(14.48, 12.8);
        map.put(16.09, 14.5);
        map.put(17.7, 16.0);
        map.put(19.31, 19.0);
        map.put(20.92, 19.8);
        map.put(22.53, 23.0);
        return map;
    }

    public Double getMet(Double velocidadeKmh) {
        return velocidadesMets.keySet().stream()
                // Ordenar do maior para menor
                .sorted((a, b) -> b.compareTo(a))
                // Retornar a primeira velocidade que for maior ou igual à equivalente
                .filter(velocidadeMet -> velocidadeKmh >= velocidadeMet)
                .findFirst()
                // Mapeia para o MET
                .map(velocidadesMets::get)
                // Se for mais baixa
                .orElse(6.0);
    }

}
