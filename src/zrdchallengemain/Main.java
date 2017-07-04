/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdchallengemain;

import zrdunittests.UnitTest_UserTransactionData;
import zrdunittests.UnitTest_SocialNetwork;
import zrdunittests.UnitTest_Timestamp;
import zrdunittests.UnitTest_JsonFiles;
import zrdunittests.UnitTest_TransactionSetData;

/**
 *
 * @author Zach
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        runUnitTests();
        ProcessJsonInformation.runProcess();
    }
    
    public static void runUnitTests(){
        //UnitTest_SocialNetwork.runTests();
        //UnitTest_Timestamp.runTests();
        UnitTest_JsonFiles.runTests();
        //UnitTest_TransactionSetData.runTests();
        //UnitTest_UserTransactionData.runTest();
    }
}
