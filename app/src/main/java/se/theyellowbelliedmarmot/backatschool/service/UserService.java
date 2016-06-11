package se.theyellowbelliedmarmot.backatschool.service;
//
//import android.content.Context;
//import android.util.Log;
//import com.google.gson.JsonObject;
//import com.koushikdutta.async.future.FutureCallback;
//import com.koushikdutta.ion.Ion;
//import se.theyellowbelliedmarmot.backatschool.model.User;
//
///**
// * Created by TheYellowBelliedMarmot on 2016-06-01.
// */
//public final class UserService {
//
//    private static final String TAG = UserService.class.getSimpleName();
//    private static String URL = "http://beacons.zenzor.io/sys/api/register_user";
//    private static String APIKEY = "28742sk238sdkAdhfue243jdfhvnsa1923347";
//
//    public void registerUser(final User user, final Context context) {
////        String input = "input={\"api_key\":\"" + APIKEY + "\",\"first_name\":\""
////                        + user.getFirstName() + "\",\"last_name\":\"" + user.getLastName() + "\"}";
//        String input = inputToString(APIKEY, user);
//
//            Ion.with(context).load(URL)
//                .addHeader("Content-Type", "application/x-www-form-urlencoded")
//                .setStringBody(input)
//                .asJsonObject()
//                .setCallback(new FutureCallback<JsonObject>() {
//                    @Override
//                    public void onCompleted(Exception e, JsonObject result) {
//                        if (result != null) {
//                            Log.d(TAG, result.toString());
//
//
//                        } else {
//                            Log.d(TAG, "no result");
//                        }
//                    }
//                });
//
//    }
//
//    private String inputToString(String apiKey, User user){
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("api_key", apiKey);
//        jsonObject.addProperty("first_name", user.getFirstName());
//        jsonObject.addProperty("last_name", user.getLastName());
//        String inputString = "input =" + jsonObject.toString();
//        Log.d(TAG, inputString);
//        return "input=" + jsonObject.toString();
//    }
//
//}
//
