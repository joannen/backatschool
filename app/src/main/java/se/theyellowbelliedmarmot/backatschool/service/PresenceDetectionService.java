package se.theyellowbelliedmarmot.backatschool.service;

import android.util.Log;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import se.theyellowbelliedmarmot.backatschool.model.InRangeData;
import se.theyellowbelliedmarmot.backatschool.model.OutOfRangeData;

/**
 * Created by joanne on 22/06/16.
 */
public final class PresenceDetectionService {

    private Retrofit retrofit;
    private static final String TAG = PresenceDetectionService.class.getSimpleName();
    public PresenceDetectionService(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public void inRangeDetected(InRangeData input) {
        PresenceDetectionInterface presenceDetectionInterface = retrofit.create(PresenceDetectionInterface.class);
        Call<ResponseBody> result = presenceDetectionInterface.beaconInRange(input);
        sendRequest(result);
    }

    public void outOfRangeDetected(OutOfRangeData input) {
        PresenceDetectionInterface presenceDetectionInterface = retrofit.create(PresenceDetectionInterface.class);
        Call<ResponseBody> result = presenceDetectionInterface.beaconOutOfRange(input);
        sendRequest(result);
    }

    private void sendRequest(Call<ResponseBody> result) {
        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String response_value = response.message();
                int response_code = response.code();
                Log.d(TAG, "RETROFIT RESPONSE: " + response_value + "\nCODE: " + response_code);
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }
}
