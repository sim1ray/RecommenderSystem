/*
 * Simone Ray. Assignment 4. 6/14/19
 * The Recommender class provides functionality to print friends of a given user,
 * the common friends, common artists listened to by a pair of given users, as well as the top 10
 * artists and top 10 artists recommended for a given user.
 */

import edu.princeton.cs.algs4.MaxPQ;

import java.util.ArrayList;
import java.util.HashSet;

public class Recommender {
    private FriendNetwork friendNetwork;
    private FanNetwork fanNetwork;

    public Recommender(String userFriendFilename, String artistInfoFilename, String userArtistFilename) {
        friendNetwork = new FriendNetwork(userFriendFilename);
        fanNetwork = new FanNetwork(artistInfoFilename, userArtistFilename);
    }

    // Prints the list of friends for the given user
    public void listFriends(int user) {
        HashSet<Integer> list = friendNetwork.getFriends(user);
        String heading = "Friends of user " + user + " are: ";
        System.out.println(friendNetwork.printFriends(list, heading));
    }

    // Prints the common friends of the given users
    public void listCommonFriends(int user1, int user2) {
        HashSet<Integer> list = friendNetwork.getCommonFriends(user1, user2);
        String heading = "Common friends of user " + user1 + " and user " + user2 + " are:";
        System.out.println(friendNetwork.printFriends(list, heading));
    }

    // Prints the list of artists listened to by both of the given users
    public void listCommonArtists(int user1, int user2) {
        HashSet<Integer> list = fanNetwork.getCommonArtists(user1, user2);
        String heading = "Common artists listened to by user " + user1 + " and user " + user2 + " are:";
        System.out.println(fanNetwork.printArtists(list, heading));
    }

    // Prints the top 10 artists listened to by all the users
    public void listTop10() {
        ArrayList<Artist> list = fanNetwork.getTop10Artists();
        fanNetwork.printTop10(list);
    }

    // Recommends 10 artists listened to by given user and friends
    public void recommend10(int user) {
        ArrayList<Artist> list = fanNetwork.getTop10RecommendedArtists(user, this.friendNetwork);
        fanNetwork.printRecommendedTop10(user, list);
    }

}
