package se.theyellowbelliedmarmot.backatschool.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by joanne on 13/06/16.
 */
public class BackgroundScanningService extends IntentService {


    public BackgroundScanningService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
