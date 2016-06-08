package se.theyellowbelliedmarmot.backatschool.model;

/**
 * Created by joanne on 02/06/16.
 */
public final class Beacon implements Comparable<Beacon> {

    private String uuid;
    private String major;
    private String minor;
    private int rssi;
    private String name;

    public Beacon(String uuid, String major, String minor, int rssi, String name) {
        this.uuid = uuid;
        this.major = major;
        this.minor = minor;
        this.rssi = rssi;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }

    public String getMajor() {
        return major;
    }

    public String getMinor() {
        return minor;
    }

    public int getRssi() {
        return rssi;
    }

    @Override
    public String toString() {
        return "Beacon{" +
                "uuid='" + uuid + '\'' +
                ", major='" + major + '\'' +
                ", minor='" + minor + '\'' +
                ", rssi=" + rssi +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Beacon beacon = (Beacon) o;

        if (uuid != null ? !uuid.equals(beacon.uuid) : beacon.uuid != null) return false;
        if (major != null ? !major.equals(beacon.major) : beacon.major != null) return false;
        return minor != null ? minor.equals(beacon.minor) : beacon.minor == null;

    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (major != null ? major.hashCode() : 0);
        result = 31 * result + (minor != null ? minor.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Beacon beacon) {
        return rssi - beacon.getRssi();
    }
}
