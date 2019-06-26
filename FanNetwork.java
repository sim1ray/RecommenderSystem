/*
 * Simone Ray. Assignment 4. 6/14/19
 * The FanNetwork class is constructed by reading in two data files: one of artist ids linked to their names,
 * and the other a data file of users, the artists they listen to, and frequency of listens. The information is then
 * used to implement methods that print the top 10 most popular artists, the top 10 recommended artists for a
 * given user, and common artists listened to by a given pair of users.
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MaxPQ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FanNetwork {

    private HashMap<Integer, String> artists;               //map of artist ids to names
    private HashMap<Integer, HashSet<Integer>> fanNetwork;  //map of users to set of artists they listen to
    private HashMap<Integer, Long> artistWeights;           //map of artists to their weights(frequency of listens)
    private MaxPQ<Artist> rankedArtists;                    //max heap of popular artists

    public FanNetwork(String artistInfoFilename, String userArtistInfoFilename) {
        this.artists = new HashMap<>();
        this.fanNetwork = new HashMap<>();
        this.artistWeights = new HashMap<>();
        inputArtists(artistInfoFilename);
        inputFanInfo(userArtistInfoFilename);
    }

    // Read artist data into a HashMap of artist ids mapped to artist names
    private void inputArtists(String filename) {
        In in = new In(filename);
        in.readLine();
        String lines[] = in.readAllLines();
        for (int j = 0; j < lines.length; j++) {
            // Extract artist id and name from file
            Pattern p = Pattern.compile("(\\d+)([ \t]+)(.+?)http:");
            Matcher m = p.matcher(lines[j]);
            String artistName = "";
            int artistId = 0;
            if (m.find()) {
                artistId = Integer.parseInt(m.group(1));
                artistName = m.group(3);
            }
            artists.put(artistId, artistName);
        }
    }

    // Read data from file to input users mapped to a set of artists they listen to
    private void inputFanInfo(String filename) {
        In in = new In(filename);
        in.readLine();                  // discard title line
        while(!in.isEmpty()) {
            int userId = in.readInt();
            int artistId = in.readInt();
            long weight = in.readLong();

            // For each user, add artists they listen to
            HashSet<Integer> friendList;
            if (fanNetwork.containsKey(userId)) {
                friendList = fanNetwork.get(userId);
            } else {
                friendList = new HashSet<>();
            }
            friendList.add(artistId);
            fanNetwork.put(userId, friendList);

            //Update weight for each artists
            if (artistWeights.containsKey(artistId)) {
                weight += artistWeights.get(artistId);
            }
            artistWeights.put(artistId, weight);
        }
    }

    // Returns a max heap of the top 10 most popular artists across all users
    public ArrayList<Artist> getTop10Artists() {
        //Initialize and update max heap of popular artists
        rankedArtists = new MaxPQ<>(10);
        for (Integer artistId : artistWeights.keySet()) {
            Long value = artistWeights.get(artistId);
            Artist newArtist = new Artist(artistId, value);
            rankedArtists.insert(newArtist);
        }

        ArrayList<Artist> artistList = new ArrayList<>();
        int totalLength = rankedArtists.size();
        for (int i = 0; i < 10 && i < totalLength; i++) {
            artistList.add(rankedArtists.delMax());
        }

        return artistList;
    }

    public void printTop10(ArrayList<Artist> rankedArtists) {
        System.out.println("Top 10 artists are: ");
        for (Artist artist : rankedArtists) {
            printArtistNameAndWeight(artist);
        }
        System.out.println();
    }

    // Returns a max heap of the top 10 recommended artists for a given user
    public ArrayList<Artist> getTop10RecommendedArtists(int user, FriendNetwork friendNetwork) {
        // Get the set of artists listened to by given user and his/her friends
        HashSet<Integer> artistsListened = fanNetwork.getOrDefault(user, new HashSet<>());

        rankedArtists = new MaxPQ<>(10);

        Iterable<Integer> friends = friendNetwork.getFriends(user);
        for (Integer friend : friends) {
            artistsListened.addAll(fanNetwork.getOrDefault(friend, new HashSet<>()));
        }

        // From all listened artists, insert 10 most popular into max heap
        for (Integer artistId : artistsListened) {
            if (artistWeights.containsKey(artistId)) {
                Long value = artistWeights.get(artistId);
                Artist newArtist = new Artist(artistId, value);
                rankedArtists.insert(newArtist);
            }
        }

        ArrayList<Artist> artistList = new ArrayList<>();
        if (rankedArtists.size() != 0) {
            int totalLength = rankedArtists.size();
            for (int i = 0; i < 10 && i < totalLength; i++) {
                artistList.add(rankedArtists.delMax());
            }
        }

        return artistList;
    }

    public void printRecommendedTop10(int user, ArrayList<Artist> rankedArtists) {
        // Print top 10 recommended artists from constructed max heap
        if (rankedArtists.size() != 0) {
            System.out.println("Top 10 recommended artists for user " + user + " are: ");
            for (Artist artist : rankedArtists) {
                printArtistNameAndWeight(artist);
            }
        } else {
            System.out.println("There is no user: " + user);
        }
    }

    // Prints name and weight of given artist
    private void printArtistNameAndWeight(Artist artist) {
        String name = artists.get(artist.getId()).trim();
        Long weight = artist.getWeight();
        System.out.printf("%-25s(weight: %d)\n", name, weight);
    }

    // Returns list of common artists listen to by given pair of users
    public HashSet<Integer> getCommonArtists(int user1, int user2) {
        HashSet<Integer> artists = fanNetwork.getOrDefault(user1, new HashSet<>());
        HashSet<Integer> otherArtists = fanNetwork.getOrDefault(user2, new HashSet<>());
        artists.retainAll(otherArtists);
        return artists;
    }

    // Print the given list of artists
    public String printArtists(HashSet<Integer> list, String heading) {
        StringBuilder s = new StringBuilder(heading + "\n");
        for (Integer f : list) {
            s.append(artists.get(f) + "\n");
        }
        if (list.size() == 0) {           //list is empty
            s.append("none\n");
        }
        return s.toString();
    }

}
