package br.edu.utfpr.pb.jeanpeiter.tcc.connectivity.info;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import br.edu.utfpr.pb.jeanpeiter.tcc.persistence.sharedpreferences.AppSharedPreferences;

public class GpsInformation {

    public static boolean isLocationEnabled(Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE );
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}
