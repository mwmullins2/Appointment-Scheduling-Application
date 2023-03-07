package Utility;

import java.sql.Timestamp;

/** This class is used when converting server time and client time.
 *
 */
public class TimeConversion {

    /** Used to convert server time to client time.
     * @return Timestamp
     */
    public static Timestamp getServerTime() {
        return new Timestamp(System.currentTimeMillis());
    }
}
