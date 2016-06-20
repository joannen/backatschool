package se.theyellowbelliedmarmot.backatschool.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by joanne on 13/06/16.
 */
public class BackgroundScanningService extends Service {

    private static final String TAG = "BACKGROUNDSCANNING";
    private static final String NAME = "BACKGROUNDSERVICE";
    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;
    private ScanSettings scanSettings;
    private List<ScanFilter> scanFilters;
    private BluetoothLeScanner scanner;
    private List<String> subscribedBeacons;




    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//
//        if(getApplicationContext().getSystemService())
//        subscribedBeacons = new ArrayList<>();
//        com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
//
//        for (String  s: intent.getStringArrayListExtra("beacons")) {
//            JsonObject json = parser.parse(s).getAsJsonObject();
//            subscribedBeacons.add(JsonParser.jsonToBeacon(json));
//            Log.d(TAG, s);
//        }
        Set<String> set = getSharedPreferences("devices", Context.MODE_PRIVATE).getStringSet("devices", null);
        subscribedBeacons = new ArrayList<>(set);


        setUpFilters(subscribedBeacons);
        scanner.startScan(scanFilters, scanSettings, scanCallback);

        return START_STICKY;
    }

    @Override
    public void onCreate() {

        setUpScan();

    }

    private ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            Log.d(TAG, String.valueOf(result));

        }
    };

    private void setUpScan(){
        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        scanner = bluetoothAdapter.getBluetoothLeScanner();
        scanSettings = new ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_POWER)
                .build();

    }

    private void setUpFilters(List<String> beaconList){
        scanFilters = new ArrayList<>();
        for (String  b: beaconList) {
            ScanFilter filter = new ScanFilter.Builder().setDeviceAddress(b).build();
            scanFilters.add(filter);
        }
    }


}
