package br.edu.utfpr.pb.jeanpeiter.tcc.connectivity.info;

import android.bluetooth.BluetoothAdapter;

public class BluetoothInformation {

    public static boolean isBluetoothAvailable() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        adapter.startDiscovery();
        return adapter != null && adapter.isEnabled();
    }
}
