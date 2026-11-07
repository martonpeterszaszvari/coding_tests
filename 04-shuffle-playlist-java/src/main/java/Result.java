import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


public class Result {

    static class Artist {
        String name;
        int remainingSongs;
        int originalIndex;
        Queue<String> songs = new ArrayDeque<>();

        Artist(String name, int originalIndex) {
            this.name = name;
            this.originalIndex = originalIndex;
        }
    }

    //Solution
    public static List<List<String>> getShuffledPlaylist(
            List<List<String>> playlist) {

        List<List<String>> shuffledPlaylist = new ArrayList<>();

        // 1. Group songs by artist
        Map<String, Artist> artists = new HashMap<>();

        for (int i = 0; i < playlist.size(); i++) {
            String song = playlist.get(i).get(0);
            String artistName = playlist.get(i).get(1);

            Artist artist = artists.get(artistName);

            if (artist == null) {
                artist = new Artist(artistName, i);
                artists.put(artistName, artist);
            }

            artist.songs.add(song);
            artist.remainingSongs++;
        }

        // 2. Create priority queue
        PriorityQueue<Artist> pq = new PriorityQueue<>(
                Comparator.comparingInt((Artist a) -> a.remainingSongs)
                        .reversed()
                        .thenComparingInt(a -> a.originalIndex)
        );

        pq.addAll(artists.values());

        Artist previousArtist = null;

        // 3. Build shuffled playlist
        while (!pq.isEmpty()) {

            Artist current = pq.poll();

            // Cannot play the same artist consecutively
            // when another artist is available
            if (current == previousArtist && !pq.isEmpty()) {

                Artist alternative = pq.poll();

                pq.offer(current);
                current = alternative;
            }

            // 4. Play the next song from this artist
            String song = current.songs.poll();

            shuffledPlaylist.add(
                    List.of(song, current.name)
            );

            current.remainingSongs--;

            // 5. Return artist to queue if songs remain
            if (current.remainingSongs > 0) {
                pq.offer(current);
            }

            previousArtist = current;
        }

        return shuffledPlaylist;
    }
}
