package se.theyellowbelliedmarmot.backatschool.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import se.theyellowbelliedmarmot.backatschool.R;
import se.theyellowbelliedmarmot.backatschool.model.Beacon;
import se.theyellowbelliedmarmot.backatschool.service.BeaconService;

public class SubscribedBeacons extends AppCompatActivity {

    List<Beacon> beaconList;
    BeaconService beaconService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribed_beacons);


        beaconList = new ArrayList<>();
        beaconService = new BeaconService();


        if(getIntent().hasExtra("has_beacon")){
            Log.d("has extra", getIntent().getStringExtra("uuid"));
            String name = getIntent().getStringExtra("name");
            String uuid = getIntent().getStringExtra("uuid");
            String major = getIntent().getStringExtra("major");
            String minor = getIntent().getStringExtra("minor");
            String rssi = getIntent().getStringExtra("rssi");
            beaconService.subscribeToBeacon("94",uuid, this );

            Beacon beacon = new Beacon(uuid, major, minor,Integer.parseInt(rssi), name);
            beaconList.add(beacon);


        }
        saveBeacon(beaconList);
        readBeacons();
    }

    private void saveBeacon(List<Beacon> beaconList){
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> jsonBeacons = new HashSet<>();
        for (Beacon  beacon: beaconList) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("name", beacon.getName());
            jsonObject.addProperty("uuid", beacon.getUuid());
            jsonObject.addProperty("major", beacon.getMajor());
            jsonObject.addProperty("minor", beacon.getMinor());
            jsonObject.addProperty("rssi", beacon.getRssi());
//            jsonBeacons.add(jsonObject.getAsString());
            Log.d("JSON AS STRING: " , jsonObject.toString());
        }


        editor.putStringSet("subscribed_beacons", jsonBeacons);
        editor.commit();
    }


    private void readBeacons(){
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        Set<String> jsonBeacons = sharedPreferences.getStringSet("subscribed_beacons", null);
        List<Beacon> beacons = new ArrayList<>();
        for (String  s: jsonBeacons) {
            JsonObject json = new JsonObject();
            Log.d("JSON: ", s);


        }

    }

}
