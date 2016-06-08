package se.theyellowbelliedmarmot.backatschool.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import se.theyellowbelliedmarmot.backatschool.R;

public class SubscribedBeacons extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribed_beacons);

        if(getIntent().hasExtra("uuid")){
            Log.d("has extra", getIntent().getStringExtra("uuid"));
        }
    }
}
