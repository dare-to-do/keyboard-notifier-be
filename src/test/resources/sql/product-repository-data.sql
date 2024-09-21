INSERT INTO product (id, created_at, created_by, updated_at, updated_by, name, price, image_url, type, description, start_date, end_date, status)
VALUES (1, NOW(), 'admin', NOW(), 'admin', 'Product A', 1000, 'http://example.com/imageA.jpg', 'KEYBOARD', 'Description for Product A', '2024-08-01', '2024-08-31', 'IN_PROGRESS');
INSERT INTO product (id, created_at, created_by, updated_at, updated_by, name, price, image_url, type, description, start_date, end_date, status)
VALUES (2, NOW() + 1, 'admin', NOW(), 'admin', 'Product B', 2000, 'http://example.com/imageB.jpg', 'SWITCH', 'Description for Product B', '2024-08-01', '2024-08-31', 'NOT_YET');
INSERT INTO product (id, created_at, created_by, updated_at, updated_by, name, price, image_url, type, description, start_date, end_date, status)
VALUES (3, NOW() + 2, 'admin', NOW(), 'admin', 'Product C', 1500, 'http://example.com/imageC.jpg', 'KEY_CAP', 'Description for Product C', '2024-08-01', '2024-08-31', 'DONE');
INSERT INTO product (id, created_at, created_by, updated_at, updated_by, name, price, image_url, type, description, start_date, end_date, status)
VALUES (4, NOW() + 3, 'admin', NOW(), 'admin', 'Product D', 2500, 'http://example.com/imageD.jpg', 'STABILIZER', 'Description for Product D', '2024-08-01', '2024-08-31', 'IN_PROGRESS');
INSERT INTO product (id, created_at, created_by, updated_at, updated_by, name, price, image_url, type, description, start_date, end_date, status)
VALUES (5, NOW() + 4, 'admin', NOW(), 'admin', 'Product E', 3000, 'http://example.com/imageE.jpg', 'KIT', 'Description for Product E', '2024-08-01', '2024-08-31', 'NOT_YET');
