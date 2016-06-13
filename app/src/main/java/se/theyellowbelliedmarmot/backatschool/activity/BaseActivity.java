package se.theyellowbelliedmarmot.backatschool.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import se.theyellowbelliedmarmot.backatschool.model.Beacon;
import se.theyellowbelliedmarmot.backatschool.tools.JsonParser;

/**
 * Created by joanne on 10/06/16.
 */
public class BaseActivity extends AppCompatActivity{

    public static final String TAG = "LOGTAG";
    protected static final String APIKEY = "28742sk238sdkAdhfue243jdfhvnsa1923347";


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    protected String readUser(){
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        String firstName = sharedPreferences.getString("first_name","");
        String lastName = sharedPreferences.getString("last_name", "");
        return  firstName + lastName;
    }


    protected void saveUser(String firstName, String lastName){
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("first_name", firstName);
        editor.putString("last_name", lastName);
        editor.commit();
    }

    public void saveUserId(Context context,String id){
        SharedPreferences sharedPreferences = context.getSharedPreferences("id", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", id);
        editor.commit();
    }

    protected String readUserId(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("id", Context.MODE_PRIVATE);
        String id = sharedPreferences.getString("id", "");
        return id;
    }

    protected List<Beacon> readBeacons(){
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        Set<String> jsonBeacons = sharedPreferences.getStringSet("subscribed_beacons", null);
        List<Beacon> beacons = new ArrayList<>();
        for (String  s: jsonBeacons) {
            JsonObject json = new JsonObject();
            Log.d("JSON: ", s);
            beacons.add(JsonParser.jsonToBeacon(json));
        }
        return beacons;
    }

    protected void saveBeacon(List<Beacon> beaconList){
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
            Log.d("JSON AS STRING: " , jsonObject.toString());
        }

        editor.putStringSet("subscribed_beacons", jsonBeacons);
        editor.commit();
    }

}
