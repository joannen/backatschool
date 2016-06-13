package se.theyellowbelliedmarmot.backatschool.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import se.theyellowbelliedmarmot.backatschool.R;
import se.theyellowbelliedmarmot.backatschool.model.Beacon;
import se.theyellowbelliedmarmot.backatschool.service.BeaconService;
import se.theyellowbelliedmarmot.backatschool.tools.JsonParser;

public class SubscribedBeacons extends BaseActivity {

    List<Beacon> beaconList;
    BeaconService beaconService;
    private static final String URL = "http://beacons.zenzor.io/sys/api/subscribe_beacon";

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
            Log.d(TAG, "userid: "+ readUserId());
            subscribeToBeacon(readUserId(),uuid, this );

            Beacon beacon = new Beacon(uuid, major, minor,Integer.parseInt(rssi), name);
            beaconList.add(beacon);


        }
        saveBeacon(beaconList);
        readBeacons();
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
}
