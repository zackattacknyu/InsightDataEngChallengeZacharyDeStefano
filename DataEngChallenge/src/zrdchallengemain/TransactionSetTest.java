/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdchallengemain;

import java.util.Iterator;
import zrdnetworkdata.Transaction;
import zrdnetworkdata.TransactionSet;
import zrdnetworkdata.User;

/**
 *
 * @author Zach
 */
public class TransactionSetTest {
    
    public static void runTests(){
        
        
        System.out.println("-----TEST 1: TRANSACTIONS BEING ADDED AND NOT DELETED-----");
        runTransactionTest1(20,3,20,0.7);
        
        System.out.println();
        System.out.println("-----TEST 2: TRANSACTIONS BEING ADDED AND DELETED-----");
        runTransactionTest1(10,3,40,0.5);
        
    }
    
    public static void runTransactionTest1(int maxSize, int numUsers, 
            int numTrans,double probSwitchingTime){
        int userId;
        long currentTime = 1497378781000L;
        User[] users = new User[numUsers];
        for(int i = 0; i < numUsers; i++){
            users[i] = new User(i);
        }
        TransactionSet myset = new TransactionSet(maxSize);
        Transaction currentT;
        System.out.println("ORIGINAL SEQUENCE:");
        for(int i=0; i < numTrans; i++){
            userId=(int)(Math.random()*numUsers);
            if(Math.random() < probSwitchingTime){
                currentTime = currentTime+1000;
            }
            currentT=myset.addToSet(users[userId], currentTime, Math.random()*100);                        
            System.out.println(currentT);
        }
        System.out.println();
        
        System.out.println("REORDERED SEQUENCE:");
        Iterator allTrans = myset.transSet.descendingIterator();
        while(allTrans.hasNext()){
            System.out.println(allTrans.next());
        }
    }
    
}
