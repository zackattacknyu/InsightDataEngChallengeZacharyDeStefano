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
    public int currentSize;
    
    /*
     * Handle conflicting timestamps
     */
    
    public HashMap<Long,Integer> mapOfNextTransactionNumberToUse;
    public HashMap<Long,Integer> mapOfNumberTransactionsByTimestamp;
    
    public TreeSet<Transaction> transSet;
    
    public void addToSet(Transaction trans){
        
        if(currentSize < maximumSize){
            currentSize++;
        }else{
            transSet.pollLast();
        }
        transSet.add(trans);
        
    }
    
}
