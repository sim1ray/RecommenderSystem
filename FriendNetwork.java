/*
 * Simone Ray. Assignment 4. 6/14/19
 * The FriendNetwork class is constructed by reading in a data file of users and their friends.
 * The information is then used to implement methods to get the list of friends of a given user,
 * and the common friends of a given pair of users.
 */

import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.HashSet;

public class FriendNetwork {
    private HashMap<Integer, HashSet<Integer>> network;     //map of users to list of friends

    public FriendNetwork(String filename) {
        network = new HashMap<>();
        inputData(filename);
    }

    // Read in data from given file into the HashMap
    private void inputData(String fileName) {
        In in = new In(fileName);
        in.readLine();                  // discard title line
        while(!in.isEmpty()) {
            int userId = in.readInt();
            int friend = in.readInt();
            HashSet<Integer> friendList;
            if (network.containsKey(userId)) {
                friendList = network.get(userId);
            } else {
                friendList = new HashSet<>();
            }
            friendList.add(friend);
            network.put(userId, friendList);
        }
    }

    // Returns the list of friends of a given user
    public HashSet<Integer> getFriends(int userID) {
        return network.getOrDefault(userID, new HashSet<>());
    }

    // Returns a list of the common friends of two given users
    public HashSet<Integer> getCommonFriends(int user1, int user2) {
        HashSet<Integer> friends = network.getOrDefault(user1, new HashSet<>());
        HashSet<Integer> otherFriends = network.getOrDefault(user2, new HashSet<>());
        friends.retainAll(otherFriends);
        return friends;
    }

    // Print the given list of friends
    public String printFriends(HashSet<Integer> friends, String heading) {
        StringBuilder s = new StringBuilder(heading + "\n");
        for (Integer f : friends) {
            s.append(f + " ");
        }
        if (friends.size() == 0) {           //list is empty
            s.append("none");
        }
        return s.append("\n").toString();
    }

}
