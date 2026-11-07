# Test 3 — Content Recommendation (Java 21)

Open this folder as a Maven project in IntelliJ. Set Project SDK to JDK 21.
Run `src/main/java/Main.java` using the green ▶ button.
There are **no external libraries** for this project.

**Your edit location:** `src/main/java/Result.java` inside `getRecommendations(...)`.
Leave the method signature unchanged. The current stub intentionally returns an empty list.

Return titles in this order:
1. Videos grouped by category.
2. Watched categories, highest relevance score first.
3. Unwatched categories, alphabetical category order.
4. Alphabetical title order within each category.

Five runnable input files are in `src/main/resources/inputs/`, one expected-output
file for each in `src/main/resources/expected/`. Input file format:

```
count of watched categories
one watched category per line
count of relevance scores
one integer score per line
count of available titles
one title per line
count of available categories
one category per line
```

`Main` prints only PASS/FAIL for each case; it does not reveal solutions or hints.
