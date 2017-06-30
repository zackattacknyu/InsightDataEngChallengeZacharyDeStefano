/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdreadjsondata;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Zach
 */
public class readJsonFile {
    
    public static final String D_FIELD_STRING = "D";
    public static final String T_FIELD_STRING = "T";
    public static final String EVENT_TYPE_FIELD_STRING = "event_type";
    public static final String TIMESTAMP_FIELD_STRING = "timestamp";
    public static final String PURCHASE_EVENT_NAME = "purchase";
    public static final String PURCHASEID_FIELD_STRING = "id";
    public static final String AMOUNT_FIELD_STRING = "amount";
    public static final String UNFRIEND_EVENT_NAME = "unfriend";
    public static final String BEFRIEND_EVENT_NAME = "befriend";
    public static final String FRIENDEVENT_ID1_FIELD_STRING = "id1";
    public static final String FRIENDEVENT_ID2_FIELD_STRING = "id2";
    
    
    public static void exampleRun(){
        
        
        try {
            
            int D=0,T=0;
            String eventType;
            
            String timestamp;
            String purchaseUserID;
            String purchaseAmount;
            String friendId1;
            String friendId2;
            String introStr;
            
            String jsonFile = "jsonFiles/batch_log.json";
            Scanner sc = new Scanner(new File(jsonFile));
                        
            while(sc.hasNext()){
            
                JsonParser jsonParser = new JsonParser();
                JsonElement element = jsonParser.parse(sc.nextLine());
                JsonObject el = element.getAsJsonObject();
                if(el.has(D_FIELD_STRING)){
                    D = Integer.parseInt(el.get(D_FIELD_STRING).getAsString());
                    T = Integer.parseInt(el.get(T_FIELD_STRING).getAsString());
                }else{
                    eventType = el.get(EVENT_TYPE_FIELD_STRING).getAsString();
                    timestamp = el.get(TIMESTAMP_FIELD_STRING).getAsString();
                    if(eventType.equals(PURCHASE_EVENT_NAME)){
                        
                        purchaseUserID = el.get(PURCHASEID_FIELD_STRING).getAsString();
                        purchaseAmount = el.get(AMOUNT_FIELD_STRING).getAsString();
                        
                        System.out.println("The user " + purchaseUserID + 
                                " bought " + purchaseAmount + " on " + timestamp);
                    }else if(eventType.equals(UNFRIEND_EVENT_NAME) || 
                            eventType.equals(BEFRIEND_EVENT_NAME)){
                        friendId1 = el.get(FRIENDEVENT_ID1_FIELD_STRING).getAsString();
                        friendId2 = el.get(FRIENDEVENT_ID2_FIELD_STRING).getAsString();
                        
                        introStr = "The users " + friendId1 + " and " + friendId2;
                        
                        if(eventType.equals(UNFRIEND_EVENT_NAME)){
                            System.out.println( introStr + " disconnected on " + timestamp);
                        }else{
                            System.out.println( introStr + " connected on " + timestamp);
                        }
                        
                    }
                }

            }
            
            System.out.println(D + " " + T);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(readJsonFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch(IOException exc){
            exc.printStackTrace();
        }
        
        

        
    }
    
}
