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
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import se.theyellowbelliedmarmot.backatschool.bluetooth.BLEService;
import se.theyellowbelliedmarmot.backatschool.constants.URLS;
import se.theyellowbelliedmarmot.backatschool.model.Beacon;
import se.theyellowbelliedmarmot.backatschool.model.InRangeData;
import se.theyellowbelliedmarmot.backatschool.model.OutOfRangeData;
import se.theyellowbelliedmarmot.backatschool.tools.Utility;

/**
 * Created by joanne on 13/06/16.
 */
public class BackgroundScanningService extends Service {

    private static final String TAG = "BACKGROUNDSCANNING";
    private static final String APIKEY = "28742sk238sdkAdhfue243jdfhvnsa1923347";

    private BluetoothManager bluetoothManager;
    private List<ScanFilter> scanFilters;
    private List<String> subscribedBeacons;
    private AtomicBoolean currentlyInRange;
    private String userId;
    private PresenceDetectionService presenceDetectionService;
    private Retrofit retrofit;
    private BLEService bleService;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        currentlyInRange = new AtomicBoolean();
        bleService.getScanner().startScan(scanFilters,bleService.getScanSettings(), scanCallback);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
//        setUpScan();
        userId = getSharedPreferences("id", Context.MODE_PRIVATE).getString("id", "");
        retrofit = new Retrofit.Builder().baseUrl(URLS.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        presenceDetectionService = new PresenceDetectionService(retrofit);
        Set<String> set = getSharedPreferences("devices", Context.MODE_PRIVATE).getStringSet("devices", null);

        subscribedBeacons = new ArrayList<>(set);
        //get subscribed devices and scan only for them
        scanFilters = setUpFilters(subscribedBeacons);
        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bleService = new BLEService(new Handler(), bluetoothManager,scanFilters, ScanSettings.SCAN_MODE_LOW_POWER, getApplicationContext());
    }

    private final ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
        }

        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            Log.d(TAG, String.valueOf(result));
            boolean inRange = checkRSSIInRange(result.getRssi());
            byte[] manufacturerSpecificData = result.getScanRecord().getManufacturerSpecificData().valueAt(0);

            if (manufacturerSpecificData.length > 10) {
                if(inRange) {
                    if (currentlyInRange.compareAndSet(false, true)) {
                        Log.d(TAG, "IN RANGE TRYING TO CONNECT");
                        Beacon beacon = Utility.resultToBeacon(result, manufacturerSpecificData);
                        InRangeData inRangeData = new InRangeData(APIKEY, userId, beacon.getUuid(), new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()),beacon.getMajor(), beacon.getMinor());
                        presenceDetectionService.inRangeDetected(inRangeData);
                    }
                }
                 else {
                    if (currentlyInRange.compareAndSet(true, false)) {
                        Beacon beacon = Utility.resultToBeacon(result, manufacturerSpecificData);
                        OutOfRangeData outOfRangeData = new OutOfRangeData(APIKEY, userId, beacon.getUuid(),  new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
                        presenceDetectionService.outOfRangeDetected(outOfRangeData);
                        Log.d(TAG, "OUT OF RANGE TRYING TO CONNECT");
                    }
                }
            }
        }
    };

    private List<ScanFilter> setUpFilters(List<String> beaconList) {
        scanFilters = new ArrayList<>();
        for (String b : beaconList) {
            ScanFilter filter = new ScanFilter.Builder().setDeviceAddress(b).build();
            scanFilters.add(filter);
        }
        return scanFilters;
    }

    private boolean checkRSSIInRange(int rssi) {
        return rssi <= -25 && rssi >= -45;
    }
}
