/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdnetworkdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

/**
 *
 * @author Zach
 */
public class TransactionSet {
    
    
    public int maximumSize = TransactionHelper.MAXIMUM_NUMBER_TRANSACTIONS_IN_NETWORK;
    public int currentSize=0;
    
    /*
     * Handle conflicting timestamps
     */
    
    public HashMap<Long,Integer> lastTransNumberUsed;
    public HashMap<Long,Integer> numTransPerTime;
    
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
    
    public void incrementMaxSize(){
        maximumSize = maximumSize + TransactionHelper.MAXIMUM_NUMBER_TRANSACTIONS_IN_NETWORK;
    }
    
    public Transaction addToSet(User userX,long transTime,double amount){
        int transNumber = addToHashMap(lastTransNumberUsed,transTime);
        addToHashMap(numTransPerTime,transTime);
        
        Transaction trans=new Transaction(transTime,transNumber,amount,userX);
        addTransaction(trans);
        return trans;
    }
    
    public void addTransaction(Transaction trans){
        if(currentSize < maximumSize){
            currentSize++;
        }else{
            removeEarliest();
        }
        transSet.add(trans);
    }
    
    private int addToHashMap(HashMap<Long,Integer> toAdd, long transTime){
        int numberUse=0;
        if(toAdd.containsKey(transTime)){
            numberUse=toAdd.get(transTime);
        }
        numberUse++;
        toAdd.put(transTime,numberUse);
        return numberUse;
    }
    
    private void removeEarliest(){
        Transaction lastOne = transSet.pollFirst();
        
        if(numTransPerTime.isEmpty()){
            return;
        }
        
        long transTime = lastOne.getTransactionTime();
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
    
    public ArrayList<Double> getAmounts(){
        ArrayList<Double> transAmounts = new ArrayList<>(transSet.size());
        for(Transaction transaction: transSet){
            transAmounts.add(transaction.getAmount());
        }
        return transAmounts;
    }
    

    
}
