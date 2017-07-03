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
public class AllUsersAndTransactions {
    
    private HashMap<Integer,User> allUsers;
    private HashSet<User> allUsersSet;
    
    private TransactionSet transactionSet;

    public AllUsersAndTransactions(){
        allUsers = new HashMap<>();
        allUsersSet = new HashSet<>();
        transactionSet = new TransactionSet();
    }
    
    public AllUsersAndTransactions(int firstUserId){
        this();
        addUser(firstUserId);
    }
    
    public int getNumUsers(){
        return allUsers.size();
    }
    
    public User addUser(int newUserId){
        if(!allUsers.containsKey(newUserId)){
            User newUser = new User(newUserId);
            allUsers.put(newUserId,newUser);
            allUsersSet.add(newUser);
            transactionSet.incrementMaxSize();
            return newUser;
        }else{
            return allUsers.get(newUserId);
        }
    }
    
    public User getUser(int id){
        return allUsers.get(id);
    }
   

    public HashSet<User> getAllUsersSet() {
        return allUsersSet;
    }

    public void addFriendship(int user1id, int user2id){
        SocialNetworkHelper.beginFriendship(addUser(user1id), addUser(user2id));
    }
    
    public void removeFriendship(int user1id,int user2id){
        SocialNetworkHelper.endFriendship(addUser(user1id), addUser(user2id));
    }
    
    public void calculateTransForAllUsers(){
        for(User user: allUsersSet){
            user.recalculatePastTransactions(transactionSet);
        }
    }
    
    public void addToTransSet(User userX,long transTime,double amount){
        transactionSet.addToSet(userX, transTime, amount);
    }
    
    public void addJsonTranscation(int userId, long transTime, double amount){
        User user0=addUser(userId);
        addToTransSet(user0,transTime,amount);
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
