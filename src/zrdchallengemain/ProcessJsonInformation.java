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
    
    public static String BATCH_LOG_FILE;
    public static String STREAM_LOG_FILE;
    
    
    public static void runProcess(boolean testFlag){
        
        
        AllUsersAndTransactions allData = new AllUsersAndTransactions();
        
        //get batch log data
        System.out.println("------BATCH LOG FILE--------------");
        allData = processLogFile(allData,BATCH_LOG_FILE,false,testFlag);
        System.out.println("-----------------");
        
        System.out.println();
        System.out.println("------STREAM LOG FILE--------------");
        allData = processLogFile(allData,STREAM_LOG_FILE,true,testFlag);
        System.out.println("-----------------");
    }
    
    public static AllUsersAndTransactions processLogFile(AllUsersAndTransactions allData,
            String jsonFile, boolean streamFlag, boolean testFlag){
        long startT = Calendar.getInstance().getTimeInMillis();
        JsonFile myfile = new JsonFile(jsonFile);
        int numberAnomalies = 0;
        JsonLine myline;
        User userx;
        
        FlaggedPurchaseFile theFile= new FlaggedPurchaseFile();
        if(streamFlag && !testFlag){
            theFile.init();
        }
        
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
                        
                        //print out about a third of anomalies and their data
                        if(testFlag){
                            if(Math.random() < 0.33){
                                System.out.println("Anamolous purchase in network of user " + userx.hashCode());
                                System.out.println("Mean:" + userx.getMean() + " std:" + userx.getStd());
                                System.out.println("Amount of Purchase:" + myline.getAmount());
                                System.out.println("To verify transaction log and mean, here is the data: ");
                                userx.getPastTransactionsInNetwork().printAllInfo();
                                System.out.println(); System.out.println();
                            }
                        }
                        
                        if(!testFlag){
                            theFile.addToFile(myfile.getJsonObject(), userx);
                        }
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
        
        if(streamFlag && !testFlag){
            theFile.close();
            System.out.println("FINISHED WRITING FLAGGED PURCHASES FILE");
        }
        allData.calculateTransForAllUsers();
        long endT = Calendar.getInstance().getTimeInMillis();
        
        double elapsed = (endT-startT)/1000.0;
        System.out.println("PROCESSING LOG FILE TOOK " + elapsed + " SECONDS");
        
        allData.calculateMeanStdForAllUsers();
        
        
        if(streamFlag){
            System.out.println("TOTAL ANOMALIES DETECTED: " + numberAnomalies);
        }
        
        return allData;
    }  
    
}
