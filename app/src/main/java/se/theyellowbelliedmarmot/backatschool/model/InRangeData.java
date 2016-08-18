package se.theyellowbelliedmarmot.backatschool.model;

/**
 * Created by joanne on 16/08/16.
 */
public final class InRangeData extends PresenceData {

    private final String major;
    private final String minor;

    public InRangeData(String api_key, String id_user, String beacon_uuid, String timestamp, String major, String minor) {
        super(api_key, id_user, beacon_uuid, timestamp);
        this.major = major;
        this.minor = minor;
    }

    public String getMajor() {
        return major;
    }

    public String getMinor() {
        return minor;
    }
}
