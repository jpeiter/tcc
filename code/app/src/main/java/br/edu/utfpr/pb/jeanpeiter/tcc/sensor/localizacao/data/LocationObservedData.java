package br.edu.utfpr.pb.jeanpeiter.tcc.sensor.localizacao.data;

import android.location.Location;
import android.os.Bundle;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LocationObservedData {

    private Metodo metodo;

    private Location location;

    private String provider;

    private int status;

    private Bundle extras;

    public enum Metodo {
        LOCATION_CHANGED,
        PROVIDER_ENABLED,
        PROVIDER_DISABLED
    }

}
