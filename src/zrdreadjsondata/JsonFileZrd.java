/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdreadjsondata;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This reads a JSON file
 * 
 * In our case, each line of the file is a seperate JSON object
 * Thus the file is streamed line-by-line just like any text file
 *      and each line is parsed
 *      by the JSON parser
 * 
 * It is a custom class and not part of GSON, hence
 *      I put my initials at the end
 *
 * @author Zach
 */
public class JsonFileZrd {
    
    private Scanner sc;
    private int currentLineNumber=-1;
    JsonParser jsonParser = new JsonParser();
    private boolean fileExists;
    private JsonLineZrd retLine;
    
    /**
     * Initialize file reading
     * @param file 
     */
    public JsonFileZrd(String file){
        try {
            sc = new Scanner(new File(file));
            fileExists=true;
        } catch (FileNotFoundException ex) {
            fileExists=false;
            System.out.println("ERROR: JSON FILE WAS NOT FOUND");
        }
    }
    
    /**
     * whether or not more data exists in iterator going through each line
     * @return  if more data exists
     */
    public boolean hasMoreData(){
        if(fileExists)
            return sc.hasNext();
        else
            return false;
    }
    
    /**
     * obtains the next Json line
     * @return next JSON line
     */
    public JsonLineZrd getNextLine(){
        if(fileExists){
            return getNextValidLine();
        }else{
            return new JsonLineZrd();
        }
    }
    
    /**
     * if line exists then it is parsed
     * @return json line object of parsed line
     */
    private JsonLineZrd getNextValidLine(){
        String nextJsonLine = sc.nextLine();
        currentLineNumber++;
        retLine = new JsonLineZrd(jsonParser,nextJsonLine);
        if(retLine.isValidLine()){
            return retLine;
        }else{
            System.out.println("ERROR: LINE " + currentLineNumber + 
                    " IS INVALID FOR REASON SPECIFIED ABOVE");
            return new JsonLineZrd();
        }
        
        
    }
    
    /**
     * obtains returned line as JSON object 
     * @return returned line
     */
    public JsonObject getJsonObject(){
        return retLine.getEl();
    }
    
}
