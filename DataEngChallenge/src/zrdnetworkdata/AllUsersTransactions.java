/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdnetworkdata;

import java.util.HashSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author Zach
 */
public class AllUsersTransactions {
    
    private HashSet<User> allUsers;
    
    private TreeSet<Transaction> all_NT_Transactions;
    private int numberTranscations; 
    
    
    public void endFriendship(User user1, User user2){
        /*
         * Code here will unfriend the two people
         */
        user1.neighbors.remove(user2);
        user2.neighbors.remove(user1);
        //**RECALCULATE THE SOCIAL NETWORK HERE**
    }
    
    public void beginFriendship(User user1, User user2){
        /*
         * Code here will link two people
         */
        user1.neighbors.add(user2);
        user2.neighbors.add(user1);
        //**RECALCULATE THE SOCIAL NETWORK HERE**
    }
    
    public void addPurchase(Transaction trans){
        /*
         * Add the transaction to the large set of transcations
         */
        
        /*
         * If the set of transcations is now over a certain size,
         * delete the last entries until it is the correct size
         */
        
        /*
         * Update transcation logs for every user
         * in the social network
         */
        
        /*
         * Check if for any user, a flag was set
         */
        
    }
    
    
}
