package se.theyellowbelliedmarmot.backatschool.service.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import se.theyellowbelliedmarmot.backatschool.service.BackgroundScanningService;

/**
 * Created by joanne on 18/08/16.
 */
public class BootReciever extends BroadcastReceiver {

    private final static String TAG = BootReciever.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Starting service");
        context.startService(new Intent(context, BackgroundScanningService.class));
    }
}
