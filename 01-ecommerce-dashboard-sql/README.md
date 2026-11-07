# Test 1 — E-commerce Dashboard (SQL)

**IntelliJ project:** open this folder as a Maven project and select JDK 21.
Run `src/main/java/practice/RunTests.java` using its green ▶ button.
IntelliJ/Maven will download the open-source H2 database dependency the first time (internet needed).

**Your edit location:** `src/main/resources/solution.sql` (only).

Return these columns, in this order:
`stock_status | total_products | total_wishlist_entries`.
Values for `stock_status` must be `In Stock` or `Out of Stock`.
Count distinct products appearing on wishlists and all wishlist entries, grouped by stock status.
Both status categories appear in the provided test data. Result row order does not matter.

**Tables:** `products(id, name, price, in_stock)` and `wishlists(product_id, customer_email)`.
The four inputs are `src/main/resources/data/case01.sql` ... `case04.sql`.
Corresponding expected output files are in `expected/`.

**Console:** `case01: PASS` or `case01: FAIL`, then a total. No solutions/hints are provided.

**SQL compatibility:** The embedded database is H2 in MySQL compatibility mode, not a full MySQL server.
You may use ordinary SQL. Some database-specific built-in functions can differ from MySQL.
