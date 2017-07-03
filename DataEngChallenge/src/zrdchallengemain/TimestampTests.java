/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdchallengemain;

import java.sql.Timestamp;
import java.util.Calendar;
import zrdnetworkdata.TimestampHelper;

/**
 *
 * @author Zach
 */
public class TimestampTests {
    
    /**
     * This takes a few example timestamps and makes sure they are read
     *      correctly in Java
     * 
     * Here are the examples from the file:
     * 2017-06-14 18:46:40
     * 2017-06-14 18:46:46
     * 2017-06-14 18:46:50
     * 
     * Here are some random examples
     * 2004-12-12 00:01:20
     * 2014-05-01 03:23:43
     * 
     * I will have it display the following:
     *      - Year, Month, Day, Hour, Minute, Second for each
     */
    public static void runTests(){
        
        String[] exampleTimestamps = {
            "2017-06-14 18:46:40",
            "2017-06-14 18:46:46",
            "2017-06-14 18:46:50",
            "2004-12-12 00:01:20",
            "2014-05-01 03:23:43"
        };
        
        for(String timeSt: exampleTimestamps){
            runTestForTime(timeSt);
        }       
    }
    public static void runTestForTime(String timestamp){
        
        long currentMillis = TimestampHelper.getMillisTime(timestamp);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentMillis);
        
        System.out.println("Year:" + calendar.get(Calendar.YEAR) + 
                " Month:" + calendar.get(Calendar.MONTH) + 
                " Day:" + calendar.get(Calendar.DAY_OF_MONTH) + 
                " Hour:" + calendar.get(Calendar.HOUR_OF_DAY) + 
                " Minute:" + calendar.get(Calendar.MINUTE) + 
                " Second:" + calendar.get(Calendar.SECOND) +
                "   Millis since 1970:" + calendar.getTimeInMillis());
        
        
    }
    
}
