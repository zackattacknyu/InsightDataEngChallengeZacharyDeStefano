/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdnetworkdata;

import java.util.HashSet;

/**
 *
 * @author Zach
 */
public class User {
    
    private int idNumber;
    private double meanOfTnetworkTransactions;
    private double stdOfTnetworkTransactions;
    
    //used in the mean, std calculation
    private double sumXi,sumXiSquared,mean,std;
    
    private TransactionSet pastTransactionsInNetwork;
    
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
    
    public void recalculatePastTransactions(TransactionSet globalSet){
        pastTransactionsInNetwork = TransactionHelper.calculateUsersSet(this, globalSet);
    }

    public HashSet<User> getNeighbors() {
        return neighbors;
    }
    
    public HashSet<User> getSocialNetwork(){
        return SocialNetworkHelper.obtainSocialNetworkForUser(this);
    }

    public TransactionSet getPastTransactionsInNetwork() {
        return pastTransactionsInNetwork;
    }
    
    
    public void recalculateAll(){
        mean = TransactionHelper.getMean(pastTransactionsInNetwork);
        std = TransactionHelper.getStd(pastTransactionsInNetwork);        
    }
    
    private void recalculateMeanAndStd(){
        
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
