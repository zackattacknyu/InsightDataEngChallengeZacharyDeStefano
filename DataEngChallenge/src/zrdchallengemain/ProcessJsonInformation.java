/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdchallengemain;

import java.util.Calendar;
import zrdnetworkdata.AllUsersAndTransactions;
import zrdnetworkdata.SocialNetworkHelper;
import zrdnetworkdata.TransactionHelper;
import zrdreadjsondata.JsonFile;
import zrdreadjsondata.JsonLine;

/**
 *
 * @author Zach
 */
public class ProcessJsonInformation {
    
    
    public static void runProcess(){
        
        long startT = Calendar.getInstance().getTimeInMillis();
        AllUsersAndTransactions allData = new AllUsersAndTransactions();
        
        //get batch log data
        String jsonFile = "jsonFiles/batch_log_large.json";
        JsonFile myfile = new JsonFile(jsonFile);
        JsonLine myline;
        while(myfile.hasMoreData()){
            myline = myfile.getNextLine();
            switch(myline.getEventNumber()){
                case 1: //parameter set
                    SocialNetworkHelper.numDegreesSocialNetwork=myline.getDvalue();
                    TransactionHelper.MAXIMUM_NUMBER_TRANSACTIONS_IN_NETWORK=myline.getTvalue();
                    break;
                case 2: //purchase
                    allData.addJsonTranscation(myline.getUserX(), myline.getTimestampMillis(), myline.getAmount());
                    break;
                case 3: //friend
                    allData.addFriendship(myline.getUser1(), myline.getUser2());
                    break;
                case 4: //unfriend
                    allData.removeFriendship(myline.getUser1(), myline.getUser2());
                    break;
                           
            }
        }
        allData.calculateTransForAllUsers();
        long endT = Calendar.getInstance().getTimeInMillis();
        
        double elapsed = (endT-startT)/1000.0;
        System.out.println("PROCESSING LARGE LOG FILE TOOK " + elapsed + " SECONDS");
        
        startT=Calendar.getInstance().getTimeInMillis();
        allData.calculateMeanStdForAllUsers();
        endT=Calendar.getInstance().getTimeInMillis();
        elapsed = (endT-startT)/1000.0;
        System.out.println("CALCULATING MEAN AND STD FOR ALL USERS TOOK " + elapsed + " SECONDS");
    }
    
}
