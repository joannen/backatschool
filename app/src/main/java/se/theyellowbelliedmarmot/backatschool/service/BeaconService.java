package se.theyellowbelliedmarmot.backatschool.service;

import android.content.Context;
import android.util.Log;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by joanne on 10/06/16.
 */
public final class BeaconService {

    private static final String URL = "http://beacons.zenzor.io/sys/api/subscribe_beacon";
    private static String APIKEY = "28742sk238sdkAdhfue243jdfhvnsa1923347";

    public void subscribeToBeacon(String user_id, String beaconUuid ,Context context) {

        String input = inputToJson(APIKEY,user_id, beaconUuid);

        Ion.with(context).load(URL)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .setStringBody(input)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        if (result != null) {
                            Log.d("Result subscr: " , result);
                        }
                    }
                });
    }


    private String inputToJson(String apiKey, String userId, String beaconUuid){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("api_key", apiKey);
        jsonObject.addProperty("id_user", userId);
        jsonObject.addProperty("beacon_uuid", beaconUuid);
        return "input="+jsonObject.toString();
    }

}
