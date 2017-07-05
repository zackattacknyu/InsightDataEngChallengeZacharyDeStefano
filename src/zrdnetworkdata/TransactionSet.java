/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdnetworkdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

/**
 * This implements a set of transactions as per our rules. 
 * It is used for 3 things:
 *      - Transactions for a single user
 *      - Transactions in a user's social network
 *      - Global set of transactions across all users
 * 
 * This is also where we handle conflicting timestamps
 * That is done in the global set
 * 
 * @author Zach
 */
public class TransactionSet {
    
    /**
     * maximum size of this set
     * it starts at T, but this is allowed to vary for the case where
     *      we are storing all the user's transactions
     */
    public int maximumSize = TransactionHelper.MAXIMUM_NUMBER_TRANSACTIONS_IN_NETWORK;
    
    public int currentSize=0;
    
    /* Hashmaps used to help
     * handle conflicting timestamps
     */
    public HashMap<Long,Integer> lastTransNumberUsed;
    public HashMap<Long,Integer> numTransPerTime;
    
    /**
     * set of all transactions
     * TreeSet ensures it will return in our desired order
     */
    public TreeSet<Transaction> transSet;
    
    public TransactionSet(){
        this(TransactionHelper.MAXIMUM_NUMBER_TRANSACTIONS_IN_NETWORK);
    }
    public TransactionSet(int maxSize){
        this.maximumSize = maxSize;
        transSet = new TreeSet<>();
        lastTransNumberUsed = new HashMap<>();
        numTransPerTime = new HashMap<>();
    }
    
    /**
     * If we are making the global set of all users, then as soon as new user
     *      appears, the max size of this set is incremented by T
     */
    public void incrementMaxSize(){
        maximumSize = maximumSize + TransactionHelper.MAXIMUM_NUMBER_TRANSACTIONS_IN_NETWORK;
    }
    
    /**
     * Adds a transaction to the set
     * @param userX         user behind transaction
     * @param transTime     time of transaction
     * @param amount        amount of transaction
     * @return          final transaction object made
     */
    public Transaction addToSet(User userX,long transTime,double amount){
        //obtains the transaction number we should use
        int transNumber = addToHashMap(lastTransNumberUsed,transTime);
        
        //increments the map saying how many transactions appeared at that time
        addToHashMap(numTransPerTime,transTime);
        
        //makes a new transaction object with the information
        Transaction trans=new Transaction(transTime,transNumber,amount,userX);
        
        //adds the transaction to the set
        addTransaction(trans);
        
        return trans;
    }
    
    /**
     * This adds a transaction to the set of all transactions. 
     * If we are over max size, then the oldest one is deleted
     * @param trans 
     */
    public void addTransaction(Transaction trans){
        if(currentSize < maximumSize){
            currentSize++; //update size
        }else{
            removeEarliest(); //delete oldest one
        }
        transSet.add(trans); //add our transaction
    }
    
    /**
     * adds to the hashmap of next transaction numbers or total num of transactions
     *          and returns appropriate number
     * @param toAdd         original hashmap
     * @param transTime     time of transaction
     * @return  new transaction number to use 
     */
    private int addToHashMap(HashMap<Long,Integer> toAdd, long transTime){
        //gets existing number for that time if it exists
        int numberUse=0;
        if(toAdd.containsKey(transTime)){
            numberUse=toAdd.get(transTime);
        }
        
        //increments existing number
        numberUse++;
        
        //put new incremented number back into map
        toAdd.put(transTime,numberUse);
        
        return numberUse;
    }
    
    /**
     * removes the earliest entry from the set
     */
    private void removeEarliest(){
        
        //take out the earliest entry in the set
        Transaction lastOne = transSet.pollFirst();
        
        //if no transactions, move on
        if(numTransPerTime.isEmpty()){
            return;
        }
        
        //obtain the transaction time of the one removed
        long transTime = lastOne.getTransactionTime();
        
        /*
         * If the one removed was the last one in the hash maps 
         *      detailing the next transaction number for each time,
         *      then remove that entry from the hashmap
         * We do not want these maps to grow too large, hence the removal
         * 
         * If it is not the last one, then we will decrement the number of 
         *      transactions at the time step. I purposefully DO NOT decrement
         *      the transaction number in order to ensure no conflicts
         */
        if(numTransPerTime.get(transTime) < 2){
            numTransPerTime.remove(transTime);
            lastTransNumberUsed.remove(transTime);
        }else{
            int currentNumTrans = numTransPerTime.get(transTime);
            numTransPerTime.put(transTime, currentNumTrans-1);
        }
    }

    public TreeSet<Transaction> getTransSet() {
        return transSet;
    }
    
    /**
     * During testing, this prints the transaction data
     *      and the amounts of each transaction
     */
    public void printAllInfo(){
        System.out.println("NETWORK TRANSACTION INFO");
        for(Transaction trans: transSet){
            System.out.println(trans);
        }
        System.out.println();
        System.out.println("NETWORK TRANSACTION AMOUNTS:");
        for(Transaction trans: transSet){
            System.out.println(trans.getAmount());
        }
    }
    
    /**
     * returns an array list of doubles with each of the amounts
     * @return  array list of doubles for all the amounts
     */
    public ArrayList<Double> getAmounts(){
        ArrayList<Double> transAmounts = new ArrayList<>(transSet.size());
        for(Transaction transaction: transSet){
            transAmounts.add(transaction.getAmount());
        }
        return transAmounts;
    }
    

    
}
