package practice;

import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RunTests {
    private static final int CASES = 4;

    public static void main(String[] args) throws Exception {
        Class.forName("org.h2.Driver");
        String query = resource("solution.sql").trim();
        if (query.isBlank() || query.replaceAll("(?m)--[^\r\n]*", "").isBlank()) {
            System.out.println("No solution submitted yet. Edit src/main/resources/solution.sql");
            return;
        }
        int passed = 0;
        for (int n = 1; n <= CASES; n++) {
            String id = String.format("case%02d", n);
            boolean ok;
            try (Connection db = DriverManager.getConnection(
                    "jdbc:h2:mem:" + id + ";MODE=MySQL;DATABASE_TO_UPPER=false;DB_CLOSE_DELAY=-1")) {
                executeScript(db, resource("schema.sql"));
                executeScript(db, resource("data/" + id + ".sql"));
                ok = check(db, query, resource("expected/" + id + ".tsv"));
            } catch (Exception e) {
                ok = false;
            }
            System.out.println(id + ": " + (ok ? "PASS" : "FAIL"));
            if (ok) passed++;
        }
        System.out.println("Passed " + passed + "/" + CASES + " tests.");
    }

    private static boolean check(Connection db, String query, String expectedText) throws Exception {
        List<String> expected = new ArrayList<>();
        for (String row : expectedText.split("\\R")) {
            if (!row.isBlank() && !row.startsWith("#")) expected.add(row.trim());
        }
        List<String> actual = new ArrayList<>();
        try (Statement st = db.createStatement(); ResultSet rs = st.executeQuery(query)) {
            ResultSetMetaData meta = rs.getMetaData();
            String[] required = requiredColumns();
            if (meta.getColumnCount() != required.length) return false;
            for (int c = 1; c <= required.length; c++) {
                if (!meta.getColumnLabel(c).equalsIgnoreCase(required[c - 1])) return false;
            }
            while (rs.next()) {
                StringBuilder line = new StringBuilder();
                for (int c = 1; c <= required.length; c++) {
                    if (c > 1) line.append('|');
                    Object v = rs.getObject(c);
                    if (v == null) return false;
                    // Canonicalize numerical values while keeping all text exact.
                    if (v instanceof Number) {
                        line.append(new BigDecimal(v.toString()).stripTrailingZeros().toPlainString());
                    } else line.append(v.toString());
                }
                actual.add(line.toString());
            }
        }
        Collections.sort(actual);
        Collections.sort(expected);
        return actual.equals(expected);
    }

    private static String[] requiredColumns() {
        return new String[]{"order_id", "customer_id", "delay_days", "average_monthly_delay"};
    }

    private static void executeScript(Connection db, String script) throws Exception {
        try (Statement st = db.createStatement()) {
            for (String chunk : script.split(";")) {
                String s = chunk.trim();
                if (!s.isBlank()) st.execute(s);
            }
        }
    }

    private static String resource(String path) throws Exception {
        try (InputStream is = RunTests.class.getClassLoader().getResourceAsStream(path)) {
            if (is == null) throw new IllegalStateException("Missing resource " + path);
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
