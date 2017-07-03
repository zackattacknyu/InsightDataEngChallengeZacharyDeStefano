/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdchallengemain;

import zrdreadjsondata.JsonFile;
import zrdreadjsondata.JsonLine;

/**
 *
 * @author Zach
 */
public class JsonFileTest {
    
    public static void runTests(){
        
        String jsonFile = "jsonFiles/batch_log.json";
        JsonFile myfile = new JsonFile(jsonFile);
        JsonLine myline;
        while(myfile.hasMoreData()){
            myline = myfile.getNextLine();
            myline.displayLineData();
        }
        
        
    }
    
}
