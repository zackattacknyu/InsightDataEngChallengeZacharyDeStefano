/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdnetworkdata;

import java.util.Objects;

/**
 * Class whose instances are the transactions in our network
 * 
 * Comparable is implemented so that the TreeMap class will sort these
 * They are sorted by milliseconds since 1970 when transaction occurred
 *      as the first field. The second field is the order in which it appeared
 *      in the log. 
 *
 * @author Zach
 */
public class Transaction implements Comparable{
    
    private long transactionTime;
    private int transactionNumberInSequence;
    
    private double amount;
    
    private User transactionUser;

    /**
     * Initializes the transaction
     * @param transactionTime       millis since 1970 when occurred
     * @param transactionNumberInSequence   order it appeared in the log among ones with same time
     * @param amount            amount of transaction
     * @param transactionUser   user behind transaction
     */
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
    
    

    

    /**
     * Two transactions will be considered equal if they match on the following:
     *      - Time
     *      - Number in Sequence at that Time
     *      - User
     * @param obj other transaction
     * @return whether or not they are equal
     */
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

    /**
     * Compares two transactions. One is greater than another if it appeared
     *      later. First we check the timestamp. If those are equal,
     *      then we check the sequence number. 
     * @param obj   other transaction
     * @return -1,0,1 as per normal compareTo rules
     */
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

    @Override
    public String toString() {
        return "Transaction{" + "time=" + transactionTime + 
                ", numInSeq=" + transactionNumberInSequence + 
                ", amount=" + amount + 
                ", user=" + transactionUser.hashCode() + '}';
    }

    

    
    
    
    
}
