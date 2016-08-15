package se.theyellowbelliedmarmot.backatschool.activity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import se.theyellowbelliedmarmot.backatschool.R;
import se.theyellowbelliedmarmot.backatschool.model.Beacon;
import se.theyellowbelliedmarmot.backatschool.model.adapter.BeaconAdapter;
import se.theyellowbelliedmarmot.backatschool.tools.Utility;

public class ScanActiveBeacon extends BaseActivity {

    private static final long SCAN_PERIOD = 5000;
    private static final String BEACON_NAME = "closebeacon.com";
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 0;
    private BluetoothLeScanner scanner;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothManager bluetoothManager;
    private ScanSettings scanSettings;
    private List<Beacon> beacons;
    private List<ScanFilter> scanFilters;
    private Handler handler;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_active_beacon);

        handler = new Handler();
        beacons = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.beacon_list);
        layoutManager = new LinearLayoutManager(this);
        adapter = new BeaconAdapter(this, beacons);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        scanner = bluetoothAdapter.getBluetoothLeScanner();
        scanSettings = new ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                .build();
        scanFilters = new ArrayList<>();
        ScanFilter filter = new ScanFilter.Builder().setDeviceName(BEACON_NAME).build();
        scanFilters.add(filter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.scan, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void displayList(MenuItem item) {
        //request permission for access_coarse_location.
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
        }
        scanBeacon(true);
    }

    private final ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {

            //the manufacturerSpecificData is bytearray at the 0:th place in the scanRecord
            byte[] manufacturerSpecificData = result.getScanRecord().getManufacturerSpecificData().valueAt(0);

            //if lenght > 10, beacon is active
            if (manufacturerSpecificData.length > 10) {
                Beacon beacon = Utility.resultToBeacon(result, manufacturerSpecificData);
                addBeaconToList(beacon);
            }
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
        }
    };

    private void scanBeacon(boolean scan) {
        if (scan) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    scanner.stopScan(scanCallback);
                }
            }, SCAN_PERIOD);
            scanner.startScan(scanFilters, scanSettings, scanCallback);
            // Test this
            Toast.makeText(getApplicationContext(), "Scanning for beacons", Toast.LENGTH_SHORT).show();
        } else {
            scanner.stopScan(scanCallback);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }

    private void addBeaconToList(Beacon beacon) {

        if (beacons.contains(beacon)) {
            beacons.remove(beacon);
        }
        beacons.add(beacon);
        Collections.sort(beacons, rssiComparator);
        adapter.notifyDataSetChanged();
    }

    public void stopScan(MenuItem item) {
        scanBeacon(false);
        Toast.makeText(getApplicationContext(), "Stopped scan for beacons", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private final Comparator<Beacon> rssiComparator = new Comparator<Beacon>() {
        @Override
        public int compare(Beacon beaconOne, Beacon beaconTwo) {
            return (beaconOne.getRssi() > beaconTwo.getRssi() ? -1 : (beaconOne.getRssi() == beaconTwo.getRssi() ? 0 : 1));
        }
    };

}
