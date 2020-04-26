package br.edu.utfpr.pb.jeanpeiter.tcc.sensor.localizacao;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import java.util.Observable;
import java.util.Observer;

import br.edu.utfpr.pb.jeanpeiter.tcc.modelo.data.LocationObservedData;

public class LocalizacaoListener extends Observable implements LocationListener {

    public LocalizacaoListener(Observer observer) {
        this.addObserver(observer);
    }

    @Override
    public void onLocationChanged(Location location) {
        notifyObservers(LocationObservedData.builder()
                .metodo(LocationObservedData.Metodo.LOCATION_CHANGED)
                .location(location)
                .build());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
        notifyObservers(LocationObservedData.builder()
                .metodo(LocationObservedData.Metodo.PROVIDER_ENABLED)
                .provider(provider)
                .build());
    }

    @Override
    public void onProviderDisabled(String provider) {
        notifyObservers(LocationObservedData.builder()
                .metodo(LocationObservedData.Metodo.PROVIDER_DISABLED)
                .provider(provider)
                .build());
    }

    @Override
    public void notifyObservers(Object arg) {
        setChanged();
        super.notifyObservers(arg);
    }
}
