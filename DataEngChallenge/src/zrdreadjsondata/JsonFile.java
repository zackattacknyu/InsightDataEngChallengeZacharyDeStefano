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
    
    public JsonFile(String file){
        try {
            sc = new Scanner(new File(file));
        } catch (FileNotFoundException ex) {
            System.out.println("ERROR: JSON FILE WAS NOT FOUND");
        }
    }
    
    public boolean hasMoreData(){
        return sc.hasNext();
    }
    
    public JsonLine getNextLine(){
        String nextJsonLine = sc.nextLine();
        currentLineNumber++;
        JsonLine retLine = new JsonLine(jsonParser,nextJsonLine);
        if(retLine.isValidLine()){
            return retLine;
        }else{
            System.out.println("ERROR: LINE " + currentLineNumber + " IS INVALID FOR REASON SPECIFIED ABOVE");
            return null;
        }
        
        
    }
    
}
