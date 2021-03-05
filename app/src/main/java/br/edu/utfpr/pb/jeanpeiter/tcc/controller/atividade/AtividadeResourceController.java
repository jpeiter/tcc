package br.edu.utfpr.pb.jeanpeiter.tcc.controller.atividade;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringJoiner;

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
        Duration dur = duration;
        long hours = dur.toHours();
        dur = dur.minusHours(hours);
        long minutes = dur.toMinutes();
        dur = dur.minusMinutes(minutes);
        long seconds = dur.getSeconds();

        StringJoiner sb = new StringJoiner(" ");
        List<Long> values = new ArrayList<>();
        if (hours > 0) {
            sb.add("%dh");
            values.add(hours);
        }
        if (minutes > 0) {
            sb.add("%dmin");
            values.add(minutes);
        }
        if (seconds > 0) {
            sb.add("%ds");
            values.add(seconds);
        }
        Long[] valuesA = new Long[values.size()];
        return String.format(sb.toString(), values.toArray(valuesA));
    }

    public String data(LocalDate data) {
        return data.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
    }

    public String diaSemanaEData(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
    }

    public String velocidade(Double distanciaMetros, long tempoMillis) {
        return String.valueOf(unidadesController.velocidadeEmKmH(distanciaMetros, tempoMillis));
    }

    public String ritmo(Double distanciaMetros, long tempoMillis) {
        Duration duration = unidadesController.ritmoMinutoSegundo(distanciaMetros, tempoMillis);
        return tempo(duration);
    }

    public String calorias(Double distanciaMetros, long tempoMillis) {
        double cal = unidadesController.calorias(usuario.getPeso(), distanciaMetros, tempoMillis);
        return String.valueOf(cal);
    }


}
