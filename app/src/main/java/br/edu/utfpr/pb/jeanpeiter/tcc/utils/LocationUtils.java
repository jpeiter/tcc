package br.edu.utfpr.pb.jeanpeiter.tcc.utils;

import android.location.Location;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.posicao.AtividadePosicao;

public class LocationUtils {

    public Location toLocation(AtividadePosicao posicao) {
        Location location = new Location("");
        location.setLatitude(posicao.getLatitude());
        location.setLongitude(posicao.getLongitude());
        return location;
    }
}