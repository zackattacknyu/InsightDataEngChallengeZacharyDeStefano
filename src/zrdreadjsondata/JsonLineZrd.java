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
 * This has all the functionality behind 
 *  reading the data in the Json object and returning
 *  variables for my program to understand
 * 
 * It uses GSON from Google to parse the JSON 
 *  and return the data as appropriate
 * 
 * This is a custom class that is NOT part of GSON, hence
 *      I put my initials at the end to make it obvious
 *
 * @author Zach
 */
public class JsonLineZrd {
    
    /*
     * The fields that could appear in the JSON object
     */
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
    
    /*
     * The resulting variables that my program will understand
     */
    int user1;
    int user2;
    int userX;
    double amount;
    int Dvalue;
    int Tvalue;
    long timestampMillis;
    
    /*
     * The initial strings for those variables
     */
    String eventType;
    String timestamp;
    String purchaseUserID;
    String purchaseAmount;
    String friendId1,friendId2;
    
    /*In this program, the following numbers to denote the different types of events:
     *  0 - invalid statement
     *  1 - parameter statement
     *  2 - purchase
     *  3 - friend
     *  4 - unfriend
     */
    int eventNumber=0;
    
    /**The data is 
     * invalid if one of those following is true:
     *      - does not fit any of the event types
     *      - event type fits but not all data fields are properly specified
     * 
     * If invalid then this instance is ignored during execution
     * The invalidity is printed to the console
     */
    boolean validLine=true;
    
    //original JsonObject behind this line
    private JsonObject el;
    
    public JsonLineZrd(){
        validLine=false;
    }

    /**
     * Constructs the Json line. Parses through the JOSN string
     *      and handles the result as appropriate
     * @param jsonParser    json parser in GSON
     * @param jsonLine      json data as string
     */
    public JsonLineZrd(JsonParser jsonParser,String jsonLine){
        try{
            //generates the JsonElement
            JsonElement element = jsonParser.parse(jsonLine);
            
            //gets the corresponding object
            el = element.getAsJsonObject(); 
            
            /*
             * If the "D" appears then I assume parameters are being specified
             *      and I handle that in one method
             * Otherwise I assume another event is happening and that is handled
             *      in a sepearate method
             */
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
    
    /**
     * If parameters were specified, those strings are updated
     * The program is responsible for actually setting the static variables
     *      corresponding to the parameters
     */
    private void handleParameterObject(){
        eventNumber=1;
        Dvalue = Integer.parseInt(el.get(D_FIELD_STRING).getAsString());
        Tvalue = Integer.parseInt(el.get(T_FIELD_STRING).getAsString());
    }
    
    /**
     * If an event has been specified, this differentiates
     *      between a purchase event and a friend event
     */
    private void handleEvents(){
        
        eventType = el.get(EVENT_TYPE_FIELD_STRING).getAsString();
        
        //all events have timestamps, so the long form is found now
        timestamp = el.get(TIMESTAMP_FIELD_STRING).getAsString();
        timestampMillis = TimestampHelper.getMillisTime(timestamp);
        
        
        if(eventType.equals(PURCHASE_EVENT_NAME)){ //purchase event
            handlePurchaseEvent();
        }else if(eventType.equals(UNFRIEND_EVENT_NAME) || 
                eventType.equals(BEFRIEND_EVENT_NAME)){  //friend or unfriend event
            handleFriendEvent();
        }else{
            validLine=false; //not a valid event was specified
        }
        
    }
    
    /**
     * handles friend/unfriend event
     */
    private void handleFriendEvent(){
        
        //sets the event number variable appropriately
        if(eventType.equals(UNFRIEND_EVENT_NAME)){
            eventNumber=4;
        }else{
            eventNumber=3;
        }
        
        //gets the integer ids for both users involved with the friending/unfriending 
        friendId1 = el.get(FRIENDEVENT_ID1_FIELD_STRING).getAsString();
        friendId2 = el.get(FRIENDEVENT_ID2_FIELD_STRING).getAsString();
        user1 = Integer.parseInt(friendId1); 
        user2 = Integer.parseInt(friendId2);
    }
    
    /*
     * handles a purchase event
     */
    private void handlePurchaseEvent(){
        eventNumber=2; //sets event number correctly
        
        //finds the user id and purchase amount 
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
    
    /*
     * Displays the data found for the purposes of unit testing
     */
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

    public int getEventNumber() {
        return eventNumber;
    }

    public int getUser1() {
        return user1;
    }

    public int getUser2() {
        return user2;
    }

    public int getUserX() {
        return userX;
    }

    public double getAmount() {
        return amount;
    }

    public int getDvalue() {
        return Dvalue;
    }

    public int getTvalue() {
        return Tvalue;
    }

    public long getTimestampMillis() {
        return timestampMillis;
    }

    public JsonObject getEl() {
        return el;
    }
    
    
}
