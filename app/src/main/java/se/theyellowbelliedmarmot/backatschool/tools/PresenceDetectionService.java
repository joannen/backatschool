package se.theyellowbelliedmarmot.backatschool.tools;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


import se.theyellowbelliedmarmot.backatschool.model.ScanResponse;
import se.theyellowbelliedmarmot.backatschool.tools.JsonParser;
/**
 * Created by TheYellowBelliedMarmot on 2016-06-22.
 */
public class PresenceDetectionService {
    protected static final String APIKEY = "28742sk238sdkAdhfue243jdfhvnsa1923347";
    private static final String URL_IN_RANGE = "http://beacons.zenzor.io/sys/api/beacon_nearby";
    private static final String URL_OUT_OF_RANGE = "http://beacons.zenzor.io/sys/api/beacon_outofrange";



    public static void inRangeDetected(String apikey ,ScanResponse scanResponse, Context context){
        String input = JsonParser.detectionInputToJson(apikey, scanResponse);
        Ion.with(context).load(URL_IN_RANGE)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .setStringBody(input)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result != null) {
                            Log.d("Result in Range: " , result.toString());
                        }
                    }
                });


    }

    public static void  outOfRangeDetected(String apikey, ScanResponse scanResponse, Context context){
        String input = JsonParser.detectionInputToJson(apikey, scanResponse);
        Ion.with(context).load(URL_OUT_OF_RANGE)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .setStringBody(input)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result != null) {
                            Log.d("Result out of Range: " , result.toString());
                        }
                    }
                });


    }

}
