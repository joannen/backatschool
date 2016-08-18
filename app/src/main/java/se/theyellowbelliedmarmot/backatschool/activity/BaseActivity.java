package se.theyellowbelliedmarmot.backatschool.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import se.theyellowbelliedmarmot.backatschool.R;
import se.theyellowbelliedmarmot.backatschool.model.Beacon;
import se.theyellowbelliedmarmot.backatschool.tools.JsonParser;

/**
 * Class that other activities extend so that shared preferences can be easily accessed
 */


public abstract class BaseActivity extends AppCompatActivity {

    static final String TAG = "LOGTAG";
    static final String APIKEY = "28742sk238sdkAdhfue243jdfhvnsa1923347";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    protected String readUser() {
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        String firstName = sharedPreferences.getString("first_name", "");
        String lastName = sharedPreferences.getString("last_name", "");
        return firstName + lastName;
    }

    protected void saveUser(String firstName, String lastName) {
        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
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

    protected String readUserId() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("id", Context.MODE_PRIVATE);
        return sharedPreferences.getString("id", "");
    }

    protected List<Beacon> readBeacons() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("beacons", Context.MODE_PRIVATE);
        Set<String> jsonBeacons = sharedPreferences.getStringSet("subscribed_beacons", null);
        List<Beacon> beacons = new ArrayList<>();
        if (jsonBeacons != null) {
            com.google.gson.JsonParser parser = new com.google.gson.JsonParser();

            for (String s : jsonBeacons) {
                JsonObject json = parser.parse(s).getAsJsonObject();
                Log.d(TAG, json.toString());
                beacons.add(JsonParser.jsonToBeacon(json));
            }
        }
        return beacons;
    }

    protected void saveBeacon(List<Beacon> beaconList) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("beacons", Context.MODE_PRIVATE);
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

    protected void saveDeviceAddress(List<String> deviceAddresses) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("devices", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> devices = new HashSet<>();
        for (String s : deviceAddresses) {
            devices.add(s);
        }
        editor.putStringSet("devices", devices);
        editor.commit();
    }

    public void startScanActivity(MenuItem item){
        Intent intent = new Intent(this, ScanActiveBeacon.class);
        startActivity(intent);
    }

    public void startSubscriptionListActivity(MenuItem item) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("beacons", Context.MODE_PRIVATE);
        Set<String> jsonBeacons = sharedPreferences.getStringSet("subscribed_beacons", null);
        if (jsonBeacons != null) {
            Intent intent = new Intent(this, SubscribedBeacons.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Subscribed Beaconlist is empty!", Toast.LENGTH_LONG).show();
        }
    }


        protected List<String> readDeviceAddresses() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("devices", Context.MODE_PRIVATE);
        Set<String> devices = sharedPreferences.getStringSet("devices", null);
        return devices == null ? new ArrayList<String>() : new ArrayList<>(devices);
    }




}
