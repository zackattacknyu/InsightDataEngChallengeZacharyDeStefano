/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdnetworkdata;

import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

/**
 *
 * @author Zach
 */
public class UserTransactionSet {
    
    private HashMap<Integer,User> allUsers;
    private HashSet<User> allUsersSet;
    
    private TreeSet<Transaction> all_NT_Transactions;
    private int numberTranscations; 
    
    public UserTransactionSet(){
        allUsers = new HashMap<>();
        allUsersSet = new HashSet<>();
    }
    
    public UserTransactionSet(int firstUserId){
        this();
        addUser(firstUserId);
    }
    
    public void addUser(int newUserId){
        User newUser = new User(newUserId);
        allUsers.put(newUserId,newUser);
        allUsersSet.add(newUser);
    }

    public HashSet<User> getAllUsersSet() {
        return allUsersSet;
    }
    
    
    
    public void recalculateNetwork(){
        SocialNetworkHelper.recalculateSocialNetwork(allUsersSet);
    }
    
    public void addFriendship(int user1id, int user2id){
        SocialNetworkHelper.beginFriendship(allUsers.get(user1id), allUsers.get(user2id));
    }
    
    public void removeFriendship(int user1id,int user2id){
        SocialNetworkHelper.endFriendship(allUsers.get(user1id), allUsers.get(user2id));
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
