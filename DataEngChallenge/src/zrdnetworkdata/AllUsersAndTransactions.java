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
        User user1=addUser(user1id); User user2= addUser(user2id);
        SocialNetworkHelper.beginFriendship(user1,user2);
    }
    
    public void removeFriendship(int user1id,int user2id){
        removeFriendship(user1id,user2id,false);
    }
    public void removeFriendship(int user1id, int user2id,boolean stream){
        User user1=addUser(user1id); User user2= addUser(user2id);
        SocialNetworkHelper.endFriendship(user1,user2);
    }
    
    public void calculateTransForAllUsers(){
        for(User user: allUsersSet){
            user.recalculatePastTransactions(transactionSet);
        }
    }
    
    public void calculateMeanStdForAllUsers(){
        for(User user: allUsersSet){
            user.recalculateAll();
        }
    }
    
    public void addToTransSet(User userX,long transTime,double amount){
        transactionSet.addToSet(userX, transTime, amount);
    }
    
    public void addJsonTranscation(int userId, long transTime, double amount){
        addJsonTranscation(userId,transTime,amount,false);
    }
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
