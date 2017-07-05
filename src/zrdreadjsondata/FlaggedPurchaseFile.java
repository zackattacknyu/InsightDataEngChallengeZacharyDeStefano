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
    
    public static String truncateToTwoDecimals(double number){
        String doubleStr = Double.toString(number);
        StringBuilder newStr = new StringBuilder();
        boolean decimalEncountered = false;
        int numbersAfterDecimal = 0;
        char currentChar;
        for(int j=0; j < doubleStr.length(); j++){
            currentChar = doubleStr.charAt(j);
            newStr.append(currentChar);
            if(decimalEncountered){
                numbersAfterDecimal++;
            }
            if(currentChar=='.'){
                decimalEncountered = true;
            }
            if(numbersAfterDecimal>=2){
                break;
            }
        }
        return newStr.toString();
    }
    
    public void addToFile(JsonObject object, User userdata){
        JsonObject newObject = jsonParser.parse(object.toString()).getAsJsonObject();
        String meanStr = truncateToTwoDecimals(userdata.getMean());
        String sdStr = truncateToTwoDecimals(userdata.getStd());
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
