package se.theyellowbelliedmarmot.backatschool.tools;

import com.google.gson.JsonObject;

import se.theyellowbelliedmarmot.backatschool.model.Beacon;
import se.theyellowbelliedmarmot.backatschool.model.ScanResponse;
import se.theyellowbelliedmarmot.backatschool.service.Range;

/**
 * Created by joanne on 10/06/16.
 */
public final class JsonParser {

    private static final String INPUT = "input=";

    public static String userInputToJson(String apiKey, String firstName, String lastName) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("api_key", apiKey);
        jsonObject.addProperty("first_name", firstName);
        jsonObject.addProperty("last_name", lastName);
        String inputString = INPUT + jsonObject.toString();
        return inputString;
    }

    public static String subscriptionInputToJson(String apiKey, String userId, String beaconUuid) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("api_key", apiKey);
        jsonObject.addProperty("id_user", userId);
        jsonObject.addProperty("beacon_uuid", beaconUuid);
        String inputString = INPUT + jsonObject.toString();
        return inputString;
    }

    public static Beacon jsonToBeacon(JsonObject jsonObject) {
        Beacon beacon;
        String name = jsonObject.get("name").getAsString();
        String major = jsonObject.get("major").getAsString();
        String minor = jsonObject.get("minor").getAsString();
        String uuid = jsonObject.get("uuid").getAsString();
        int rssi = jsonObject.get("rssi").getAsInt();
        String deviceAddress = jsonObject.get("device_address").getAsString();

        beacon = new Beacon(uuid, major, minor, rssi, name, deviceAddress);
        return beacon;
    }

    public static JsonObject beaconToJson(Beacon beacon) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", beacon.getName());
        jsonObject.addProperty("uuid", beacon.getUuid());
        jsonObject.addProperty("major", beacon.getMajor());
        jsonObject.addProperty("minor", beacon.getMinor());
        jsonObject.addProperty("rssi", beacon.getRssi());
        jsonObject.addProperty("device_address", beacon.getDeviceAddress());
        return jsonObject;

    }

    public static String detectionInputToJson(String apikey, ScanResponse scanResponse) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("api_key", apikey);
        jsonObject.addProperty("id_user", scanResponse.getUserId());
        jsonObject.addProperty("beacon_uuid", scanResponse.getBeacon().getUuid());

        if (scanResponse.getRange().equals(Range.IN)) {
            jsonObject.addProperty("major", scanResponse.getBeacon().getMajor());
            jsonObject.addProperty("minor", scanResponse.getBeacon().getMinor());
            jsonObject.addProperty("rssi", scanResponse.getBeacon().getRssi());
        }

        jsonObject.addProperty("timestamp", scanResponse.getTimestamp());


        return INPUT + jsonObject.toString();

    }


}
