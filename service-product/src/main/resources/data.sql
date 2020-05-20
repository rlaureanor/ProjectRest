INSERT INTO tbl_categories (id, name) VALUES (1, 'shoes');
INSERT INTO tbl_categories (id, name) VALUES (2, 'books');
INSERT INTO tbl_categories (id, name) VALUES (3, 'electronics');

INSERT INTO tbl_products (id, name, description,stock, price, status, create_at, category_id)
VALUES (1,'adidas Cloudfoam Ultimate', 'walk in the air', 10, 250.55,'CREATED','2020-04-09',1);

INSERT INTO tbl_products (id, name, description,stock, price, status, create_at, category_id)
VALUES (2,'under armour Men', 'under armour Men', 15, 25.99,'CREATED','2020-04-09',1);

INSERT INTO tbl_products (id, name, description,stock, price, status, create_at, category_id)
VALUES (3,'Spring boot in action', 'Spring boot in action', 20, 99.55,'CREATED','2020-04-09',2);
