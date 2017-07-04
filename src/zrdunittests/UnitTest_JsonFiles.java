/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdunittests;

import java.util.Calendar;
import zrdreadjsondata.JsonFile;
import zrdreadjsondata.JsonLine;

/**
 *
 * @author Zach
 */
public class UnitTest_JsonFiles {
    
    public static void runTests(){
        
        /*
         * Test 1: Make sure we are reading batch log file correctly
         */
        String jsonFile = "jsonFiles/batch_log.json";
        System.out.println();
        System.out.println("-------DATA FROM BATCH LOG FILE:-------");
        displayDataFromJsonFile(jsonFile,1);
        
        /*
         * Test 2: Make sure we are reading stream log file correctly
         */
        System.out.println();
        System.out.println("-------DATA FROM STREAM LOG FILE:-------");
        jsonFile = "jsonFiles/stream_log.json";
        displayDataFromJsonFile(jsonFile,1);
        
        /*
         * Test 3: Read large batch log file
         *      Display random lines
         */
        jsonFile = "jsonFiles/batch_log_large.json";
        System.out.println();
        System.out.println("-------DATA FROM LARGE BATCH LOG FILE:-------");
        displayDataFromJsonFile(jsonFile,0.00001);
               
        /*
         * Test 4: Read large stream log file
         *      Display random lines
         */
        jsonFile = "jsonFiles/stream_log_large.json";
        System.out.println();
        System.out.println("-------DATA FROM LARGE STREAM LOG FILE:-------");
        displayDataFromJsonFile(jsonFile,0.01);
        
        /*
         * Test 5: Read empty file
         */
        jsonFile = "jsonFiles/batch_log_empty.json";
        System.out.println();
        System.out.println("-------DATA FROM EMPTY FILE:-------");
        displayDataFromJsonFile(jsonFile,1);
        
        /*
         * Test 6: Read not a JSON file
         */
        jsonFile = "jsonFiles/batch_log_NotJsonFormat.json";
        System.out.println();
        System.out.println("-------DATA FROM TRYING TO READ NON-JSON FILE:-------");
        displayDataFromJsonFile(jsonFile,1);
        
        /*
         * Test 7: badly specified file string
         */
        jsonFile = "jsonFiles/batch_log_NotJsonFomat.json";
        System.out.println();
        System.out.println("-------DATA FROM TRYING TO READ BAD FILE STRING:-------");
        displayDataFromJsonFile(jsonFile,1);
        
        /*
         * Test 8: Read JSON file with bad entries
         */
        jsonFile = "jsonFiles/batch_log_withImproper.json";
        System.out.println();
        System.out.println("-------DATA FROM JSON FILE WITH BAD ENTRIES (EMPTY STRINGS OR NOT PROPER NUMBERS):-------");
        displayDataFromJsonFile(jsonFile,1);
        
        /*
         * Test 9: Find time to read large batch file
         */
        long startTime = Calendar.getInstance().getTimeInMillis();
        displayDataFromJsonFile(jsonFile,-1);
        long endTime = Calendar.getInstance().getTimeInMillis();
        double numSecondsElapsed = (endTime-startTime)/1000.0;
        System.out.println();
        System.out.println("TIME TO READ LARGEST LOG FILE: " + numSecondsElapsed + " SECONDS");
        
    }
    
    public static void displayDataFromJsonFile(String jsonFile,double probabilityDisplay){
        JsonFile myfile = new JsonFile(jsonFile);
        JsonLine myline;
        while(myfile.hasMoreData()){
            myline = myfile.getNextLine();
            if(Math.random()<probabilityDisplay){
                myline.displayLineData();
            }
            
        }
        
    }
    
}
