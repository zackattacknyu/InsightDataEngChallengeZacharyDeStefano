/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdnetworkdata;

import java.util.HashSet;

/**
 * Helper class that implements some social network functionality
 *
 * @author Zach
 */
public class SocialNetworkHelper {
    
    /**
     * "D" parameter value
     */
    public static int numDegreesSocialNetwork=2;
    
    /**
     * Does unfriending between user number 1 and user number 2
     * @param user1 user number 1 
     * @param user2 user number 2
     */
    public static void endFriendship(User user1, User user2){
        
        user1.removeNeighbor(user2);
        user2.removeNeighbor(user1);
    }
    
    /**
     * Does friending between user 1 and user 2
     * @param user1 user number 1
     * @param user2 user number 2
     */
    public static void beginFriendship(User user1, User user2){
        user1.addNeighbor(user2);
        user2.addNeighbor(user1);
    }

    /**
     * Obtains the social network of the starting users
     *      Returns a set that DOES NOT include the starting users
     * @param startingUsers set of users that are the base nodes
     * @return  set representing the social network
     */
    public static HashSet<User> obtainSocialNetwork(HashSet<User> startingUsers) {
        HashSet<User> usersInNetwork = new HashSet<>();
        HashSet<User> currentNodes = startingUsers;
        HashSet<User> neighborsOfCurrent;
        usersInNetwork.addAll(startingUsers);
        
        //the BFS search is done in this loop
        for (int j = 0; j < numDegreesSocialNetwork; j++) {
            neighborsOfCurrent = new HashSet<>();
            for (User node : currentNodes) {
                for (User neighbor : node.getNeighbors()) {
                    if (!usersInNetwork.contains(neighbor)) {
                        neighborsOfCurrent.add(neighbor);
                        usersInNetwork.add(neighbor);
                    }
                }
            }
            currentNodes = neighborsOfCurrent;
        }
        
        //make sure starting users are not included
        usersInNetwork.removeAll(startingUsers);
        
        return usersInNetwork;
    }

    /**
     * Does a breadth-first search to obtain the users
     *      whose transaction log needs to be updated
     *      by the change in the social network. 
     * Result INCLUDES the original users too
     * @param user1     user number 1
     * @param user2     user number 2
     * @return  users who need to be updated
     */
    public static HashSet<User> obtainUsersToUpdate(User user1, User user2) {
        HashSet<User> startingUsers = new HashSet<User>();
        startingUsers.add(user2);
        startingUsers.add(user1);
        HashSet<User> usersInNetwork = obtainSocialNetwork(startingUsers);
        usersInNetwork.addAll(startingUsers);
        return usersInNetwork;
    }

    /**
     * Obtains a user's social network. Result DOES NOT INCLUDE the original
     *      user
     * @param user1     user to obtain network for
     * @return      set of users in their network
     */
    public static HashSet<User> obtainSocialNetworkForUser(User user1) {
        HashSet<User> userSet = new HashSet<User>();
        userSet.add(user1);
        return obtainSocialNetwork(userSet);
    }
    
}
