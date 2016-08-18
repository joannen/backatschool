package se.theyellowbelliedmarmot.backatschool.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import java.util.List;

/**
 * Created by joanne on 18/08/16.
 */
public final class BLEService {

    private BluetoothLeScanner scanner;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothManager bluetoothManager;
    private ScanSettings scanSettings;
    private List<ScanFilter> scanFilters;
    private Handler handler;
    private int SCAN_PERIOD = 5000;
    private Context context;
    private int scanMode;


    public BLEService(Handler handler, BluetoothManager manager, List<ScanFilter> scanFilters, int scanMode, Context context){
        this.bluetoothManager = manager;
        this.handler = handler;
        this.scanFilters = scanFilters;
        this.scanMode = scanMode;
        this.context = context;
        setUpBLEScan();
    }

    private void setUpBLEScan(){
        bluetoothAdapter = bluetoothManager.getAdapter();
        scanner = bluetoothAdapter.getBluetoothLeScanner();
        scanSettings = new ScanSettings.Builder()
                .setScanMode(scanMode)
                .build();
    }

    public BluetoothLeScanner getScanner() {
        return scanner;
    }

    public BluetoothAdapter getBluetoothAdapter() {
        return bluetoothAdapter;
    }

    public BluetoothManager getBluetoothManager() {
        return bluetoothManager;
    }

    public ScanSettings getScanSettings() {
        return scanSettings;
    }

    public List<ScanFilter> getScanFilters() {
        return scanFilters;
    }

    public Handler getHandler() {
        return handler;
    }

    public void scanBeacon(boolean scan, final ScanCallback scanCallback){
        if (scan) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    scanner.stopScan(scanCallback);
                }
            }, SCAN_PERIOD);
            scanner.startScan(scanFilters, scanSettings, scanCallback);
            // Test this
            Toast.makeText(context, "Scanning for beacons", Toast.LENGTH_SHORT).show();
        } else {
            scanner.stopScan(scanCallback);
        }
    }

}
