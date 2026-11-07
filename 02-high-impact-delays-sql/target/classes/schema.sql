CREATE TABLE orders (
    id INT PRIMARY KEY,
    customer_id VARCHAR(20),
    order_date DATE,
    expected_delivery_date DATE,
    actual_delivery_date DATE
);
