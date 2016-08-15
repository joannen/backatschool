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
import java.util.concurrent.atomic.AtomicBoolean;

import se.theyellowbelliedmarmot.backatschool.model.Beacon;
import se.theyellowbelliedmarmot.backatschool.model.ScanResponse;
import se.theyellowbelliedmarmot.backatschool.tools.Utility;

/**
 * Created by joanne on 13/06/16.
 */
public class BackgroundScanningService extends Service {

    private static final String TAG = "BACKGROUNDSCANNING";
    private static final String APIKEY = "28742sk238sdkAdhfue243jdfhvnsa1923347";

    private BluetoothManager bluetoothManager;
    private BluetoothAdapter bluetoothAdapter;
    private ScanSettings scanSettings;
    private List<ScanFilter> scanFilters;
    private BluetoothLeScanner scanner;
    private List<String> subscribedBeacons;
    private AtomicBoolean inRange;
    private String userId;
    private PresenceDetectionService presenceDetectionService;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        inRange = new AtomicBoolean();

        //get subscribed devices and scan only for them
        Set<String> set = getSharedPreferences("devices", Context.MODE_PRIVATE).getStringSet("devices", null);
        subscribedBeacons = new ArrayList<>(set);
        setUpFilters(subscribedBeacons);
        scanner.startScan(scanFilters, scanSettings, scanCallback);

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "SERVICE STARTED");
        setUpScan();
        userId = getSharedPreferences("id", Context.MODE_PRIVATE).getString("id", "");
        presenceDetectionService = new PresenceDetectionService(this.getApplicationContext());
    }

    private final ScanCallback scanCallback = new ScanCallback() {

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            Log.d(TAG, "FAILED:" + errorCode);
        }

        @Override
        public void onScanResult(int callbackType, ScanResult result) {

            Log.d(TAG, String.valueOf(result));
            Range range = checkRSSI(result.getRssi());
            byte[] manufacturerSpecificData = result.getScanRecord().getManufacturerSpecificData().valueAt(0);

            if (manufacturerSpecificData.length > 10) {
                switch (range) {
                    case IN:
                        if (!inRange.get()) {
                            inRange.set(true);
                            Log.d(TAG, "IN RANGE");
                            Beacon beacon = Utility.resultToBeacon(result, manufacturerSpecificData);
                            presenceDetectionService.inRangeDetected(APIKEY, new ScanResponse(beacon, userId, Range.IN), getApplicationContext());
//                            inRangeDetected(APIKEY, new ScanResponse(beacon, userId, Range.IN), getApplicationContext());

                        } else {
                            Log.d(TAG, "IN RANGE BUT DOING NOTHING");
                        }
                        break;
                    case OUT:
                        if (inRange.get()) {
                            inRange.set(false);
                            Beacon beacon = Utility.resultToBeacon(result, manufacturerSpecificData);
                            presenceDetectionService.outOfRangeDetected(APIKEY, new ScanResponse(beacon, userId, Range.OUT), getApplicationContext());
//                            outOfRangeDetected(APIKEY, new ScanResponse(beacon, userId, Range.OUT), getApplicationContext());
                            Log.d(TAG, "OUT OF RANGE");
                        } else {
                            Log.d(TAG, "OUT OF RANGE AND DOING NOTHING");
                        }
                        break;
                    default:
                        Log.d(TAG, "UNCLEAR");
                }
            }
        }
    };

    private void setUpScan() {
        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        scanner = bluetoothAdapter.getBluetoothLeScanner();
        scanSettings = new ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_POWER)
                .build();
    }

    private void setUpFilters(List<String> beaconList) {
        scanFilters = new ArrayList<>();
        for (String b : beaconList) {
            ScanFilter filter = new ScanFilter.Builder().setDeviceAddress(b).build();
            scanFilters.add(filter);
        }
    }

    private Range checkRSSI(int rssi) {
        if (rssi <= -25 && rssi >= -45) {
            return Range.IN;
        } else return Range.OUT;
    }

//    public static void inRangeDetected(String apikey, ScanResponse scanResponse, Context context) {
//        Log.d("IN RANGE DETECTED", "YAY");
//        String input = JsonParser.detectionInputToJson(apikey, scanResponse);
//        Log.d(TAG, input);
//        Ion.with(context).load(URLS.IN_RANGE)
//                .addHeader("Content-Type", "application/x-www-form-urlencoded")
//                .setStringBody(input)
//                .asJsonObject()
//                .setCallback(new FutureCallback<JsonObject>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonObject result) {
//                        Log.d("result is null: ", String.valueOf(result == null));
//                        if (result != null) {
//                            Log.d("Result in Range: ", result.toString());
//                        }
//                    }
//                });
//    }
//
//    public static void outOfRangeDetected(String apikey, ScanResponse scanResponse, Context context) {
//        Log.d("OUT OF RANGE DETECTED", "YAY");
//
//        String input = JsonParser.detectionInputToJson(apikey, scanResponse);
//        Ion.with(context).load(URLS.OUT_OF_RANGE)
//                .addHeader("Content-Type", "application/x-www-form-urlencoded")
//                .setStringBody(input)
//                .asJsonObject()
//                .setCallback(new FutureCallback<JsonObject>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonObject result) {
//                        Log.d("result is null: ", String.valueOf(result == null));
//
//                        if (result != null) {
//                            Log.d("Result out of Range: ", result.toString());
//                        }
//                    }
//                });
//    }

}
