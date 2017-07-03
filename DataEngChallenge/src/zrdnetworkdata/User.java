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
public class User {
    
    private int idNumber;
    private double meanOfTnetworkTransactions;
    private double stdOfTnetworkTransactions;
    
    //used in the mean, std calculation
    private double sumXi;
    private double sumXiSquared;
    
    private TreeSet<Transaction> pastTransactionsInNetwork;
    
    private HashSet<User> neighbors;
    
    public User(int id){
        idNumber=id;
        neighbors = new HashSet<>();
    }
    
    public void removeNeighbor(User neighbor){
        neighbors.remove(neighbor);
    }
    
    public void addNeighbor(User neighbor){
        neighbors.add(neighbor);
    }
    
    public void recalculatePastTransactions(TreeSet<Transaction> globalSet){
        pastTransactionsInNetwork = new TreeSet<>();
        Iterator mostRecentTransactions = globalSet.descendingIterator();
        HashSet<User> socialNetwork = SocialNetworkHelper.obtainSocialNetworkForUser(this);
        int numberTransactionsIncluded = 0;
        Transaction currentTrans;
        while(mostRecentTransactions.hasNext() && 
                numberTransactionsIncluded < TransactionHelper.MAXIMUM_NUMBER_TRANSACTIONS_IN_NETWORK){
            
            currentTrans = (Transaction) mostRecentTransactions.next();
            if(socialNetwork.contains(currentTrans.getTransactionUser())){
                
            }
            
        }
    }

    public HashSet<User> getNeighbors() {
        return neighbors;
    }
    
    public HashSet<User> getSocialNetwork(){
        return SocialNetworkHelper.obtainSocialNetworkForUser(this);
    }


    public void recalculateNetwork(){
        /*
         * Goes through friends of friends ...
         * and calculates the social network
         */
    }
    
    
    public void recalculateAll(){
        /*
         * When adding or deleting a friendship,
         * This will go through the global list of transactions
         *      and recalculate the T most recent that
         *      are in the user's social network
         * 
         * It will then find the mean and STD of those transactions
         */
        
    }
    
    public void recalculateMeanAndStd(){
        
        /*
         * When one transaction is added to a user's list, 
         * there might be one removed
         * 
         * This updates sumXi and sumXiSquared based on the addition
         *  and removal and then recalculates the mean and Std
         * 
         * Mean = SumXi/N
         * Std = Sqrt( Mean^2 + 1/N ( sumXi - 2*Mean*SumXi ) ) 
         */
        
    }
    
    
    @Override
    public int hashCode(){
        return idNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (this.idNumber != other.idNumber) {
            return false;
        }
        return true;
    }
    
}
