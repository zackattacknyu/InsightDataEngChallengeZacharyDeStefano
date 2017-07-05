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
import zrdnetworkdata.User;

/**
 * This class is used to create the Json File of Flagged Purchases
 *
 * @author Zach
 */
public class FlaggedPurchaseFile {
    
    private File outputFile;
    private FileWriter outputFileWrite;
    private JsonParser jsonParser = new JsonParser();

    public static String FLAGGED_PURCHASE_FILE;
    
    private boolean firstLineWritten=false;
    
    /**
     * Intialize the file by writing it to directory
     */
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
    
    /**
     * This does the truncation manually to ensure two numbers after the period
     * @param number        number as double
     * @return      string of number with only two numbers after the decimal
     */
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
    
    /**
     * adds the JsonObject representing the purchase to the file
     * It adds the mean and std information before writing the Json line
     * @param object    json object from stream log file
     * @param userdata  user data for user
     */
    public void addToFile(JsonObject object, User userdata){
        //makes a new object from the input
        JsonObject newObject = jsonParser.parse(object.toString()).getAsJsonObject();
        
        //gets the mean and std string truncated
        String meanStr = truncateToTwoDecimals(userdata.getMean());
        String sdStr = truncateToTwoDecimals(userdata.getStd());
        
        //adds the mean, std string to our output object
        newObject.addProperty("mean", meanStr);
        newObject.addProperty("sd",sdStr);
        
        //writes the line to the file
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
    
    /**
     * closes the writer
     */
    public void close(){
        try {
            outputFileWrite.close();
        } catch (IOException ex) {
            System.out.println("IO EXCEPTION WHEN CLOSING FLAGGED PURCHASES FILE: " + ex.getMessage());
        }
    }
    
    
    
    
    
    
}
