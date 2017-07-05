/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdnetworkdata;

import java.util.HashMap;
import java.util.HashSet;

/**
 * This stores all the users and all the transactions
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
    
    /**
     * Adds a new user or returns an existing one
     * @param newUserId     the id number for the user
     * @return pointer to User object with that id number
     */
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

    /**
     * Adds a friendship. Updates the set of pointers in both users sets
     * Does NOT update the transaction maps for the users
     * @param user1id user1 id number
     * @param user2id user2 id number
     */
    public void addFriendship(int user1id, int user2id){
        User user1=addUser(user1id); User user2= addUser(user2id);
        SocialNetworkHelper.beginFriendship(user1,user2);
    }
    
    /**
     * Removes a friendship. Updates the set of pointers in both users sets
     * Does NOT update the transaction maps for the users
     * @param user1id user1 id number
     * @param user2id user2 id number
     */
    public void removeFriendship(int user1id, int user2id){
        User user1=addUser(user1id); User user2= addUser(user2id);
        SocialNetworkHelper.endFriendship(user1,user2);
    }
    
    /**
     * Recalculates the past transactions for all users in the network
     */
    public void calculateTransForAllUsers(){
        for(User user: allUsersSet){
            user.recalculatePastTransactions(transactionSet);
        }
    }
    
    /**
     * Recalculates the mean, std for all users in network
     */
    public void calculateMeanStdForAllUsers(){
        for(User user: allUsersSet){
            user.recalculateAll();
        }
    }
    
    /**
     * Adds a transaction to the set
     * @param userX         user object behind transaction
     * @param transTime     time when transaction was done
     * @param amount        amount of transaction
     */
    public void addToTransSet(User userX,long transTime,double amount){
        transactionSet.addToSet(userX, transTime, amount);
    }
    
    /**
     * Adds transaction to set and uses user Id
     * @param userId        user id
     * @param transTime     time of transaction
     * @param amount        amount of transaction
     */
    public void addJsonTranscation(int userId, long transTime, double amount){
        addJsonTranscation(userId,transTime,amount,false);
    }
    
    /**
     * Adds transaction to set, does update if in streaming mode
     * @param userId        user id integer
     * @param transTime     time of transaction
     * @param amount        amount of transaction
     * @param stream        whether or not we are streaming
     * @return user object
     */
    public User addJsonTranscation(int userId, long transTime, double amount,boolean stream){
        User user0=addUser(userId);
        addToTransSet(user0,transTime,amount);
        if(stream){
            user0.recalculatePastTransactions(transactionSet);
            user0.recalculateAll();
        }
        return user0;
    }

    
    
}
