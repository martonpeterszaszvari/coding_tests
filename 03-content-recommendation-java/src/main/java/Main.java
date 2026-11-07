import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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
                int pos = 0;
                int watchedCount = Integer.parseInt(data.get(pos++));
                List<String> watched = new ArrayList<>();
                for (int j = 0; j < watchedCount; j++) watched.add(data.get(pos++));
                int scoreCount = Integer.parseInt(data.get(pos++));
                List<Integer> scores = new ArrayList<>();
                for (int j = 0; j < scoreCount; j++) scores.add(Integer.parseInt(data.get(pos++)));
                int titleCount = Integer.parseInt(data.get(pos++));
                List<String> titles = new ArrayList<>();
                for (int j = 0; j < titleCount; j++) titles.add(data.get(pos++));
                int categoryCount = Integer.parseInt(data.get(pos++));
                List<String> categories = new ArrayList<>();
                for (int j = 0; j < categoryCount; j++) categories.add(data.get(pos++));
                List<String> expected = lines("expected/" + filename + ".txt");
                List<String> actual = Result.getRecommendations(watched, scores, titles, categories);
//                System.out.println(expected);
//                System.out.println(actual);
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
    private static List<String> lines(String path) throws Exception {
        try (InputStream is = Main.class.getClassLoader().getResourceAsStream(path)) {
            if (is == null) throw new IllegalStateException("Missing file " + path);
            String s = new String(is.readAllBytes(), StandardCharsets.UTF_8);
            return s.lines().filter(line -> !line.isBlank()).toList();
        }
    }
}
