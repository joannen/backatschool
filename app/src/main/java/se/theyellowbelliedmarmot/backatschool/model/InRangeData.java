package se.theyellowbelliedmarmot.backatschool.model;

/**
 * Created by joanne on 16/08/16.
 */
public class InRangeData extends PresenceData {

    private String major;
    private String minor;

    public InRangeData(String api_key, String id_user, String beacon_uuid, String timestamp, String major, String minor) {
        super(api_key, id_user, beacon_uuid, timestamp);
        this.major = major;
        this.minor = minor;
    }
}
