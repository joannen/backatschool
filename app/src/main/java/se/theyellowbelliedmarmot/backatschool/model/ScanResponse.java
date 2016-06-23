package se.theyellowbelliedmarmot.backatschool.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import se.theyellowbelliedmarmot.backatschool.service.Range;

public final class ScanResponse {
    private Beacon beacon;
    private String userId;
    private String timestamp;
    private Range range;

    public ScanResponse (Beacon beacon, String id, Range range){
        this.beacon = beacon;
        this.userId = id;
        this.range = range;
        this.timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    }

    public Beacon getBeacon() {
        return beacon;
    }

    public String getUserId() {
        return userId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Range getRange() {
        return range;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScanResponse that = (ScanResponse) o;

        if (beacon != null ? !beacon.equals(that.beacon) : that.beacon != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        return timestamp != null ? timestamp.equals(that.timestamp) : that.timestamp == null;

    }

    @Override
    public int hashCode() {
        int result = beacon != null ? beacon.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ScanResponse{" +
                "beacon=" + beacon +
                ", userId='" + userId + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
