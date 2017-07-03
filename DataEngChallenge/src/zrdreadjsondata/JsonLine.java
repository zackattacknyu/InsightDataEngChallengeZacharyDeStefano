/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdreadjsondata;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import zrdnetworkdata.SocialNetworkHelper;
import zrdnetworkdata.TimestampHelper;

/**
 *
 * @author Zach
 */
public class JsonLine {
    
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
    
    int user1;
    int user2;
    int userX;
    double amount;
    int Dvalue;
    int Tvalue;
    long timestampMillis;
    
    String eventType;
    String timestamp;
    String purchaseUserID;
    String purchaseAmount;
    String friendId1,friendId2;
    
    /*Following standard is used:
     *  0 - invalid statement
     *  1 - parameter statemetn
     *  2 - purchase
     *  3 - friend
     *  4 - unfriend
     * 
     */
    int eventNumber=0;
    
    /**
     * Invalid if one of those following is true:
     *      - does not fit any of the event types
     *      - event type fits but not all data fields are properly specified
     * 
     * If invalid then this instance is ignored during execution
     * The invalidity is printed to the console
     */
    boolean validLine=true;
    
    private JsonObject el;
    
    public JsonLine(){
        validLine=false;
    }

    
    public JsonLine(JsonParser jsonParser,String jsonLine){
        try{
            JsonElement element = jsonParser.parse(jsonLine);
            el = element.getAsJsonObject();
            
            if(el.has(D_FIELD_STRING)){
                handleParameterObject();
            }else{
                handleEvents();
            }
        }catch (Exception e){
            System.out.println("ERROR: FOLLOWING IS IMPROPER: " + jsonLine);
            System.out.println("ERROR: FOR FOLLOWING REASON: " + e.getMessage());
            validLine=false;
        }

    }
    
    private void handleParameterObject(){
        eventNumber=1;
        Dvalue = Integer.parseInt(el.get(D_FIELD_STRING).getAsString());
        Tvalue = Integer.parseInt(el.get(T_FIELD_STRING).getAsString());
        SocialNetworkHelper.numDegreesSocialNetwork=Dvalue;
        
    }
    
    private void handleEvents(){
        
        eventType = el.get(EVENT_TYPE_FIELD_STRING).getAsString();
        timestamp = el.get(TIMESTAMP_FIELD_STRING).getAsString();
        timestampMillis = TimestampHelper.getMillisTime(timestamp);        
        if(eventType.equals(PURCHASE_EVENT_NAME)){
            handlePurchaseEvent();
        }else if(eventType.equals(UNFRIEND_EVENT_NAME) || 
                eventType.equals(BEFRIEND_EVENT_NAME)){         
            handleFriendEvent();
        }else{
            validLine=false;
        }
        
    }
    
    private void handleFriendEvent(){
        if(eventType.equals(UNFRIEND_EVENT_NAME)){
            eventNumber=4;
        }else{
            eventNumber=3;
        }
        friendId1 = el.get(FRIENDEVENT_ID1_FIELD_STRING).getAsString();
        friendId2 = el.get(FRIENDEVENT_ID2_FIELD_STRING).getAsString();
        user1 = Integer.parseInt(friendId1); 
        user2 = Integer.parseInt(friendId2);
    }
    
    private void handlePurchaseEvent(){
        eventNumber=2;
        purchaseUserID = el.get(PURCHASEID_FIELD_STRING).getAsString();
        purchaseAmount = el.get(AMOUNT_FIELD_STRING).getAsString();
        userX = Integer.parseInt(purchaseUserID);
        amount = Double.parseDouble(purchaseAmount);
    }

    public boolean isValidLine() {
        return validLine;
    }
    
    public void displayLineData(){
        if(validLine){
            displayValidLineData();
        }else{
            System.out.println();
        }
    }
    
    private void displayValidLineData(){
        String friendInfoStr = "The users " + user1 + " and " + user2;
        String timeInfoStr = " on " + timestamp + 
                        " which is " + timestampMillis +" in millis";
        switch(eventNumber){
            case 0: 
                System.out.println("INVALID LINE");
                break;
            case 1:
                System.out.println("PARAMETERS SPECIFIED:: D:" + Dvalue + 
                        "  T:" + Tvalue);
                break;
            case 2:
                System.out.println("PURCHASE EVENT: User " + userX + 
                                " bought " + amount + timeInfoStr);   
                break;
            case 3:
                System.out.println("FRIEND EVENT: " + friendInfoStr + 
                        " became friends" + timeInfoStr);
                break;
            case 4:
                System.out.println("UNFRIEND EVENT: " + friendInfoStr + 
                        " ended friendship" + timeInfoStr);
                break;
                
                       
        }
    }
    
    
}
