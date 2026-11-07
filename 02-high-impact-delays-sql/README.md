# Test 2 — High-Impact Delays (SQL)

Open this folder as a Maven project in IntelliJ, set JDK 21, and run the `practice.RunTests` main class.
Maven downloads the H2 database dependency on first use (internet needed).

**Your edit location:** `src/main/resources/solution.sql`.

Find orders with delay `actual_delivery_date - expected_delivery_date` strictly greater than
five times the **rounded** monthly average delay. For averaging, assign every order's delay to
**the month and year of the order_date**. The output must have:
`order_id | customer_id | delay_days | average_monthly_delay`.
The monthly average must be rounded to two decimal places; the test compares numeric values.
Order of output rows does not matter.

**Table:** `orders(id, customer_id, order_date, expected_delivery_date, actual_delivery_date)`.
`customer_id` is VARCHAR here because the photographed example uses values such as C005,
even though part of the photographed schema suggested INT.

Input records for four independent tests are in `src/main/resources/data/` and expected outputs
in `src/main/resources/expected/`. **Case 01 actual delivery dates are reconstructed test
fixtures**, not claimed to be the hidden/cropped original dates. They are chosen to match the
photographed example's January and February averages and the expected result.

**Console:** pass/fail only, no solutions/hints.

**SQL compatibility:** H2 with MySQL compatibility mode (not full MySQL). Prefer portable SQL.
