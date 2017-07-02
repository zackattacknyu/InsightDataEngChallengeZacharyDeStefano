/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdchallengemain;

import zrdnetworkdata.SocialNetworkHelper;
import zrdnetworkdata.User;
import zrdnetworkdata.UserTransactionSet;

/**
 *
 * @author Zach
 */
public class SocialNetworkTest {
    
    
    /**
     * This will test the following network:
     * 
     *  1--2--3
     *     |  |
     *     4--5
     *     | /
     *     6
     * 
     * I will test the degree 1, 2, 3 and 4 network
     *      for each user and make sure the output
     *      is as expected
     */
    public static void runTests(){
        
        /*
         * Test1: Original Network above
         */
        UserTransactionSet test1set = constructNetwork1();
        displayNetworkWithDifferentNumConnections(test1set,1,4);
        
        
        
    }
    
    public static void displayNetworkWithDifferentNumConnections(UserTransactionSet theSet,int minNum, int maxNum){
        for(int numConn = 1; numConn <= 4; numConn++){
            System.out.println("Network with Number Connections set to: " + numConn);
            SocialNetworkHelper.numDegreesSocialNetwork=numConn;
            displayNetwork(theSet);
            System.out.println();
        }
    }
    
    public static UserTransactionSet constructNetwork1(){
        UserTransactionSet theSet = new UserTransactionSet();
        for(int kk=1;kk<=6;kk++){
            theSet.addUser(kk);
        }
        theSet.addFriendship(1, 2);
        theSet.addFriendship(2, 3);
        theSet.addFriendship(2, 4);
        theSet.addFriendship(3, 5);
        theSet.addFriendship(4, 6);
        theSet.addFriendship(4, 5);
        theSet.addFriendship(5, 6);
        
        theSet.recalculateNetwork();
        return theSet;
    }
    
    public static void displayNetwork(UserTransactionSet theSet){
        
        
        for(User user:theSet.getAllUsersSet()){
            System.out.print("Network of " + user.hashCode() + ":");
            for(User inNetwork: user.getSocialNetwork()){
                System.out.print(inNetwork.hashCode() + " ");
            }
            System.out.println();
        }
    }
    
}
