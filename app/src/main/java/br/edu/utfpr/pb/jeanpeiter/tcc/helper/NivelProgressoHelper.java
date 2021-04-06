package br.edu.utfpr.pb.jeanpeiter.tcc.helper;

import com.digidemic.unitof.UnitOf;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.enums.AtividadeTipo;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.nivelprogresso.NivelProgresso;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.nivelprogresso.UsuarioNivelProgresso;

public class NivelProgressoHelper {

    public Long calcular(Long duracaoSegundos, Double distanciaMetros, Long calorias, AtividadeTipo tipo) {
        double duracaoMinutos = new UnitOf.Time().fromSeconds(duracaoSegundos).toMinutes();
        double distanciaKm = new UnitOf.Length().fromMeters(distanciaMetros).toKilometers();

        double distanciaTempo = duracaoMinutos * distanciaKm;
        long pontuacao = (long) (distanciaTempo + calorias);
        long multiplicadorTipo = AtividadeTipo.DUPLA.equals(tipo) && duracaoMinutos >= 15 ? 2L : 1L;
        final int FATOR = 5;
        return pontuacao * multiplicadorTipo * FATOR;
    }

    public List<UsuarioNivelProgresso> nivelAtualizado(UsuarioNivelProgresso nivelAtual, String uid, Long pontuacao) {
        int total = pontuacao.intValue();
        LocalDateTime dataHora = LocalDateTime.now();

        if (nivelAtual != null) {
            total += nivelAtual.getPontuacao();
        } else {
            nivelAtual = UsuarioNivelProgresso.builder()
                    .nivel(NivelProgresso.BRIDGETOWN)
                    .usuarioUid(uid)
                    .dataHora(dataHora)
                    .build();
        }
        List<NivelProgresso> niveis = getNiveisPorPontuacao(nivelAtual.getNivel(), total);
        return atualizarProgressos(nivelAtual, total, niveis, uid, dataHora);
    }

    private List<UsuarioNivelProgresso> atualizarProgressos(UsuarioNivelProgresso nivelAtual, int pontuacao, List<NivelProgresso> niveis, String uid, LocalDateTime dataHora) {
        List<UsuarioNivelProgresso> niveisProgresso = new ArrayList<>();

        nivelAtual.setPontuacao(Math.min(pontuacao, nivelAtual.getNivel().getPontuacaoMaxima()));
        niveisProgresso.add(nivelAtual);

        if (!niveis.isEmpty()) {
            List<UsuarioNivelProgresso> novos = niveis.stream()
                    .filter(n -> n.getOrdem() != nivelAtual.getNivel().getOrdem())
                    .map(n ->
                            // Se a pontuação total for maior que a máxima do nível,
                            new UsuarioNivelProgresso(uid, n, dataHora, Math.min(n.getPontuacaoMaxima(), pontuacao)))
                    .collect(Collectors.toList());
            niveisProgresso.addAll(novos);
        }


        return niveisProgresso;
    }

    public List<NivelProgresso> getNiveisPorPontuacao(NivelProgresso nivel, int pontuacao) {
        return Arrays.stream(NivelProgresso.values())
                .filter(n -> pontuacao >= n.getPontuacaoMinima() && n.getOrdem() > nivel.getOrdem())
                .collect(Collectors.toList());
    }

    public NivelProgresso proximoNivel(NivelProgresso nivel) {
        int idx = isUltimo(nivel) ? nivel.ordinal() : nivel.ordinal() + 1;
        return NivelProgresso.values()[idx];
    }

    public boolean isUltimo(NivelProgresso nivel) {
        return nivel.ordinal() + 1 == NivelProgresso.values().length;
    }
}
