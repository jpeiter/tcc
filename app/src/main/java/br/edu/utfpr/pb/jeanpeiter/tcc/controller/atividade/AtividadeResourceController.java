package br.edu.utfpr.pb.jeanpeiter.tcc.controller.atividade;

import android.content.Context;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;

public class AtividadeResourceController {

    private Context context;
    private AtividadeUnidadesController unidadesController;

    public AtividadeResourceController(Context context) {
        this.context = context;
        unidadesController = new AtividadeUnidadesController();
    }

    public String getUnidadeMedidaDistancia(Double distanciaMetros) {
        return distanciaMetros < 1000 ? context.getString(R.string.m) : context.getString(R.string.km);
    }

    public String distancia(Double distanciaMetros) {
        return String.valueOf(unidadesController.distancia(distanciaMetros));
    }

    public String velocidade(Double distanciaMetros, long tempoMillis) {
        return String.valueOf(unidadesController.velocidade(distanciaMetros, tempoMillis));
    }

    public String ritmo(Double distanciaMetros, long tempoMillis) {
        return String.valueOf(unidadesController.ritmo(distanciaMetros, tempoMillis));
    }


}
