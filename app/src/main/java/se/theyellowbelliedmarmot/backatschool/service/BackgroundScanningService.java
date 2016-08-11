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
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import se.theyellowbelliedmarmot.backatschool.model.Beacon;
import se.theyellowbelliedmarmot.backatschool.model.ScanResponse;
import se.theyellowbelliedmarmot.backatschool.tools.PresenceDetectionService;
import se.theyellowbelliedmarmot.backatschool.tools.Utility;


public class BackgroundScanningService extends Service {


    private static final String TAG = "BACKGROUNDSCANNING";
    protected static final String APIKEY = "28742sk238sdkAdhfue243jdfhvnsa1923347";

    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;
    private ScanSettings scanSettings;
    private List<ScanFilter> scanFilters;
    private BluetoothLeScanner scanner;
    private List<String> subscribedBeacons;
    private AtomicBoolean inRange;
    private String userId;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        inRange = new AtomicBoolean();

        Set<String> set = getSharedPreferences("devices", Context.MODE_PRIVATE).getStringSet("devices", null);
        subscribedBeacons = new ArrayList<>(set);
        setUpFilters(subscribedBeacons);
        scanner.startScan(scanFilters, scanSettings, scanCallback);

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        setUpScan();
        userId = getSharedPreferences("id",Context.MODE_PRIVATE).getString("id", "");
    }

    private ScanCallback scanCallback = new ScanCallback() {

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            Log.d(TAG, "FAILED");
        }

        @Override
        public void onScanResult(int callbackType, ScanResult result) {

            if(result== null){
                Log.d(TAG, "RESULT NULL");
            }

            Log.d(TAG, String.valueOf(result));
            Log.d(TAG, String.valueOf(inRange));
            Range range = checkRSSI(result.getRssi());
            byte[] manufacturerSpecificData = result.getScanRecord().getManufacturerSpecificData().valueAt(0);

            switch (range){
                case IN:
                    if(!inRange.get()){
                        inRange.set(true);
                        Log.d(TAG, "IN RANGE");
                        if(manufacturerSpecificData.length>10){
                            int major = (manufacturerSpecificData[18] & 0xff) * 0x100 + (manufacturerSpecificData[19] & 0xff);
                            int minor = (manufacturerSpecificData[20] & 0xff) * 0x100 + (manufacturerSpecificData[21] & 0xff);
                            String uuid = Utility.convertToHex(Arrays.copyOfRange(manufacturerSpecificData, 2,18));
                            String deviceAddress = result.getDevice().getAddress();
                            Beacon beacon = new Beacon(uuid, Integer.toString(major), Integer.toString(minor), result.getRssi(), result.getDevice().getName(), deviceAddress);
                            PresenceDetectionService.inRangeDetected(APIKEY,new ScanResponse(beacon, userId, Range.IN), getApplicationContext());

                        }
                    }else {
                        Log.d(TAG, "IN RANGE BUT DOING NOTHING");
                    }
                    break;
                case OUT:
                    if(inRange.get()){
                        inRange.set(false);
                        if(manufacturerSpecificData.length>10){
                            int major = (manufacturerSpecificData[18] & 0xff) * 0x100 + (manufacturerSpecificData[19] & 0xff);
                            int minor = (manufacturerSpecificData[20] & 0xff) * 0x100 + (manufacturerSpecificData[21] & 0xff);
                            String uuid = Utility.convertToHex(Arrays.copyOfRange(manufacturerSpecificData, 2,18));
                            String deviceAddress = result.getDevice().getAddress();
                            Beacon beacon = new Beacon(uuid, Integer.toString(major), Integer.toString(minor), result.getRssi(), result.getDevice().getName(), deviceAddress);
                            PresenceDetectionService.outOfRangeDetected(APIKEY, new ScanResponse(beacon, userId, Range.OUT), getApplicationContext());
                            Log.d(TAG, "OUT OF RANGE");
                        }

                    }else {
                        Log.d(TAG, "OUT OF RANGE AND DOING NOTHING");
                    }
                    break;
                default:Log.d(TAG, "UNCLEAR");
            }
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

    private Range checkRSSI(int rssi){
        if(rssi<= -25&&rssi >=-45){
            return Range.IN;
        }else return Range.OUT;
    }
}
