-- categories
INSERT INTO categories(name)
VALUES ('Category 1'),
       ('Category 2'),
       ('Category 3'),
       ('Category 4'),
       ('Category 5');

-- products
INSERT INTO products(name, description, price, category_id)
values ('yellow', 'white yellow blue red', 10, 1),
       ('blue', 'white blue red green', 10, 2),
       ('red', 'white blue black orange', 20, 1),
       ('white', 'red green black violet', 10, 3),
       ('black', 'black white', 15, 1),
       ('green', 'red green blue', 15, 1),
       ('orange', 'blue orange white', 10, 2),
       ('purple', 'yellow orange', 20, 1),
       ('rose', 'green red orange', 20, 3),
       ('violet', 'red white', 10, 2);



