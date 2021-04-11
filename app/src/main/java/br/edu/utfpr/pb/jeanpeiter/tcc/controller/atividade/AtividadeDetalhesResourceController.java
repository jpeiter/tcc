package br.edu.utfpr.pb.jeanpeiter.tcc.controller.atividade;

import android.content.Context;

import com.digidemic.unitof.UnitOf;

import java.time.LocalDate;

import br.edu.utfpr.pb.jeanpeiter.tcc.utils.DateUtils;

public class AtividadeDetalhesResourceController {

    private final AtividadeResourceController resourceController;
    private final AtividadeUnidadesController unidadesController;
    private final DateUtils dateUtils;

    public AtividadeDetalhesResourceController(Context context) {
        resourceController = new AtividadeResourceController(context);
        unidadesController = new AtividadeUnidadesController();
        dateUtils = new DateUtils();
    }

    public String diaSemanaEData(Long inicioMillis) {
        return resourceController.diaSemanaEData(dateUtils.millisToLocalDate(inicioMillis));
    }

    public String distancia(Double distanciaMetros) {
        return resourceController.distancia(distanciaMetros);
    }

    public String unidadeMedidaDistancia(Double distanciaMetros) {
        return resourceController.getUnidadeMedidaDistancia(distanciaMetros);
    }

    public String duracao(Long duracaoSegundos) {
        return resourceController.tempo(
                new AtividadeUnidadesController().duracao(duracaoSegundos)
        );
    }

    public String calorias(Long calorias) {
        return String.valueOf(calorias);
    }

    public String inicio(Long inicio) {
        return dateUtils.horario(
                dateUtils.millisToLocalDateTime(inicio).toLocalTime()
        );
    }

    public String fim(Long fim) {
        return inicio(fim);
    }

    public String velocidade(Double velocidade) {
        return String.valueOf(unidadesController.velocidadeEmKmH(velocidade));
    }

    public String ritmo(Integer ritmoSegundos) {
        return resourceController.tempo(
                unidadesController.duracao(ritmoSegundos.longValue())
        );
    }


}
