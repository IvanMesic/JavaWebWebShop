CREATE TABLE IF NOT EXISTS "user" (
                                    id INT PRIMARY KEY AUTO_INCREMENT,
                                    name VARCHAR(255) NOT NULL,
                                    password VARCHAR(255) NOT NULL,
                                    privilege VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS category (
                                        id INT PRIMARY KEY  AUTO_INCREMENT,
                                        name VARCHAR(255) NOT NULL,
                                        description TEXT
);

CREATE TABLE IF NOT EXISTS item (
                                    id INT  PRIMARY KEY AUTO_INCREMENT,
                                    name VARCHAR(255) NOT NULL,
                                    price DECIMAL(10,2) NOT NULL,
                                    category_id INT,
                                    description TEXT,
                                    url VARCHAR(255),
                                    FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE IF NOT EXISTS "transaction" (
                                           id INT  PRIMARY KEY AUTO_INCREMENT,
                                           user_id INT,
                                           purchase_date TIMESTAMP,
                                           FOREIGN KEY (user_id) REFERENCES "user"(id),
                                           ttype VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS transaction_item (
                                                id INT  PRIMARY KEY AUTO_INCREMENT,
                                                transaction_id INT,
                                                item_id INT,
                                                quantity INT,
                                                FOREIGN KEY (transaction_id) REFERENCES "transaction"(id),
                                                FOREIGN KEY (item_id) REFERENCES item(id)
);

CREATE TABLE IF NOT EXISTS user_log (
                                        id INT PRIMARY KEY AUTO_INCREMENT,
                                        name VARCHAR(255) NOT NULL,
                                        login_date TIMESTAMP,
                                        ip_address VARCHAR(50),
                                        event_type VARCHAR(50)
);
