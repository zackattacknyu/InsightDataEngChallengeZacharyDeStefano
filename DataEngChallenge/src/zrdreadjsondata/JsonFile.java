/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdreadjsondata;

import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Zach
 */
public class JsonFile {
    
    private Scanner sc;
    private int currentLineNumber=-1;
    JsonParser jsonParser = new JsonParser();
    private boolean fileExists;
    
    public JsonFile(String file){
        try {
            sc = new Scanner(new File(file));
            fileExists=true;
        } catch (FileNotFoundException ex) {
            fileExists=false;
            System.out.println("ERROR: JSON FILE WAS NOT FOUND");
        }
    }
    
    public boolean hasMoreData(){
        if(fileExists)
            return sc.hasNext();
        else
            return false;
    }
    
    public JsonLine getNextLine(){
        if(fileExists){
            return getNextValidLine();
        }else{
            return new JsonLine();
        }
    }
    
    private JsonLine getNextValidLine(){
        String nextJsonLine = sc.nextLine();
        currentLineNumber++;
        JsonLine retLine = new JsonLine(jsonParser,nextJsonLine);
        if(retLine.isValidLine()){
            return retLine;
        }else{
            System.out.println("ERROR: LINE " + currentLineNumber + " IS INVALID FOR REASON SPECIFIED ABOVE");
            return new JsonLine();
        }
        
        
    }
    
}
