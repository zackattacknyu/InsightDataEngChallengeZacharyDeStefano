/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdchallengemain;

import zrdreadjsondata.FlaggedPurchaseFile;
import zrdunittests.UnitTest_JsonFiles;
import zrdunittests.UnitTest_MeanStdCalculation;
import zrdunittests.UnitTest_SocialNetwork;
import zrdunittests.UnitTest_Timestamp;
import zrdunittests.UnitTest_TransactionSetData;
import zrdunittests.UnitTest_UserTransactionData;

/**
 *
 * @author Zach
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if(args.length==3){
            ProcessJsonInformation.BATCH_LOG_FILE=args[0];
            ProcessJsonInformation.STREAM_LOG_FILE=args[1];
            FlaggedPurchaseFile.FLAGGED_PURCHASE_FILE=args[2];
            
            ProcessJsonInformation.runProcess(false);
        }
        
        if(args.length==1){
            runUnitTests();
        }
        
        
        
        
    }
    
    public static void printTestAnnouce(String announce){
        System.out.println();
        System.out.println("-----------------------------------------------");
        System.out.println(announce);
        System.out.println("-----------------------------------------------");
        System.out.println();
    }
    
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
        
        
        //runs the process json unit test
        printTestAnnouce("NOW THE JSON LOG DATA");
        ProcessJsonInformation.runProcess(true);
        
        System.out.println(); System.out.println();
        System.out.println("FINISHED ALL TESTS. CAN NOW DO COMPUTATION");
    }
}
