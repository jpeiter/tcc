package br.edu.utfpr.pb.jeanpeiter.tcc.utils;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.modelo.atividade.posicao.AtividadePosicao;

public class LocationUtils {

    public Location toLocation(AtividadePosicao posicao) {
        Location location = new Location("");
        location.setLatitude(posicao.getLatitude());
        location.setLongitude(posicao.getLongitude());
        return location;
    }

    public LatLng toLatLng(AtividadePosicao posicao) {
        return toLatLng(toLocation(posicao));
    }

    public LatLng toLatLng(Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }
}
