/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdnetworkdata;

import java.util.HashMap;
import java.util.TreeSet;

/**
 *
 * @author Zach
 */
public class TransactionSet {
    
    public int maximumSize;
    public int currentSize=0;
    
    /*
     * Handle conflicting timestamps
     */
    
    public HashMap<Long,Integer> lastTransNumberUsed;
    
    public TreeSet<Transaction> transSet;
    
    private void addToSet(User userX,long transTime,double amount){
        int transNumberUse=0;
        if(lastTransNumberUsed.containsKey(transTime)){
            transNumberUse=lastTransNumberUsed.get(transTime);
        }
        
        transNumberUse++;
        
        addToSet(new Transaction(transTime,transNumberUse,amount,userX));
        
        lastTransNumberUsed.put(transTime,transNumberUse);
    }
    
    private void addToSet(Transaction trans){
        
        if(currentSize < maximumSize){
            currentSize++;
        }else{
            transSet.pollLast();
        }
        transSet.add(trans);
        
    }
    
}
