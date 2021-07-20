package br.edu.utfpr.pb.jeanpeiter.tcc.sensor.localizacao;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.preference.PreferenceManager;

import androidx.core.app.ActivityCompat;

import java.text.DateFormat;
import java.time.LocalTime;
import java.util.Date;

import br.edu.utfpr.pb.jeanpeiter.tcc.utils.DateUtils;

public class LocationServiceUtil {

    public static final String KEY_REQUESTING_LOCATION_UPDATES = "requesting_location_updates";

    /**
     * Returns true if requesting location updates, otherwise returns false.
     *
     * @param context The {@link Context}.
     */
    public static boolean requestingLocationUpdates(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(KEY_REQUESTING_LOCATION_UPDATES, false);
    }

    /**
     * Stores the location updates state in SharedPreferences.
     *
     * @param requestingLocationUpdates The location updates state.
     */
    static void setRequestingLocationUpdates(Context context, boolean requestingLocationUpdates) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(KEY_REQUESTING_LOCATION_UPDATES, requestingLocationUpdates)
                .apply();
    }

    /**
     * Returns the {@code location} object as a human readable string.
     *
     * @param location The {@link Location}.
     */
    static String getLocationText(Location location) {
        return location == null ? "Unknown location" : "(" + location.getLatitude() + ", " + location.getLongitude() + ")";
    }

    static String getLocationTitle() {
        String dataHora = new DateUtils().horario(LocalTime.now());
        return "Location Updated: " + dataHora;
    }

    public static boolean checkPermissions(Context context) {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(context,
                Build.VERSION.SDK_INT == Build.VERSION_CODES.Q ?
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION :
                        Manifest.permission.ACCESS_FINE_LOCATION);
    }

}