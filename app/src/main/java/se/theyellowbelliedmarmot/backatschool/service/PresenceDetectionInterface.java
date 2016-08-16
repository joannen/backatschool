package se.theyellowbelliedmarmot.backatschool.service;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by joanne on 16/08/16.
 */
public interface PresenceDetectionInterface {
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @POST("beacon_nearby")
    Call<ResponseBody> beaconInRange(@Body RequestBody body);

    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @POST("beacon_outofrange")
    Call<ResponseBody> beaconOutOfRange(@Body RequestBody body);
}
