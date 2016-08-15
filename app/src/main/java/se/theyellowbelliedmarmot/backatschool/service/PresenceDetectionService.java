package se.theyellowbelliedmarmot.backatschool.service;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import se.theyellowbelliedmarmot.backatschool.constants.URLS;
import se.theyellowbelliedmarmot.backatschool.model.ScanResponse;
import se.theyellowbelliedmarmot.backatschool.tools.JsonParser;

/**
 * Created by joanne on 22/06/16.
 */
public class PresenceDetectionService {

    private Context context;

    public PresenceDetectionService(Context context){
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void inRangeDetected(String apikey, ScanResponse scanResponse, Context context) {
        Log.d("IN METHOD IN RANGE", "TRYING TO CONNECT TO SERVER");
        String input = JsonParser.detectionInputToJson(apikey, scanResponse);
        Log.d("INPUT", input);
        Ion.with(context).load(URLS.IN_RANGE)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .setStringBody(input)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Log.d("I'M HERE!!!", "IN RANGE");

                        Log.d("result is null: ", String.valueOf(result == null));
                        if (result != null) {
                            Log.d("Result in Range: ", result.toString());
                        }
                    }
                });
    }

    public void outOfRangeDetected(String apikey, ScanResponse scanResponse, Context context) {
        Log.d("IN METHOD OUT OF RANGE", "TRYING TO CONNECT TO SERVER");

        String input = JsonParser.detectionInputToJson(apikey, scanResponse);
        Ion.with(context).load(URLS.OUT_OF_RANGE)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .setStringBody(input)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        Log.d("I'M HERE!!!", "OUT OF RANGE");
                        Log.d("result is null: ", String.valueOf(result == null));

                        if (result != null) {
                            Log.d("Result out of Range: ", result.toString());
                        }
                    }
                });
    }

}
