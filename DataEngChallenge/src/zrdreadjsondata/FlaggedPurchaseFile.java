/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdreadjsondata;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import zrdnetworkdata.User;

/**
 *
 * @author Zach
 */
public class FlaggedPurchaseFile {
    
    private File outputFile;
    private FileWriter outputFileWrite;
    private JsonParser jsonParser = new JsonParser();
    DecimalFormat df = new DecimalFormat("###.##");

    public FlaggedPurchaseFile() {
        
        outputFile = new File("log_output/flagged_purchases.json");
        try {
            outputFile.createNewFile();
            outputFileWrite = new FileWriter(outputFile);
            
            
        } catch (IOException ex) {
            System.out.println("IO EXCEPTION WHEN CREATING FLAGGED PURCHASES FILE: " + ex.getMessage());
        }
        
        
        
    }
    
    public void addToFile(JsonObject object, User userdata){
        JsonObject newObject = jsonParser.parse(object.toString()).getAsJsonObject();
        String meanStr = df.format(userdata.getMean());
        String sdStr = df.format(userdata.getStd());
        newObject.addProperty("mean", meanStr);
        newObject.addProperty("sd",sdStr);
        
        String outputStr = newObject.toString() + "\n";
        try {
            outputFileWrite.write(outputStr);
        } catch (IOException ex) {
            System.out.println("IO EXCEPTION WHEN WRITING FLAGGED PURCHASES FILE: " + ex.getMessage());
        }
    }
    
    public void close(){
        try {
            outputFileWrite.close();
        } catch (IOException ex) {
            System.out.println("IO EXCEPTION WHEN CLOSING FLAGGED PURCHASES FILE: " + ex.getMessage());
        }
    }
    
    
    
    
    
    
}
