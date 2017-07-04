/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdchallengemain;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Zach
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SocialNetworkTest.runTests();
        TimestampTests.runTests();
        JsonFileTest.runTests();
        TransactionSetTest.runTests();
        UserTransactionTest.runTest();
        ProcessJsonInformation.runProcess();
    }
}
