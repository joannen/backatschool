package se.theyellowbelliedmarmot.backatschool;

import org.junit.Before;
import org.junit.Test;

import se.theyellowbelliedmarmot.backatschool.model.Beacon;
import se.theyellowbelliedmarmot.backatschool.model.ScanResponse;

import static junit.framework.Assert.assertTrue;

/**
 * Created by joanne on 22/06/16.
 */

public class ScanResponseTest {

    private ScanResponse scanResponse;
    private Beacon beacon;
    private String userId;

    @Before
    public void setup(){
        Beacon beacon = new Beacon("uuid", "2", "1", 20, "beacon", "deviceaddress");
        userId = "1";
    }

    @Test
    public void ScanResponseShouldCreateTimestamp(){
        scanResponse = new ScanResponse(beacon, userId, Range.IN);
        assertTrue(scanResponse.getTimestamp().contains("2016"));
    }

}
