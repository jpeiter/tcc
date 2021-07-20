package br.edu.utfpr.pb.jeanpeiter.tcc.sensor.localizacao.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import org.greenrobot.eventbus.EventBus;

import br.edu.utfpr.pb.jeanpeiter.tcc.sensor.localizacao.LocationUpdatesService;
import br.edu.utfpr.pb.jeanpeiter.tcc.sensor.localizacao.data.LocationObservedData;

public class LocalizacaoReceiver extends BroadcastReceiver {

    private Location lastLocation = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        Location location = intent.getParcelableExtra(LocationUpdatesService.EXTRA_LOCATION);
        boolean hasMinDistance = lastLocation != null && (location != null ? location.distanceTo(lastLocation) : 0) > 5;
        lastLocation = lastLocation != null ? location : new Location("");
        if (location != null && hasMinDistance) {
            EventBus.getDefault().post(
                    LocationObservedData.builder()
                            .metodo(LocationObservedData.Metodo.LOCATION_CHANGED)
                            .location(location)
                            .build()
            );
        }
    }
}
