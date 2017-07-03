/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdnetworkdata;

import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

/**
 *
 * @author Zach
 */
public class TransactionHelper {
    public static int MAXIMUM_NUMBER_TRANSACTIONS_IN_NETWORK = 50;
    
    
    public static TransactionSet calculateUsersSet(User userX,TransactionSet globalSet){
        TransactionSet transForUsers = new TransactionSet();
        Iterator mostRecentTransactions = globalSet.getTransSet().descendingIterator();
        HashSet<User> socialNetwork = SocialNetworkHelper.obtainSocialNetworkForUser(userX);
        int numberTransactionsIncluded = 0;
        Transaction currentTrans;
        while(mostRecentTransactions.hasNext() && 
                numberTransactionsIncluded < TransactionHelper.MAXIMUM_NUMBER_TRANSACTIONS_IN_NETWORK){
            
            currentTrans = (Transaction) mostRecentTransactions.next();
            if(socialNetwork.contains(currentTrans.getTransactionUser())){

                transForUsers.addTransaction(currentTrans);
                numberTransactionsIncluded++;
            }
            
        }
        return transForUsers;
    }
    
}
