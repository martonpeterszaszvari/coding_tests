CREATE TABLE products (
    id INT PRIMARY KEY,
    name VARCHAR(255),
    price DECIMAL(6,2),
    in_stock SMALLINT
);
CREATE TABLE wishlists (
    product_id INT,
    customer_email VARCHAR(255),
    FOREIGN KEY (product_id) REFERENCES products(id)
);
