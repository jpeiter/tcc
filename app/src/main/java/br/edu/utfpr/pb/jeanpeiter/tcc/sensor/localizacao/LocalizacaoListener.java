package br.edu.utfpr.pb.jeanpeiter.tcc.sensor.localizacao;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import org.greenrobot.eventbus.EventBus;

import br.edu.utfpr.pb.jeanpeiter.tcc.sensor.localizacao.data.LocationObservedData;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LocalizacaoListener implements LocationListener {

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
        EventBus.getDefault().post(
            LocationObservedData.builder()
                .metodo(LocationObservedData.Metodo.PROVIDER_ENABLED)
                .provider(provider)
            .build()
        );
    }

    @Override
    public void onProviderDisabled(String provider) {
        EventBus.getDefault().post(
            LocationObservedData.builder()
                .metodo(LocationObservedData.Metodo.PROVIDER_DISABLED)
                .provider(provider)
            .build()
        );
    }
}
