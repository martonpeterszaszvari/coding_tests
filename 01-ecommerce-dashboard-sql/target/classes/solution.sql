-- Write your SQL query below. Do not change the table definitions or expected files.
--In Stock|2|4
--Out of Stock|2|6

--products(id, name, price, in_stock) and wishlists(product_id, customer_email)
--whislists count by id -> id, count
--left join products id w id -> we have id,totalwhislist,in_stock-> group by in_stock

SELECT
    case when result.in_stock = 1 then 'In Stock' else 'Out of Stock' end as stock_status,
       Count(*) as total_products, SUM(result.wishlist_entries) as total_wishlist_entries
from products
Left join (Select products.in_stock,products.id, Count(wishlists.product_id) as wishlist_entries
FROM products
    Left Join wishlists on products.id = wishlists.product_id
GROUP BY products.id) result on products.id = result.id where result.wishlist_entries > 0
GROUP BY result.in_stock;
