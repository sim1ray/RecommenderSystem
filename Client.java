/*
 * Simone Ray. Assignment 4. 6/14/19
 * The Client class tests the functionality of the Recommender System for last.fm.
 */

public class Client {
    public static void main(String[] args) {
        try {
            //Recommender rec = new Recommender("user_friends.dat", "artists.dat", "user_artists.dat");
            Recommender rec = new Recommender("user_friends_test.dat", "artists_test.dat", "user_artists_test.dat");
            rec.listFriends(3);
            rec.listCommonFriends(46, 4);
            rec.listCommonArtists(48, 46);
            rec.listTop10();
            rec.recommend10(2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
