package se.theyellowbelliedmarmot.backatschool.activity;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import java.util.ArrayList;
import java.util.List;
import se.theyellowbelliedmarmot.backatschool.R;
import se.theyellowbelliedmarmot.backatschool.fragment.BeaconNameFragment;
import se.theyellowbelliedmarmot.backatschool.model.Beacon;
import se.theyellowbelliedmarmot.backatschool.model.adapter.SubscribedBeaconAdapter;
import se.theyellowbelliedmarmot.backatschool.service.BackgroundScanningService;
import se.theyellowbelliedmarmot.backatschool.tools.JsonParser;

public class SubscribedBeacons extends BaseActivity implements BeaconNameFragment.NoticeDialogListener {

    List<Beacon> existingBeacons;
    List<String> devices;
    private static final String URL = "http://beacons.zenzor.io/sys/api/subscribe_beacon";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribed_beacons);
        setTitle(R.string.subscribed_beacons);

        if (readBeacons() == null){
            existingBeacons = new ArrayList<>();
        }else {
            existingBeacons = readBeacons();
        }

        if(readDeviceAddresses()==null){
            devices = new ArrayList<>();
        }else{
            devices = readDeviceAddresses();
        }

        recyclerView = (RecyclerView) findViewById(R.id.subscribed_beacon_list);
        layoutManager = new LinearLayoutManager(this);
        adapter = new SubscribedBeaconAdapter(this, existingBeacons);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        //check if user clicked on new beacon and save that beacon in shared pref
        if(getIntent().hasExtra("has_beacon")){
            String name = getIntent().getStringExtra("name");
            String uuid = getIntent().getStringExtra("uuid");
            String major = getIntent().getStringExtra("major");
            String minor = getIntent().getStringExtra("minor");
            String rssi = getIntent().getStringExtra("rssi");
            String deviceAddress = getIntent().getStringExtra("deviceAddress");
            subscribeToBeacon(readUserId(),uuid, this );

            Beacon beacon = new Beacon(uuid, major, minor,Integer.parseInt(rssi), name, deviceAddress);
            //add beacon to recycler view
            addBeaconToSubscriptionList(beacon);
            //add beacon to shared pref
            saveBeacon(existingBeacons);
            //save deviceaddress in shared pref for background scanning service
            devices.add(beacon.getDeviceAddress());
            saveDeviceAddress(devices);
        }
        else {
            //just update recyclerview
            adapter.notifyDataSetChanged();
        }
        //get all beacons from shared pref
        Intent intent = new Intent(this, BackgroundScanningService.class);
        startService(intent);
    }

    public void subscribeToBeacon(String user_id, String beaconUuid ,Context context) {
        String input = JsonParser.subscriptionInputToJson(APIKEY, user_id, beaconUuid);

        Ion.with(context).load(URL)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .setStringBody(input)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result != null) {
                            Log.d("Result subscr: " , result.toString());
                        }
                    }
                });
    }

    private void addBeaconToSubscriptionList(Beacon beacon){
        if(!existingBeacons.contains(beacon)){
            existingBeacons.add(beacon);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment fragment, Beacon beacon, String name) {
        for (Beacon b : existingBeacons) {
            if (b.equals(beacon)){
                existingBeacons.remove(b);
                Beacon newBeacon = new Beacon(beacon.getUuid(), beacon.getMajor(), beacon.getMinor(), 1 , name, beacon.getDeviceAddress());
                existingBeacons.add(newBeacon);
                saveBeacon(existingBeacons);
                adapter.notifyDataSetChanged();
            }
        }


    }
}
