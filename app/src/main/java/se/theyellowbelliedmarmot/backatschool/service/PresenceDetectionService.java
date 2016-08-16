package se.theyellowbelliedmarmot.backatschool.service;

import android.content.Context;
import android.util.Log;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by joanne on 22/06/16.
 */
public class PresenceDetectionService {

    private Context context;
    private Retrofit retrofit;
    private static final String TAG = PresenceDetectionService.class.getSimpleName();

    public PresenceDetectionService(Context context, Retrofit retrofit){
        this.context = context;
        this.retrofit = retrofit;
    }

    public Context getContext() {
        return context;
    }


    public void inRangeDetected(String input){

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), input);
        PresenceDetectionInterface presenceDetectionInterface = retrofit.create(PresenceDetectionInterface.class);
        Call<ResponseBody> result = presenceDetectionInterface.beaconInRange(requestBody);
        sendRequest(result);

    }

    private void sendRequest(Call<ResponseBody> result){

        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String response_value = response.message();
                Log.d(TAG, "RETROFIT RESPONSE: " + response_value);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });

    }



    public void outOfRangeDetected(String input){
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), input);
        PresenceDetectionInterface presenceDetectionInterface = retrofit.create(PresenceDetectionInterface.class);
        Call<ResponseBody> result = presenceDetectionInterface.beaconOutOfRange(requestBody);
        sendRequest(result);

    }




//    public void inRangeDetected(String apikey, ScanResponse scanResponse, Context context) {
//
//        try{
//            Log.d(TAG, "IN RANGE DETECTED");
//            String input = JsonParser.detectionInputToJson(apikey, scanResponse);
//
//            Ion.with(context).load(URLS.IN_RANGE)
//                    .setLogging(TAG, Log.VERBOSE)
//                    .setTimeout(1000)
//                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
//                    .setStringBody(input)
//                    .asJsonObject()
//                    .setCallback(new FutureCallback<JsonObject>() {
//                        @Override
//                        public void onCompleted(Exception e, JsonObject result) {
//
//                            Log.d("result is null: ", String.valueOf(result == null));
//                            if (result != null) {
//                                Log.d("Result in Range: ", result.toString());
//                            }
//                        }
//                    });
//
//        }catch (Exception e){
//            Log.d(TAG, e.getMessage());
//        }
//
//    }
//
//    public void outOfRangeDetected(String apikey, ScanResponse scanResponse, Context context) {
//        Log.d(TAG, "OUT OF RANGE DETECTED");
//        String input = JsonParser.detectionInputToJson(apikey, scanResponse);
//        Ion.with(context).load(URLS.OUT_OF_RANGE)
//                .setTimeout(1000)
//                .setLogging(TAG, Log.VERBOSE)
//                .addHeader("Content-Type", "application/x-www-form-urlencoded")
//                .setStringBody(input)
//                .asJsonObject()
//                .setCallback(new FutureCallback<JsonObject>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonObject result) {
//                        Log.d("result is null: ", String.valueOf(result == null));
//
//                        if (result != null) {
//                            Log.d("Result out of Range: ", result.toString());
//                        }
//                    }
//                });
//    }

}
