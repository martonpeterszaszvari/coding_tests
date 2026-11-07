import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static final int CASES = 5;
    public static void main(String[] args) throws Exception {
        int passed = 0;
        for (int i = 1; i <= CASES; i++) {
            String filename = String.format("case%02d", i);
            boolean ok;
            try {
                List<String> data = lines("inputs/" + filename + ".txt");
                int songCount = Integer.parseInt(data.get(0));
                List<List<String>> songs = new ArrayList<>();
                for (int j = 0; j < songCount; j++) songs.add(pair(data.get(j + 1)));
                List<String> desired = lines("expected/" + filename + ".txt");
                int expectedCount = Integer.parseInt(desired.get(0));
                List<List<String>> expected = new ArrayList<>();
                for (int j = 0; j < expectedCount; j++) expected.add(pair(desired.get(j + 1)));
                List<List<String>> actual = Result.getShuffledPlaylist(songs);
                System.out.println(actual);
                System.out.println(expected);
                ok = expected.equals(actual);
            } catch (Exception e) {
                System.out.println(e);
                ok = false;
            }

            System.out.println(filename + ": " + (ok ? "PASS" : "FAIL"));
            if (ok) passed++;
        }
        System.out.println("Passed " + passed + "/" + CASES + " tests.");
    }
    private static List<String> pair(String line) {
        String[] parts = line.strip().split("\\s+");
        if (parts.length != 2) throw new IllegalArgumentException("Expected title and artist");
        return Arrays.asList(parts[0], parts[1]);
    }
    private static List<String> lines(String path) throws Exception {
        try (InputStream is = Main.class.getClassLoader().getResourceAsStream(path)) {
            if (is == null) throw new IllegalStateException("Missing file " + path);
            String s = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            return s.lines().filter(line -> !line.isBlank()).toList();
        }
    }
}
