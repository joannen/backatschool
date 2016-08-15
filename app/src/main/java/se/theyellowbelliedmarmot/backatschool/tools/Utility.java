package se.theyellowbelliedmarmot.backatschool.tools;

import android.bluetooth.le.ScanResult;

import java.util.Arrays;

import se.theyellowbelliedmarmot.backatschool.model.Beacon;

/**
 * Created by joanne on 05/06/16.
 */
public final class Utility {

    private static String bytesToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static Beacon resultToBeacon(ScanResult result, byte[] manufacturerSpecificData) {

        int major = (manufacturerSpecificData[18] & 0xff) * 0x100 + (manufacturerSpecificData[19] & 0xff);
        int minor = (manufacturerSpecificData[20] & 0xff) * 0x100 + (manufacturerSpecificData[21] & 0xff);
        String uuid = Utility.bytesToHex(Arrays.copyOfRange(manufacturerSpecificData, 2, 18));
        String deviceAddress = result.getDevice().getAddress();
        String deviceName = result.getDevice().getName();
        Beacon beacon = new Beacon(uuid, Integer.toString(major), Integer.toString(minor), result.getRssi(), deviceName, deviceAddress);
        return beacon;

    }
}
