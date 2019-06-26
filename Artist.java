/*
 * Simone Ray. Assignment 4. 6/14/19
 * The Artist class represents an artist by their id and weight
 * (the total number of times users have listened to them).
 */

public class Artist implements Comparable<Artist> {
    private int id;
    private long weight;

    public Artist(int id, long weight) {
        this.id = id;
        this.weight = weight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    @Override
    public int compareTo(Artist other) {
        return Long.compare(weight, other.getWeight());
    }

    @Override
    public String toString() {
        return "Artist {" +
                "id=" + id +
                ", weight=" + weight +
                '}';
    }
}
