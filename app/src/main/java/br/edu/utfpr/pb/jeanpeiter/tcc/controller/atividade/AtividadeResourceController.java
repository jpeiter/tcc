package br.edu.utfpr.pb.jeanpeiter.tcc.controller.atividade;

import android.content.Context;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.usuario.Usuario;
import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.sharedpreferences.AppSharedPreferences;

public class AtividadeResourceController {

    private Context context;
    private AtividadeUnidadesController unidadesController;
    private Usuario usuario;

    public AtividadeResourceController(Context context) {
        this.context = context;
        this.unidadesController = new AtividadeUnidadesController();
        this.usuario = new AppSharedPreferences(context).getUsuario();
    }

    public String getUnidadeMedidaDistancia(Double distanciaMetros) {
        return distanciaMetros < 1000 ? context.getString(R.string.m) : context.getString(R.string.km);
    }

    public String distancia(Double distanciaMetros) {
        return String.valueOf(unidadesController.distancia(distanciaMetros));
    }

    public String tempo(Duration duration) {
        return String.format("%dh %dmin", duration.toHours(), duration.toMinutes() % 60);
    }

    public String data(LocalDate data) {
        return data.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
    }

    public String velocidade(Double distanciaMetros, long tempoMillis) {
        return String.valueOf(unidadesController.velocidadeEmKmH(distanciaMetros, tempoMillis));
    }

    public String ritmo(Double distanciaMetros, long tempoMillis) {
        int[] minSec = unidadesController.ritmoMinutoSegundo(distanciaMetros, tempoMillis);
        String min = String.valueOf(minSec[0]).concat("'");
        String sec = String.valueOf(minSec[1] < 10 ? "0" + minSec[1] : minSec[1]).concat("\"");
        return min.concat(sec);
    }

    public String calorias(Double distanciaMetros, long tempoMillis) {
        double cal = unidadesController.calorias(usuario.getPeso(), distanciaMetros, tempoMillis);
        return String.valueOf(cal);
    }


}
