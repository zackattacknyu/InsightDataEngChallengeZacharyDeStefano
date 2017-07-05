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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
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

    public static String FLAGGED_PURCHASE_FILE;
    
    private boolean firstLineWritten=false;
        
    public void init(){
        try {
            outputFile = new File(FLAGGED_PURCHASE_FILE);
            outputFile.createNewFile();
            outputFileWrite = new FileWriter(outputFile);
        } catch (IOException ex) {
            System.out.println("IO EXCEPTION WHEN CREATING FLAGGED PURCHASES FILE: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public void addToFile(JsonObject object, User userdata){
        JsonObject newObject = jsonParser.parse(object.toString()).getAsJsonObject();
        String meanStr = df.format(userdata.getMean());
        String sdStr = df.format(userdata.getStd());
        newObject.addProperty("mean", meanStr);
        newObject.addProperty("sd",sdStr);
        
        String outputStr = newObject.toString();
        try {
            if(firstLineWritten){
                outputFileWrite.write("\n");
            }else{
                firstLineWritten=true;
            }
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
