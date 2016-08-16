package se.theyellowbelliedmarmot.backatschool.service;

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
public final class PresenceDetectionService {

    private Retrofit retrofit;
    private static final String TAG = PresenceDetectionService.class.getSimpleName();

    public PresenceDetectionService(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public void inRangeDetected(String input) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), input);
        PresenceDetectionInterface presenceDetectionInterface = retrofit.create(PresenceDetectionInterface.class);
        Call<ResponseBody> result = presenceDetectionInterface.beaconInRange(requestBody);
        sendRequest(result);
    }

    public void outOfRangeDetected(String input) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), input);
        PresenceDetectionInterface presenceDetectionInterface = retrofit.create(PresenceDetectionInterface.class);
        Call<ResponseBody> result = presenceDetectionInterface.beaconOutOfRange(requestBody);
        sendRequest(result);
    }

    private void sendRequest(Call<ResponseBody> result) {
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


}
