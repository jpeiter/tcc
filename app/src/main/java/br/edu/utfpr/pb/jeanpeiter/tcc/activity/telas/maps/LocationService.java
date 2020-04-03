package br.edu.utfpr.pb.jeanpeiter.tcc.activity.telas.maps;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import java.util.Observable;

public class LocationService extends Observable implements ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

    @Override
    public void onLocationChanged(Location location) {
        notifyObservers(
                LocationObservedData.builder()
                        .metodo(LocationObservedData.Metodo.LOCATION_CHANGED)
                        .location(location)
                        .build()
        );
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        notifyObservers(
                LocationObservedData.builder()
                        .metodo(LocationObservedData.Metodo.STATUS_CHANGED)
                        .provider(provider)
                        .status(status)
                        .extras(extras)
                        .build()
        );
    }

    @Override
    public void onProviderEnabled(String provider) {
        notifyObservers(
                LocationObservedData.builder()
                        .metodo(LocationObservedData.Metodo.PROVIDER_ENABLED)
                        .provider(provider)
                        .build()
        );
    }

    @Override
    public void onProviderDisabled(String provider) {
        notifyObservers(
                LocationObservedData.builder()
                        .metodo(LocationObservedData.Metodo.PROVIDER_DISABLED)
                        .provider(provider)
                        .build()
        );

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        notifyObservers(
                LocationObservedData.builder()
                        .metodo(LocationObservedData.Metodo.CONNECTED)
                        .bundle(bundle)
                        .build()
        );

    }

    @Override
    public void onConnectionSuspended(int i) {
        notifyObservers(
                LocationObservedData.builder()
                        .metodo(LocationObservedData.Metodo.CONNECTION_SUSPENDED)
                        .i(i)
                        .build()
        );
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        notifyObservers(
                LocationObservedData.builder()
                        .metodo(LocationObservedData.Metodo.CONNECTION_FAILED)
                        .connectionResult(connectionResult)
                        .build()
        );
    }
}
