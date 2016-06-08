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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import se.theyellowbelliedmarmot.backatschool.R;
import se.theyellowbelliedmarmot.backatschool.model.Beacon;
import se.theyellowbelliedmarmot.backatschool.model.adapter.BeaconAdapter;
import se.theyellowbelliedmarmot.backatschool.tools.Utility;

public class ScanActiveBeacon extends AppCompatActivity {

    private static final long SCAN_PERIOD = 5000;
    public static final String TAG = "LOGTAG";
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION =0 ;
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
        ScanFilter filter = new ScanFilter.Builder().setDeviceName("closebeacon.com").build();
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
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
        }else {
            //TOAST???????
            Log.d(TAG, "PERMISSION WAS GRANTED");
        }
        scanBeacon(true);
    }

    private ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
//            byte[] manufacturerSpecificData = result.getScanRecord().getManufacturerSpecificData(76);
            byte[] manufacturerSpecificData = result.getScanRecord().getManufacturerSpecificData().valueAt(0);

            int major = (manufacturerSpecificData[18] & 0xff) * 0x100 + (manufacturerSpecificData[19] & 0xff);
            int minor = (manufacturerSpecificData[20] & 0xff) * 0x100 + (manufacturerSpecificData[21] & 0xff);
            String uuid = Utility.convertToHex(Arrays.copyOfRange(manufacturerSpecificData, 2,18));

            Beacon beacon = new Beacon(uuid, Integer.toString(major), Integer.toString(minor), result.getRssi(), result.getDevice().getName());
            addBeaconToList(beacon);
        }
        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
        }
    };


    private void scanBeacon(boolean scan) {
        if(scan){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    scanner.stopScan(scanCallback);
                }
            }, SCAN_PERIOD);
            scanner.startScan(scanFilters, scanSettings, scanCallback);
            // Test this
            Toast.makeText(getApplicationContext(), "Scanning for beacons", Toast.LENGTH_SHORT).show();
        }  else {
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

    private void addBeaconToList(Beacon beacon){
        if (!beacons.contains(beacon)){
            beacons.add(beacon);
            adapter.notifyDataSetChanged();
        }
    }

    public void subscribeToBeacon(View view){



//        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this).setTitle(getString(R.string.confirm_subscription_alert))
//                .setMessage(getString(R.string.confirm_subscription_text))
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(getApplicationContext(), "Added subscrpition", Toast.LENGTH_LONG).show();
//                    }
//                })
//                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(getApplicationContext(), "Nope", Toast.LENGTH_LONG).show();
//                        dialogInterface.cancel();
//                        dialogInterface.dismiss();
//                        finish();
//                    }
//                });
//        alertDialog.create();
//        alertDialog.show();

    }
    // Test this
    public void stopScan(MenuItem item) {
        scanBeacon(false);
        Toast.makeText(getApplicationContext(), "Stopped scan for beacons", Toast.LENGTH_SHORT).show();
    }

    //Test this
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
