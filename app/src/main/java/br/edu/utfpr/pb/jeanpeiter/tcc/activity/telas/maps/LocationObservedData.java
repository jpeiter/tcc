package br.edu.utfpr.pb.jeanpeiter.tcc.activity.telas.maps;

import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;

import lombok.Builder;
import lombok.Getter;

@Builder @Getter
public class LocationObservedData {

    private Metodo metodo;

    private Location location;

    private String provider;

    private int status;

    private Bundle extras;

    private Bundle bundle;

    private int i;

    private ConnectionResult connectionResult;

    public enum Metodo {
        LOCATION_CHANGED,
        STATUS_CHANGED,
        PROVIDER_ENABLED,
        PROVIDER_DISABLED,
        CONNECTED,
        CONNECTION_SUSPENDED,
        CONNECTION_FAILED
    }

}
