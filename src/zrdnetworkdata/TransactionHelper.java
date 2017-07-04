/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdnetworkdata;

import java.util.ArrayList;
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
    
    public static double getMean(TransactionSet transactions){
        return getMean(transactions.getAmounts());
    }
    
    public static double getMean(ArrayList<Double> doubleArr){
        double sum = 0;
        double denom=0;
        for(Double num: doubleArr){
            sum+=num; denom++;
        }
        return sum/denom;
    }
    
    public static double getStd(ArrayList<Double> doubleArr){
        double mean = getMean(doubleArr); double denom=0;
        double sumDiffSq = 0;
        double diff,diffSq;
        for(Double tAmount: doubleArr){
            diff=tAmount-mean;
            diffSq=Math.pow(diff,2);
            sumDiffSq += diffSq; denom++;
        }
        return Math.sqrt(sumDiffSq/denom);
    }
    
    public static double getStd(TransactionSet transactions){
        return getStd(transactions.getAmounts());
    }
    
    public static boolean isAnamoly(double amount, double mean, double std){
        return (amount > (mean + 3*std));
    }
    
}
