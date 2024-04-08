-- Users
INSERT INTO item (name, description, price, url) VALUES
                                         ('Laptop', 'High-performance laptop with SSD drive', 30, 'https://images.twinkl.co.uk/tw1n/image/private/t_630/u/ux/pc_ver_1.png'),
                                         ('Smartphone', 'Latest smartphone model with dual cameras', 20, 'https://upload.wikimedia.org/wikipedia/commons/9/9e/IBM_Simon_Personal_Communicator.png'),
                                         ('Head phones', 'Wireless noise-canceling headphones', 40, 'https://upload.wikimedia.org/wikipedia/commons/0/00/S%C5%82uchawki_referencyjne_K-701_firmy_AKG.jpg'),
                                         ('Keyboard', 'Mechanical gaming keyboard with RGB lighting', 5, 'https://upload.wikimedia.org/wikipedia/commons/thumb/7/7f/Atari_400_keyboard.jpg/1280px-Atari_400_keyboard.jpg');

-- Items
INSERT INTO "user" (name, password, privilege) VALUES
                                                   ('zujo', '$2a$12$.Y7HqWbx3v5WcezAhSEkC.MAJjPhQ/bR3qYNov3aCqO8OfgLXD5F2', 'ADMIN'),
                                                   ('meske', '$2a$12$.Y7HqWbx3v5WcezAhSEkC.MAJjPhQ/bR3qYNov3aCqO8OfgLXD5F2', 'USER'),
                                                   ('Bob Johnson', 'bobpass', 'regular');

-- Categories
INSERT INTO category (name, description) VALUES
                                             ('Undefined', 'Undefined Items'),
                                             ('Electronics', 'Electronic devices and gadgets'),
                                             ('Computers', 'Desktops, laptops, and accessories'),
                                             ('Mobile', 'Mobile phones and accessories');
/*
-- Item-Category relationships
INSERT INTO itemcategory (ItemId, CategoryId) VALUES
                                                       (1, 1), -- Laptop belongs to Electronics category
                                                       (2, 1), -- Smartphone belongs to Electronics category
                                                       (3, 2); -- Keyboard belongs to Computers category
*/
-- Transactions
INSERT INTO "transaction" (user_id, purchase_date, ttype) VALUES
                                                              (1, '2024-02-22 10:30:00', 'cash'), -- John Doe purchases
                                                              (2, '2024-02-22 11:45:00', 'cash'), -- Alice Smith purchases
                                                              (3, '2024-02-22 13:20:00', 'cash'); -- Bob Johnson purchases

-- Transaction-Item relationships
INSERT INTO transaction_item (transaction_id, item_id, quantity) VALUES
                                                                  (1, 1, 1), -- John Doe purchases 1 Laptop
                                                                  (1, 2, 1);-- John Doe purchases 1 Smartphone

