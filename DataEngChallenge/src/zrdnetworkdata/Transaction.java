/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdnetworkdata;

import java.util.Objects;

/**
 *
 * @author Zach
 */
public class Transaction implements Comparable{
    
    private long transactionTime;
    private int transactionNumberInSequence;
    
    private double amount;
    
    private User transactionUser;

    public Transaction(long transactionTime, int transactionNumberInSequence, double amount, User transactionUser) {
        this.transactionTime = transactionTime;
        this.transactionNumberInSequence = transactionNumberInSequence;
        this.amount = amount;
        this.transactionUser = transactionUser;
    }

    public long getTransactionTime() {
        return transactionTime;
    }

    public int getTransactionNumberInSequence() {
        return transactionNumberInSequence;
    }

    public double getAmount() {
        return amount;
    }

    public User getTransactionUser() {
        return transactionUser;
    }
    
    

    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Transaction other = (Transaction) obj;
        if (this.transactionTime != other.transactionTime) {
            return false;
        }
        if (this.transactionNumberInSequence != other.transactionNumberInSequence) {
            return false;
        }
        if (!Objects.equals(this.transactionUser, other.transactionUser)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Object obj) {
        Transaction other = (Transaction)obj;
        if(other.transactionTime==this.transactionTime){
            if(this.transactionNumberInSequence < other.transactionNumberInSequence){
                return -1;
            }
            else if(this.transactionNumberInSequence == other.transactionNumberInSequence){
                return 0;
            }
            else if(this.transactionNumberInSequence > other.transactionNumberInSequence){
                return 1;
            }
        }else if(this.transactionTime < other.transactionTime){
            return -1;
        }else if(this.transactionTime > other.transactionTime){
            return 1;
        }
        return -1;
    }

    

    
    
    
    
}
