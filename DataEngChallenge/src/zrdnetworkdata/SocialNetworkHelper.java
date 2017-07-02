/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package zrdnetworkdata;

import java.util.HashSet;

/**
 *
 * @author Zach
 */
public class SocialNetworkHelper {
    public static int numDegreesSocialNetwork;
    
    public static void endFriendship(User user1, User user2){
        /*
         * Code here will unfriend the two people
         */
        user1.removeNeighbor(user2);
        user2.removeNeighbor(user1);
        SocialNetworkHelper.recalculateSocialNetwork( user1,  user2);
    }
    
    public static void beginFriendship(User user1, User user2){
        /*
         * Code here will link two people
         */
        user1.addNeighbor(user2);
        user2.addNeighbor(user1);
        SocialNetworkHelper.recalculateSocialNetwork( user1,  user2);
    }

    public static void recalculateSocialNetwork(User user1, User user2) {
        HashSet<User> usersToUpdate = obtainUsersToUpdate(user1, user2);
        recalculateSocialNetwork(usersToUpdate);
    }
    public static void recalculateSocialNetwork(HashSet<User> usersToUpdate){
        for (User user : usersToUpdate) {
            user.setSocialNetwork(obtainSocialNetworkForUser(user));
        }
    }

    /**
     * Obtains the social network of the starting users
     *      Returns a set that DOES NOT include the starting users
     * @param startingUsers
     * @return
     */
    public static HashSet<User> obtainSocialNetwork(HashSet<User> startingUsers) {
        HashSet<User> usersInNetwork = new HashSet<>();
        HashSet<User> currentNodes = startingUsers;
        HashSet<User> neighborsOfCurrent;
        usersInNetwork.addAll(startingUsers);
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
        usersInNetwork.removeAll(startingUsers);
        return usersInNetwork;
    }

    /**
     * Does a breadth-first search to obtain the users
     *      whose transaction log needs to be updated
     *      by the change in the social network
     * @param user1
     * @param user2
     * @return
     */
    public static HashSet<User> obtainUsersToUpdate(User user1, User user2) {
        HashSet<User> startingUsers = new HashSet<User>();
        startingUsers.add(user2);
        startingUsers.add(user1);
        HashSet<User> usersInNetwork = obtainSocialNetwork(startingUsers);
        usersInNetwork.addAll(startingUsers);
        return usersInNetwork;
    }

    public static HashSet<User> obtainSocialNetworkForUser(User user1) {
        HashSet<User> userSet = new HashSet<User>();
        userSet.add(user1);
        return obtainSocialNetwork(userSet);
    }
    
}
