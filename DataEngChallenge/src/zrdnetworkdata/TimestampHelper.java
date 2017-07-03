/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdnetworkdata;

import java.sql.Timestamp;

/**
 *
 * @author Zach
 */
public class TimestampHelper {
    
    public static long getMillisTime(String timestamp){
        Timestamp currentTime = Timestamp.valueOf(timestamp);
        return currentTime.getTime();
    }
    
    
    
}
