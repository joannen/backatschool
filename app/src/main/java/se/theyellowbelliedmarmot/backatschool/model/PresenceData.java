package se.theyellowbelliedmarmot.backatschool.model;

/**
 * Created by joanne on 16/08/16.
 */
public class PresenceData {

    String api_key;
    String id_user;
    String beacon_uuid;
    String timestamp;

    public PresenceData(String api_key, String id_user, String beacon_uuid, String timestamp) {
        this.api_key = api_key;
        this.id_user = id_user;
        this.beacon_uuid = beacon_uuid;
        this.timestamp = timestamp;
    }

    public String getApi_key() {
        return api_key;
    }

    public String getId_user() {
        return id_user;
    }

    public String getBeacon_uuid() {
        return beacon_uuid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "PresenceData{" +
                "api_key='" + api_key + '\'' +
                ", id_user='" + id_user + '\'' +
                ", beacon_uuid='" + beacon_uuid + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
