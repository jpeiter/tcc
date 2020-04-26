package br.edu.utfpr.pb.jeanpeiter.tcc.controller.atividade;

import android.content.Context;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class AtividadeResourceController {

    private Context context;

    public String getUnidadeMedidaDistancia(Double distancia) {
        return distancia < 1000 ? context.getString(R.string.m) : context.getString(R.string.km);
    }


}
