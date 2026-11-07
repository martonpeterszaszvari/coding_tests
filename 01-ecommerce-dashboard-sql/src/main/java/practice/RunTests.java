package practice;


import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.*;
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
                System.out.println(e);
                ok = false;
            }
            System.out.println(id + ": " + (ok ? "PASS" : "FAIL"));
            if (ok) passed++;
        }
        System.out.println("Passed " + passed + "/" + CASES + " tests.");
    }

    private static boolean check(
            Connection db,
            String query,
            String expectedText
    ) throws Exception {

        List<String> expected = new ArrayList<>();
        for (String row : expectedText.split("\\R")) {
            if (!row.isBlank() && !row.startsWith("#")) {
                expected.add(row.trim());
            }
        }

        List<String> actual = new ArrayList<>();
        boolean validColumns = true;
        boolean noNulls = true;

        try (Statement st = db.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            ResultSetMetaData meta = rs.getMetaData();
            String[] required = requiredColumns();

            int columnCount = meta.getColumnCount();
            validColumns = columnCount == required.length;

            List<String> columnNames = new ArrayList<>();

            for (int c = 1; c <= columnCount; c++) {
                String name = meta.getColumnLabel(c);
                columnNames.add(name);

                if (c > required.length ||
                        !name.equalsIgnoreCase(required[c - 1])) {
                    validColumns = false;
                }
            }

//            System.out.println("\nACTUAL RESULT:");
//            System.out.println(String.join(" | ", columnNames));
//            System.out.println("--------------------------------");

            while (rs.next()) {
                StringBuilder line = new StringBuilder();

                for (int c = 1; c <= columnCount; c++) {
                    if (c > 1) line.append('|');

                    Object value = rs.getObject(c);

                    if (value == null) {
                        noNulls = false;
                        line.append("NULL");
                    } else if (value instanceof Number) {
                        line.append(
                                new BigDecimal(value.toString())
                                        .stripTrailingZeros()
                                        .toPlainString()
                        );
                    } else {
                        line.append(value.toString());
                    }
                }

                actual.add(line.toString());
                System.out.println(line.toString().replace("|", " | "));
            }

            if (actual.isEmpty()) {
                System.out.println("(no rows returned)");
            }
        }

        Collections.sort(actual);
        Collections.sort(expected);

        return validColumns && noNulls && actual.equals(expected);
    }

    private static String[] requiredColumns() {
        return new String[]{"stock_status", "total_products", "total_wishlist_entries"};
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
