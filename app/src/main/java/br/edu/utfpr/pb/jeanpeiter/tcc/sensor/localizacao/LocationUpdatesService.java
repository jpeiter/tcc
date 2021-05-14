package br.edu.utfpr.pb.jeanpeiter.tcc.sensor.localizacao;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import br.edu.utfpr.pb.jeanpeiter.tcc.R;

public class LocationUpdatesService extends Service {

    private static final String PACKAGE_NAME = LocationUpdatesService.class.getPackage().getName();
    private static final String TAG = LocationUpdatesService.class.getSimpleName();
    private static final String CHANNEL_ID = "location_notification_channel";
    public static final String ACTION_BROADCAST = PACKAGE_NAME + ".broadcast";
    public static final String EXTRA_LOCATION = PACKAGE_NAME + ".location";
    private static final String EXTRA_STARTED_FROM_NOTIFICATION = PACKAGE_NAME + ".started_from_notification";
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 4000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    private static final int NOTIFICATION_ID = 12345678;
    private boolean mChangingConfiguration = false;
    private NotificationManager mNotificationManager;
    private final IBinder mBinder = new LocalBinder();
    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private Handler mServiceHandler;
    public static Location mLastLocation;

    public LocationUpdatesService() {
    }

    @Override
    public void onCreate() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                onNewLocation(locationResult.getLastLocation());
            }
        };

        createLocationRequest();
        getLastLocation();

        HandlerThread handlerThread = new HandlerThread(TAG);
        handlerThread.start();
        mServiceHandler = new Handler(handlerThread.getLooper());
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        String name = getString(R.string.app_name);
        // Cria canal de notificações
        NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
        mNotificationManager.createNotificationChannel(mChannel);
    }

    private void onNewLocation(Location location) {
        // Notifica os listeners
        Intent intent = new Intent(ACTION_BROADCAST);
        intent.putExtra(EXTRA_LOCATION, location);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
        mLastLocation = location;
        // Atualiza a notificacao
        mNotificationManager.notify(NOTIFICATION_ID, getNotification());
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest()
                .setInterval(UPDATE_INTERVAL_IN_MILLISECONDS)
                .setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void getLastLocation() {
        try {
            mFusedLocationClient.getLastLocation().addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult() != null) {
                    mLastLocation = task.getResult();
                }
            });
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public void requestLocationUpdates() {
        LocationServiceUtil.setRequestingLocationUpdates(this, true);
        startService(new Intent(getApplicationContext(), LocationUpdatesService.class));
        try {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
        } catch (SecurityException e) {
            LocationServiceUtil.setRequestingLocationUpdates(this, false);
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mChangingConfiguration = true;
    }

    @Override
    public IBinder onBind(Intent intent) {
        stopForeground(true);
        mChangingConfiguration = false;
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        stopForeground(true);
        mChangingConfiguration = false;
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if (!mChangingConfiguration && LocationServiceUtil.requestingLocationUpdates(this)) {
            startForeground(NOTIFICATION_ID, getNotification());
        }
        return true;
    }

    @Override
    public void onDestroy() {
        mNotificationManager.cancelAll();
        mServiceHandler.removeCallbacksAndMessages(null);
    }


    public void removeLocationUpdates() {
        try {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
            LocationServiceUtil.setRequestingLocationUpdates(this, false);
            stopSelf();
        } catch (SecurityException e) {
            LocationServiceUtil.setRequestingLocationUpdates(this, true);
        }
    }

    private Notification getNotification() {
        Intent intent = new Intent(this, LocationUpdatesService.class);
        CharSequence text = LocationServiceUtil.getLocationText(mLastLocation);
        intent.putExtra(EXTRA_STARTED_FROM_NOTIFICATION, true);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setAutoCancel(false)
                .setContentText(text)
                .setContentTitle(LocationServiceUtil.getLocationTitle())
                .setOngoing(true)
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker(text)
                .setWhen(System.currentTimeMillis());

        builder.setChannelId(CHANNEL_ID);
        return builder.build();
    }
//
//    private void getLastLocation() {
//        try {
//            mFusedLocationClient.getLastLocation().addOnCompleteListener(task -> {
//                if (task.isSuccessful() && task.getResult() != null) {
//                    mLastLocation = task.getResult();
//                }
//            });
//        } catch (SecurityException unlikely) {
//            Log.e(TAG, "Lost location permission." + unlikely);
//        }
//    }
//
//    private void onNewLocation(Location location) {
//        // Notifica os listeners
//        Intent intent = new Intent(ACTION_BROADCAST);
//        intent.putExtra(EXTRA_LOCATION, location);
//        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
//
//        mLastLocation = location;
//        // Atualiza a notificacao se esta em 2o plano
//        mNotificationManager.notify(NOTIFICATION_ID, getNotification());
//    }
//
//    private void createLocationRequest() {
//        mLocationRequest = new LocationRequest()
//                .setInterval(UPDATE_INTERVAL_IN_MILLISECONDS)
//                .setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS)
//                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//    }

    public class LocalBinder extends Binder {
        public LocationUpdatesService getService() {
            return LocationUpdatesService.this;
        }
    }
}
