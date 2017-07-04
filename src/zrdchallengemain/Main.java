/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdchallengemain;

import zrdreadjsondata.FlaggedPurchaseFile;
import zrdunittests.UnitTest_MeanStdCalculation;

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
        }
        //runUnitTests();
        
        //runs the process json unit test
        ProcessJsonInformation.runProcess(true);
        
        System.out.println(); System.out.println();
        System.out.println("FINISHED ALL TESTS. NOW DOING COMPUTATION:");
        ProcessJsonInformation.runProcess(false);
    }
    
    public static void runUnitTests(){
        //UnitTest_SocialNetwork.runTests();
        //UnitTest_Timestamp.runTests();
        //UnitTest_JsonFiles.runTests();
        //UnitTest_TransactionSetData.runTests();
        //UnitTest_UserTransactionData.runTest();
        UnitTest_MeanStdCalculation.runTests();
    }
}
