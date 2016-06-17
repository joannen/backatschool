package se.theyellowbelliedmarmot.backatschool.application;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.powersave.BackgroundPowerSaver;
import org.altbeacon.beacon.startup.BootstrapNotifier;
import org.altbeacon.beacon.startup.RegionBootstrap;

import se.theyellowbelliedmarmot.backatschool.activity.BackgroundActivity;

/**
 * Created by joanne on 13/06/16.
 */
public class ScanningApplication extends Application implements BootstrapNotifier {
    private static final String TAG = "LOGTAG";
    private RegionBootstrap regionBootstrap;
    private BackgroundPowerSaver backgroundPowerSaver;


    @Override
    public void onCreate() {
        super.onCreate();

        backgroundPowerSaver = new BackgroundPowerSaver(this);

        BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));

        Region region = new Region("closebeacon", null , null, null);
        regionBootstrap = new RegionBootstrap(this, region);
    }

    @Override
    public void didEnterRegion(Region region) {
        Log.d(TAG, "did enter region");
        Intent intent = new Intent(this, BackgroundActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);


    }

    @Override
    public void didExitRegion(Region region) {

    }

    @Override
    public void didDetermineStateForRegion(int i, Region region) {

    }
}
