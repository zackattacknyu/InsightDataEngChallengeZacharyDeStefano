/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdchallengemain;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import zrdnetworkdata.AllUsersAndTransactions;
import zrdnetworkdata.SocialNetworkHelper;
import zrdnetworkdata.Transaction;
import zrdnetworkdata.TransactionHelper;
import zrdnetworkdata.User;

/**
 *
 * @author Zach
 */
public class UserTransactionTest {
    
    public static void runTest(){
        TransactionHelper.MAXIMUM_NUMBER_TRANSACTIONS_IN_NETWORK=10;
        SocialNetworkHelper.numDegreesSocialNetwork=2;
        int userId;
        long currentTime = 1497378781000L;
        
        
        int maxNumUsers = 20000;
        double probSwitchingTime = 0.1;
        int numTrans=(int)(2*Math.pow(10,6));
        int numFriendActions = (int)(5*Math.pow(10,5));
        double probDisplay = 0.005;
        
        AllUsersAndTransactions usersTrans = new AllUsersAndTransactions();
        
      
        Transaction currentT;
        User currentU;
        long startTime = Calendar.getInstance().getTimeInMillis();
        for(int i=0; i < numTrans; i++){
            userId=(int)(Math.random()*maxNumUsers);
            if(Math.random() < probSwitchingTime){
                currentTime = currentTime+1000;
            }
            currentU = usersTrans.addUser(userId);
            usersTrans.addToTransSet(currentU, currentTime, Math.random()*100);                        
        }
        
        usersTrans = SocialNetworkTest.addRandomFriendships(usersTrans, numFriendActions);
        usersTrans.calculateTransForAllUsers();
        
        long endTime = Calendar.getInstance().getTimeInMillis();
        double elapsedTime = (endTime-startTime)/1000.0;
        
        HashSet<User> users = usersTrans.getAllUsersSet();
        for(User user: users){
            
            if(Math.random() < probDisplay){
                //display the sequence with this user
                System.out.println("----TEST: REORDERED SEQUENCE OF USER " + user.hashCode());
                Iterator currentTransactions = user.getPastTransactionsInNetwork().getTransSet().descendingIterator();
                while(currentTransactions.hasNext()){
                    System.out.println(currentTransactions.next());
                }
                System.out.println("---------------------------");
            }
            
        }
        
        
        System.out.println("TOTAL CALCULATION TIME: " + 
                    elapsedTime + " SECONDS");
        
        
    }
    
}
