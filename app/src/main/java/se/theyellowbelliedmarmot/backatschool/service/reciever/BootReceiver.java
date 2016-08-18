package se.theyellowbelliedmarmot.backatschool.service.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import se.theyellowbelliedmarmot.backatschool.service.BackgroundScanningService;

/**
 * Created by joanne on 18/08/16.
 */
public class BootReceiver extends BroadcastReceiver {

    //starts scanning service when phone turned on
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, BackgroundScanningService.class));
    }
}
