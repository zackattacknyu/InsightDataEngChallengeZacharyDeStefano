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
import zrdreadjsondata.JsonFileZrd;
import zrdreadjsondata.JsonLineZrd;

/**
 * 
 * This implements the core functionality
 * It reads the Json File line-by-line and calls the 
 *      correct update methods
 *
 * @author Zach
 */
public class ProcessJsonInformation {
    
    public static String BATCH_LOG_FILE;
    public static String STREAM_LOG_FILE;
    
    /**
     * Calls the function that processes Json Files for both Json files used
     * @param testFlag whether we are testing or not
     */
    public static void runProcess(boolean testFlag){
        
        
        AllUsersAndTransactions allData = new AllUsersAndTransactions();
        
        //get batch log data
        System.out.println("------BATCH LOG FILE--------------");
        allData = processLogFile(allData,BATCH_LOG_FILE,false,testFlag);
        System.out.println("-----------------");
        
        //get stream log data
        System.out.println();
        System.out.println("------STREAM LOG FILE--------------");
        allData = processLogFile(allData,STREAM_LOG_FILE,true,testFlag);
        System.out.println("-----------------");
    }
    
    /**
     * Processes a Json File and returns the Data Structure with all the 
     *      important information about it. It also creates
     *      the flagged_purchases file and writes to it if we are in streaming mode. 
     * 
     * @param allData       starting data structure if some of the data was computed before
     * @param jsonFile      path to Json File with data
     * @param streamFlag    whether or not we are in streaming mode
     * @param testFlag      whether or not we are in testing mode
     * @return ending data structure after all computation done
     */
    public static AllUsersAndTransactions processLogFile(AllUsersAndTransactions allData,
            String jsonFile, boolean streamFlag, boolean testFlag){
        long startT = Calendar.getInstance().getTimeInMillis();
        JsonFileZrd myfile = new JsonFileZrd(jsonFile);
        int numberAnomalies = 0;
        JsonLineZrd myline;
        User userx;
        
        //creates the flagged purchases file
        FlaggedPurchaseFile theFile= new FlaggedPurchaseFile();
        if(streamFlag && !testFlag){
            theFile.init();
        }
        
        //iterate through each line of Json File
        while(myfile.hasMoreData()){
            myline = myfile.getNextLine();
            switch(myline.getEventNumber()){ //switches depending on event happened
                case 1: //parameter set event
                    SocialNetworkHelper.numDegreesSocialNetwork=myline.getDvalue();
                    TransactionHelper.MAXIMUM_NUMBER_TRANSACTIONS_IN_NETWORK=myline.getTvalue();
                    break;
                case 2: //purchase event
                    
                    //adds the data to the logs
                    userx=allData.addJsonTranscation(myline.getUserX(), myline.getTimestampMillis(), myline.getAmount(),streamFlag);
                    
                    if(streamFlag && userx.isAmountAnamolous(myline.getAmount())){
                        
                        //print out about a third of anomalies and their data if we are testing
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
                case 3: //friend event
                    allData.addFriendship(myline.getUser1(), myline.getUser2());
                    break;
                case 4: //unfriend event
                    allData.removeFriendship(myline.getUser1(), myline.getUser2());
                    break;
                           
            }
        }
        
        if(streamFlag && !testFlag){
            theFile.close();
            System.out.println("FINISHED WRITING FLAGGED PURCHASES FILE");
        }
        
        //ensures transaction set is calculated for everyone
        allData.calculateTransForAllUsers();
        
        //how long the processing took for the filw
        //max was 5 seconds on the 50 MB file
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
