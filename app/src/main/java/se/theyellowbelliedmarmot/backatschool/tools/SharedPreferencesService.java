package se.theyellowbelliedmarmot.backatschool.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import se.theyellowbelliedmarmot.backatschool.model.Beacon;

/**
 * Created by joanne on 15/08/16.
 */
public class SharedPreferencesService {

    protected String readUser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        String firstName = sharedPreferences.getString("first_name", "");
        String lastName = sharedPreferences.getString("last_name", "");
        return firstName + lastName;
    }

    protected void saveUser(Context context,String firstName, String lastName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("first_name", firstName);
        editor.putString("last_name", lastName);
        editor.commit();
    }

    public void saveUserId(Context context, String id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("id", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", id);
        editor.commit();
    }

    protected String readUserId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("id", Context.MODE_PRIVATE);
        String id = sharedPreferences.getString("id", "");
        return id;
    }

    protected List<Beacon> readBeacons(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("beacons", Context.MODE_PRIVATE);
        Set<String> jsonBeacons = sharedPreferences.getStringSet("subscribed_beacons", null);
        List<Beacon> beacons = new ArrayList<>();
        if (jsonBeacons != null) {
            com.google.gson.JsonParser parser = new com.google.gson.JsonParser();

            for (String s : jsonBeacons) {

                JsonObject json = parser.parse(s).getAsJsonObject();
                beacons.add(JsonParser.jsonToBeacon(json));
            }
        }

        return beacons;
    }

    protected void saveBeacon(Context context, List<Beacon> beaconList) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("subscribed_beacons", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> jsonBeacons = new HashSet<>();

        for (Beacon beacon : beaconList) {
            JsonObject jsonObject = JsonParser.beaconToJson(beacon);
            Log.d("JSON AS STRING: ", jsonObject.toString());
            jsonBeacons.add(jsonObject.toString());
        }

        editor.putStringSet("subscribed_beacons", jsonBeacons);
        editor.commit();
    }

    protected void saveDeviceAddress(Context context, List<String> deviceAddresses) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("devices", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> devices = new HashSet<>();
        for (String s : deviceAddresses) {
            devices.add(s);
        }
        editor.putStringSet("devices", devices);
        editor.commit();
    }

    protected List<String> readDeviceAddresses(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("devices", Context.MODE_PRIVATE);
        Set<String> devices = sharedPreferences.getStringSet("devices", null);
        return devices == null ? new ArrayList<String>() : new ArrayList<>(devices);
    }



}
