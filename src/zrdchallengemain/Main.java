/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdchallengemain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import zrdreadjsondata.FlaggedPurchaseFile;
import zrdunittests.UnitTest_JsonFiles;
import zrdunittests.UnitTest_MeanStdCalculation;
import zrdunittests.UnitTest_SocialNetwork;
import zrdunittests.UnitTest_Timestamp;
import zrdunittests.UnitTest_TransactionSetData;
import zrdunittests.UnitTest_UserTransactionData;

/**
 * This is the main class that runs the core functionality and
 *  the informal unit tests
 *
 * @author Zach
 */
public class Main {

    
    public static String LOG_OUTPUT_FOLDER_NAME = "log_output";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        /*
         * Makes sure the log output folder has been created
         */
        createLogOutputFolder();
        
        /*
         * If the batch, stream, and output file have been specified
         */
        if(args.length==3){
            ProcessJsonInformation.BATCH_LOG_FILE=args[0];
            ProcessJsonInformation.STREAM_LOG_FILE=args[1];
            FlaggedPurchaseFile.FLAGGED_PURCHASE_FILE=args[2];
            
            
        }
        
        /*
         *Does the unit tests only 
         */
        if(args.length==1){
            
            ProcessJsonInformation.BATCH_LOG_FILE="log_input_small/batch_log.json";
            ProcessJsonInformation.STREAM_LOG_FILE="log_input_small/stream_log.json";
            FlaggedPurchaseFile.FLAGGED_PURCHASE_FILE=
                    "log_output/flagged_purchases_smallSampleDataSet.json";
            
            runUnitTests();
            
            ProcessJsonInformation.BATCH_LOG_FILE="log_input_large/batch_log.json";
            ProcessJsonInformation.STREAM_LOG_FILE="log_input_large/stream_log.json";
            FlaggedPurchaseFile.FLAGGED_PURCHASE_FILE=
                    "log_output/flagged_purchases_largeSampleDataSet.json";
            
            runJsonUnitTest();
        }else{
            
            /*
             * Runs the core functionality
             */
            ProcessJsonInformation.BATCH_LOG_FILE="log_input/batch_log.json";
            ProcessJsonInformation.STREAM_LOG_FILE="log_input/stream_log.json";
            FlaggedPurchaseFile.FLAGGED_PURCHASE_FILE= 
                    "log_output/flagged_purchases.json";
            
            ProcessJsonInformation.runProcess(false);
        }
        
        
        
        
    }
    
    /**
     * Creates the log output file in case it was not created before
     */
    public static void createLogOutputFolder(){
        Path currentPath = Paths.get("").toAbsolutePath().resolve(LOG_OUTPUT_FOLDER_NAME);
        System.out.println(currentPath);
        if(!Files.isDirectory(currentPath)){
            try {
                Files.createDirectory(currentPath);
            } catch (IOException ex) {
                System.out.println("IO EXCEPTION WHEN CREATING LOG_OUTPUT DIRECTORY: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * 
     * Tells which set of tests is about to be done
     */
    public static void printTestAnnouce(String announce){
        System.out.println();
        System.out.println("-----------------------------------------------");
        System.out.println(announce);
        System.out.println("-----------------------------------------------");
        System.out.println();
    }
    
    /**
     * Runs all the unit tests
     */
    public static void runUnitTests(){
        System.out.println("NOW DOING THE INFORMAL UNIT TESTS");

        printTestAnnouce("NOW TESTING THE SOCIAL NETWORK");
        UnitTest_SocialNetwork.runTests();

        printTestAnnouce("NOW TESTING TIMESTAMPS");
        UnitTest_Timestamp.runTests();
        
        printTestAnnouce("NOW TESTING THE SOCIAL NETWORK");
        UnitTest_JsonFiles.runTests();
        
        printTestAnnouce("NOW TESTING TRANSACTION SET DATA");
        UnitTest_TransactionSetData.runTests();
        
        printTestAnnouce("NOW TESTING USER,TRANSACTION SET DATA");
        UnitTest_UserTransactionData.runTest();
        
        printTestAnnouce("NOW TESTING MEAN,STD CALCULATION DATA");
        UnitTest_MeanStdCalculation.runTests();
        
    }
    
    /**
     * This test the core functionality. It outputs the anomalous 
     *      purchases found and the associated transaction history
     *      as a verification step. 
     */
    public static void runJsonUnitTest(){
        //runs the process json unit test
        printTestAnnouce("NOW THE JSON LOG DATA");
        ProcessJsonInformation.runProcess(true);
        
        System.out.println(); System.out.println();
        System.out.println("FINISHED ALL TESTS. CAN NOW DO COMPUTATION");
    }
}
