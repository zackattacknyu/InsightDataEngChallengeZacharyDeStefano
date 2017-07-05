/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdnetworkdata;

import java.sql.Timestamp;

/**
 * Methods to help with handling the timestamps
 * @author Zach
 */
public class TimestampHelper {
    
    /**
     * Takes the timestamp from the Json files and returns 
     *      milliseconds since 1970
     * @param timestamp     timestamp string from Json file
     * @return  millis since 1970 long
     */
    public static long getMillisTime(String timestamp){
        Timestamp currentTime = Timestamp.valueOf(timestamp);
        return currentTime.getTime();
    }
    
    
    
}
