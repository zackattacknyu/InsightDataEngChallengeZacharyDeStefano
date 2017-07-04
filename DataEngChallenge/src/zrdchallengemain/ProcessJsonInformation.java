/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdchallengemain;

import java.util.Calendar;
import zrdnetworkdata.AllUsersAndTransactions;
import zrdnetworkdata.SocialNetworkHelper;
import zrdnetworkdata.TransactionHelper;
import zrdnetworkdata.User;
import zrdreadjsondata.FlaggedPurchaseFile;
import zrdreadjsondata.JsonFile;
import zrdreadjsondata.JsonLine;

/**
 *
 * @author Zach
 */
public class ProcessJsonInformation {
    
    
    public static void runProcess(){
        
        
        AllUsersAndTransactions allData = new AllUsersAndTransactions();
        String jsonFile = "jsonFiles/batch_log_large.json";
        
        //get batch log data
        System.out.println("------BATCH LOG FILE--------------");
        allData = processLogFile(allData,jsonFile,false);
        System.out.println("-----------------");
        
        System.out.println();
        System.out.println("------STREAM LOG FILE--------------");
        String jsonFile2 = "jsonFiles/stream_log_large.json";
        allData = processLogFile(allData,jsonFile2,true);
        System.out.println("-----------------");
    }
    
    public static AllUsersAndTransactions processLogFile(AllUsersAndTransactions allData,String jsonFile, boolean streamFlag){
        long startT = Calendar.getInstance().getTimeInMillis();
        JsonFile myfile = new JsonFile(jsonFile);
        int numberAnomalies = 0;
        JsonLine myline;
        User userx;
        FlaggedPurchaseFile theFile = new FlaggedPurchaseFile();
        while(myfile.hasMoreData()){
            myline = myfile.getNextLine();
            switch(myline.getEventNumber()){
                case 1: //parameter set
                    SocialNetworkHelper.numDegreesSocialNetwork=myline.getDvalue();
                    TransactionHelper.MAXIMUM_NUMBER_TRANSACTIONS_IN_NETWORK=myline.getTvalue();
                    break;
                case 2: //purchase
                    userx=allData.addJsonTranscation(myline.getUserX(), myline.getTimestampMillis(), myline.getAmount(),streamFlag);
                    if(streamFlag && userx.isAmountAnamolous(myline.getAmount())){
                        theFile.addToFile(myfile.getJsonObject(), userx);
                        numberAnomalies++;
                    }
                    break;
                case 3: //friend
                    allData.addFriendship(myline.getUser1(), myline.getUser2());
                    break;
                case 4: //unfriend
                    allData.removeFriendship(myline.getUser1(), myline.getUser2());
                    break;
                           
            }
        }
        
        theFile.close();
        allData.calculateTransForAllUsers();
        long endT = Calendar.getInstance().getTimeInMillis();
        
        double elapsed = (endT-startT)/1000.0;
        System.out.println("PROCESSING LOG FILE TOOK " + elapsed + " SECONDS");
        
        startT=Calendar.getInstance().getTimeInMillis();
        allData.calculateMeanStdForAllUsers();
        endT=Calendar.getInstance().getTimeInMillis();
        elapsed = (endT-startT)/1000.0;
        System.out.println("CALCULATING MEAN AND STD FOR ALL USERS TOOK " + elapsed + " SECONDS");
        
        if(streamFlag){
            System.out.println("TOTAL ANOMALIES DETECTED: " + numberAnomalies);
        }
        
        return allData;
    }  
    
}
