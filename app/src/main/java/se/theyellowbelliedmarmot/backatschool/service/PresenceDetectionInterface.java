package se.theyellowbelliedmarmot.backatschool.service;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import se.theyellowbelliedmarmot.backatschool.model.InRangeData;
import se.theyellowbelliedmarmot.backatschool.model.OutOfRangeData;

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

    @FormUrlEncoded
    @POST("beacon_nearby")
    Call<ResponseBody> beaconInRange(@Field("input") InRangeData input);

    @FormUrlEncoded
    @POST("beacon_outofrange")
    Call<ResponseBody> beaconOutOfRange(@Field("input") OutOfRangeData input);


}
