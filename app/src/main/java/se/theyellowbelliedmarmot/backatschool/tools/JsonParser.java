package se.theyellowbelliedmarmot.backatschool.tools;

import com.google.gson.JsonObject;

/**
 * Created by joanne on 10/06/16.
 */
public final class JsonParser {

    private static final String INPUT = "input=";

    public static String userInputToJson(String apiKey, String firstName, String lastName){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("api_key", apiKey);
        jsonObject.addProperty("first_name", firstName);
        jsonObject.addProperty("last_name", lastName);
        String inputString = INPUT + jsonObject.toString();
        return inputString;
    }

    public static String subscriptionInputToJson(String apiKey, String userId, String beaconUuid){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("api_key", apiKey);
        jsonObject.addProperty("id_user", userId);
        jsonObject.addProperty("beacon_uuid", beaconUuid);
        String inputString = INPUT + jsonObject.toString();
        return inputString;
    }


}
