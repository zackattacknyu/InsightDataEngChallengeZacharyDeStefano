/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdnetworkdata;

import java.util.HashSet;

/**
 * Instances of this class are our users
 *
 * @author Zach
 */
public class User {
    
    //user id number from Json File
    private int idNumber;
    
    //mean, std calculation
    private double mean,std;
    
    //set of transactions in user's social network
    private TransactionSet pastTransactionsInNetwork;
    
    //user's friends
    private HashSet<User> neighbors;
    
    /**
     * Initializes user
     * @param id    user id integer
     */
    public User(int id){
        idNumber=id;
        neighbors = new HashSet<>();
    }
    
    /**
     * remove a friend from their list
     * @param neighbor friend to remove
     */
    public void removeNeighbor(User neighbor){
        neighbors.remove(neighbor);
    }
    
    /**
     * add a friend to their list
     * @param neighbor friend to add
     */
    public void addNeighbor(User neighbor){
        neighbors.add(neighbor);
    }
    
    /**
     * recalculates the transactions in the social network
     * @param globalSet     pointer to set of all recent transactions
     */
    public void recalculatePastTransactions(TransactionSet globalSet){
        pastTransactionsInNetwork = TransactionHelper.calculateUsersSet(this, globalSet);
    }

    public HashSet<User> getNeighbors() {
        return neighbors;
    }
    
    /**
     * returns the social network. 
     * this is purposefully NOT stored or else it would overload the Memory Heap
     * @return  set of users in social network
     */
    public HashSet<User> getSocialNetwork(){
        return SocialNetworkHelper.obtainSocialNetworkForUser(this);
    }

    public TransactionSet getPastTransactionsInNetwork() {
        return pastTransactionsInNetwork;
    }
    
    /**
     * recalculates the mean and std of all past transactions in network
     */
    public void recalculateAll(){
        mean = TransactionHelper.getMean(pastTransactionsInNetwork);
        std = TransactionHelper.getStd(pastTransactionsInNetwork);        
    }
    
    /**
     * tells if amount for user is anomalous given their network mean, std
     * @param amount    amount of purchase
     * @return      whether it is anomalous 
     */
    public boolean isAmountAnamolous(double amount){
        return TransactionHelper.isAnamoly(amount, mean, std);
    }
    
    /**
     * the hash code the users will be the integer user id
     * @return  integer user id
     */
    @Override
    public int hashCode(){
        return idNumber;
    }

    /**
     * Two users are considered equal if they are an id
     * @param obj   other user
     * @return  whether or not they share an id
     */
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

    public double getMean() {
        return mean;
    }

    public double getStd() {
        return std;
    }
    
}
