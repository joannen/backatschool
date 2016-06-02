package se.theyellowbelliedmarmot.backatschool.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import se.theyellowbelliedmarmot.backatschool.R;

public class ScanActiveBeacon extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_active_beacon);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.scan, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.scan_beacon_action){
            Toast.makeText(getApplicationContext(), "Scanning for beacons", Toast.LENGTH_SHORT).show();
        }
        if (item.getItemId() == R.id.stop_scan_action){
            Toast.makeText(getApplicationContext(), "Stopped scan for beacons", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
