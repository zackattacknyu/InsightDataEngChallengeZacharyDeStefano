/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdchallengemain;

import java.util.Calendar;
import zrdnetworkdata.SocialNetworkHelper;
import zrdnetworkdata.User;
import zrdnetworkdata.UserTransactionSet;

/**
 *
 * @author Zach
 */
public class SocialNetworkTest {
    
    public static void runTests(){
        
        /**
        * Test 1,2,3 will use the following network:
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
        * 
        * 
        */
        
        /*
         * Test1: Original Network above
         */
        System.out.println("----------Test 1: Original Network------------");
        UserTransactionSet test1set = constructNetwork1();
        displayNetworkWithDifferentNumConnections(test1set,1,4);
        displayLines();
        
        /*
         * Test 2: Remove the 3,5 friendship
         */
        System.out.println("------Test 2: 3,5 Friendship Removed------------");
        test1set.removeFriendship(3, 5);
        displayNetworkWithDifferentNumConnections(test1set,1,4);
        displayLines();
        
        /*
         * Test 3: Remove the 2,4 friendship
         * so that there are two connected components
         */
        System.out.println("------Test 3: 2,4 Friendship Removed------------");
        test1set.removeFriendship(2,4);
        displayNetworkWithDifferentNumConnections(test1set,1,4);
        displayLines();
        
        /*
         * Test 4: 4 connected components, including lone nodes and 6 degree
         *      connections included
         * 
         * This is the network: 
         * 1--2--3--4--5--6--7   10--11--12  13  14
         *    |     |
         *    8     9
         */
        System.out.println("------Test 4: New Network------------");
        UserTransactionSet net4 = constructNetwork4();
        displayNetworkWithDifferentNumConnections(net4,1,6);
        displayLines();
        
        
        /*
         * Test 5: Test Scalability. Randomly generate millions of users
         *      and millions of conections. Print out random subset
         *      of the network. Test performance. 
         */
        long startingTime = Calendar.getInstance().getTimeInMillis();
        constructLargeRandomNetwork();
        long endingTime = Calendar.getInstance().getTimeInMillis();
        double timeElapsed = (endingTime-startingTime)/1000.0;
        System.out.println("Time to do Test 5: " + timeElapsed + " seconds");
        
    }
    
    public static void displayLines(){
        System.out.println("-----------------------------------");
    }
    
    public static void displayNetworkWithDifferentNumConnections(UserTransactionSet theSet,int minNum, int maxNum){
        for(int numConn = minNum; numConn <= maxNum; numConn++){
            System.out.println("Network with Number Connections set to: " + numConn);
            SocialNetworkHelper.numDegreesSocialNetwork=numConn;
            theSet.recalculateNetwork();
            displayNetwork(theSet);
            System.out.println();
        }
    }
    
    public static UserTransactionSet constructLargeRandomNetwork(){
        /*
         * There were 100,000 "befriend" actions in the large json file
         *      so I will use 100,000 users an upper bound on the number
         *      to handle at once. 
         * And I will make 500,000 random "befriend" actions
         */
        int numUsers = (int)Math.pow(10, 5);
        int numFriendActions = (int)(2*Math.pow(10,5));
        
        UserTransactionSet theSet = new UserTransactionSet();
        for(int kk=0; kk < numUsers; kk++){
            theSet.addUser(kk);
        }
        
        for(int kk=0; kk < numFriendActions; kk++){
            
            int randId1 = (int)(Math.floor(numUsers*Math.random()));
            int randId2 = (int)(Math.floor(numUsers*Math.random()));
            if(randId1 != randId2){
                if(Math.random()<0.5){
                    theSet.addFriendship(randId1, randId2);
                }else{
                    theSet.removeFriendship(randId1, randId2);
                }
            }
            
            
            
        }
        
        return theSet;
    }
    
    public static UserTransactionSet constructNetwork4(){
        UserTransactionSet theSet = new UserTransactionSet();
        for(int kk=1;kk<=14;kk++){
            theSet.addUser(kk);
        }
        theSet.addFriendship(1, 2);
        theSet.addFriendship(2, 3);
        theSet.addFriendship(3, 4);
        theSet.addFriendship(4, 5);
        theSet.addFriendship(5, 6);
        theSet.addFriendship(6, 7);
        theSet.addFriendship(2, 8);
        theSet.addFriendship(4, 9);
        theSet.addFriendship(10, 11);
        theSet.addFriendship(11, 12);
        
        
        theSet.recalculateNetwork();
        return theSet;
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
