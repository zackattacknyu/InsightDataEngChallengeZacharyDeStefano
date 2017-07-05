/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdnetworkdata;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 *
 * This implements some of the transaction functionality
 * 
 * @author Zach
 */
public class TransactionHelper {
    
    /**
     * This is the "T" parameter
     */
    public static int MAXIMUM_NUMBER_TRANSACTIONS_IN_NETWORK = 50;
    
    /**
     * This calculates the set of T transactions in a given user's social network
     * @param userX         the user
     * @param globalSet     the set of all transactions 
     * @return  the set of all transactions in a user's social network
     */
    public static TransactionSet calculateUsersSet(User userX,TransactionSet globalSet){
        TransactionSet transForUsers = new TransactionSet();
        
        //obtain iterator to go through most recent transactions in network
        Iterator mostRecentTransactions = globalSet.getTransSet().descendingIterator();
        
        //obtain the social network for a user
        HashSet<User> socialNetwork = SocialNetworkHelper.obtainSocialNetworkForUser(userX);
        
        int numberTransactionsIncluded = 0;
        Transaction currentTrans;
        
        /*
         * iterate through most recent transactions until one of the following happens:
         *      (1) T are included
         *      (2) there are no more left in network
         */
        while(mostRecentTransactions.hasNext() && 
                numberTransactionsIncluded < TransactionHelper.MAXIMUM_NUMBER_TRANSACTIONS_IN_NETWORK){
            
            currentTrans = (Transaction) mostRecentTransactions.next();
            
            if(socialNetwork.contains(currentTrans.getTransactionUser())){ //verify transaction is in user's social network

                transForUsers.addTransaction(currentTrans);
                numberTransactionsIncluded++;
            }
            
        }
        return transForUsers;
    }
    
    /**
     * gets the mean of a set of transactions
     * @param transactions  set of transactions
     * @return mean value
     */
    public static double getMean(TransactionSet transactions){
        return getMean(transactions.getAmounts());
    }
    
    /**
     * gets the mean of an array list of doubles
     * @param doubleArr     array list of doubles
     * @return  mean value
     */
    public static double getMean(ArrayList<Double> doubleArr){
        double sum = 0;
        double denom=0;
        for(Double num: doubleArr){
            sum+=num; denom++;
        }
        return sum/denom;
    }
    
    /**
     * gets the std of an array list of doubles
     * @param doubleArr array list of doubles
     * @return  std value
     */
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
    
    /**
     * gets the std of a set of transactions
     * @param transactions  transaction set
     * @return  std value
     */
    public static double getStd(TransactionSet transactions){
        return getStd(transactions.getAmounts());
    }
    
    /**
     * Takes in the amount, mean, and std and tells whether the purchase
     *      is over 3 std's away
     * @param amount        purchase amount
     * @param mean          mean value
     * @param std           std value
     * @return  whether purchase is > mean+3*std
     */
    public static boolean isAnamoly(double amount, double mean, double std){
        return (amount > (mean + 3*std));
    }
    
}
