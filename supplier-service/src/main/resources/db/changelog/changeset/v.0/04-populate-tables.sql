-- category
WITH category_series(category_id) AS (SELECT GENERATE_SERIES(1, 100) AS category_id)
INSERT
INTO categories(name)
SELECT
    CONCAT('Category ', category_id) AS name
FROM category_series;

-- product
WITH product_series(product_id) AS (SELECT GENERATE_SERIES(1, 200) AS product_id)
INSERT
INTO products(name, description, price, category_id)
SELECT
    CONCAT('Product ', product_id) AS name,
    MD5(RANDOM()::TEXT)            AS description,
    (RANDOM() *99 )::FLOAT as price, ROUND((RANDOM() * 99):: INT, 0) + 1 as category_id
FROM product_series;

-- review
WITH review_series(review_id) AS (SELECT GENERATE_SERIES(1, 5000) AS review_id)
INSERT
INTO reviews( message, product_id)
SELECT
    MD5(RANDOM()::TEXT)                  AS message,
    ROUND((RANDOM() * 199):: INT, 0) + 1 as product_id
FROM review_series;