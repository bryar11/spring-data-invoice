/* Populate clients */
INSERT INTO clients (name, lastname, email, create_at) VALUES('John', 'Doe', 'john.doe@gmail.com', '2022-01-22');
INSERT INTO clients (name, lastname, email, create_at) VALUES('Linus', 'Torvalds', 'linus.torvalds@gmail.com', '2022-01-22');
INSERT INTO clients (name, lastname, email, create_at) VALUES('Jane', 'Doe', 'jane.doe@gmail.com', '2022-01-22');
INSERT INTO clients (name, lastname, email, create_at) VALUES('Rasmus', 'Lerdorf', 'rasmus.lerdorf@gmail.com', '2022-01-22');
INSERT INTO clients (name, lastname, email, create_at) VALUES('Erich', 'Gamma', 'erich.gamma@gmail.com', '2022-01-22');
INSERT INTO clients (name, lastname, email, create_at) VALUES('Richard', 'Helm', 'richard.helm@gmail.com', '2022-01-22');
INSERT INTO clients (name, lastname, email, create_at) VALUES('Ralph', 'Johnson', 'ralph.johnson@gmail.com', '2022-01-22');
INSERT INTO clients (name, lastname, email, create_at) VALUES('John', 'Vlissides', 'john.vlissides@gmail.com', '2022-01-22');
INSERT INTO clients (name, lastname, email, create_at) VALUES('James', 'Gosling', 'james.gosling@gmail.com', '2022-01-22');
INSERT INTO clients (name, lastname, email, create_at) VALUES('Bruce', 'Lee', 'bruce.lee@gmail.com', '2022-01-22');
INSERT INTO clients (name, lastname, email, create_at) VALUES('Johnny', 'Doe', 'johnny.doe@gmail.com', '2022-01-22');
INSERT INTO clients (name, lastname, email, create_at) VALUES('John', 'Roe', 'john.roe@gmail.com', '2022-01-22');
INSERT INTO clients (name, lastname, email, create_at) VALUES('Jane', 'Roe', 'jane.roe@gmail.com', '2022-01-22');
INSERT INTO clients (name, lastname, email, create_at) VALUES('Richard', 'Doe', 'richard.doe@gmail.com', '2022-01-22');
INSERT INTO clients (name, lastname, email, create_at) VALUES('Janie', 'Doe', 'janie.doe@gmail.com', '2022-01-22');
INSERT INTO clients (name, lastname, email, create_at) VALUES('Phillip', 'Webb', 'phillip.webb@gmail.com', '2022-01-22');
INSERT INTO clients (name, lastname, email, create_at) VALUES('Stephane', 'Nicoll', 'stephane.nicoll@gmail.com', '2022-01-22');
INSERT INTO clients (name, lastname, email, create_at) VALUES('Sam', 'Brannen', 'sam.brannen@gmail.com', '2022-01-22');
INSERT INTO clients (name, lastname, email, create_at) VALUES('Juergen', 'Hoeller', 'juergen.Hoeller@gmail.com', '2022-01-22');
INSERT INTO clients (name, lastname, email, create_at) VALUES('Janie', 'Roe', 'janie.roe@gmail.com', '2022-01-22');
INSERT INTO clients (name, lastname, email, create_at) VALUES('John', 'Smith', 'john.smith@gmail.com', '2022-01-22');
INSERT INTO clients (name, lastname, email, create_at) VALUES('Joe', 'Bloggs', 'joe.bloggs@gmail.com', '2022-01-22');
INSERT INTO clients (name, lastname, email, create_at) VALUES('John', 'Stiles', 'john.stiles@gmail.com', '2022-01-22');
INSERT INTO clients (name, lastname, email, create_at) VALUES('Richard', 'Roe', 'stiles.roe@gmail.com', '2022-01-22');

/* Populate products */
INSERT INTO products (name, price, create_at) VALUES('Pantalla LCD Panasonic', 300000, NOW());
INSERT INTO products (name, price, create_at) VALUES('Camara digital Sony', 125000, NOW());
INSERT INTO products (name, price, create_at) VALUES('iPhone', 630000, NOW());
INSERT INTO products (name, price, create_at) VALUES('Dell G5 15', 900000, NOW());
INSERT INTO products (name, price, create_at) VALUES('Hewlett Packard Multifuncional F2280', 45000, NOW());
INSERT INTO products (name, price, create_at) VALUES('Bianchi Bicicleta Aro 26', 45000, NOW());
INSERT INTO products (name, price, create_at) VALUES('Comoda 5 Cajones', 65000, NOW());

/* Populate invoices */
INSERT INTO invoices (description, observation, client_id, create_at) VALUES('Factura equipos de oficina', null, 1, NOW());
INSERT INTO invoices (description, observation, client_id, create_at) VALUES('Factura Bicicleta', 'Alguna nota importante!', 1, NOW());

/* Populate invoices_items */
INSERT INTO invoices_items (quantity, invoice_id, product_id) VALUES(1, 1, 1);
INSERT INTO invoices_items (quantity, invoice_id, product_id) VALUES(2, 1, 4);
INSERT INTO invoices_items (quantity, invoice_id, product_id) VALUES(1, 1, 5);
INSERT INTO invoices_items (quantity, invoice_id, product_id) VALUES(1, 1, 7);
INSERT INTO invoices_items (quantity, invoice_id, product_id) VALUES(3, 2, 6);

/* Populate users */
INSERT INTO users (id, username, password, enabled) VALUES (1, 'barce', '$2a$10$O9wxmH/AeyZZzIS09Wp8YOEMvFnbRVJ8B4dmAMVSGloR62lj.yqXG', 1);
INSERT INTO users (id, username, password, enabled) VALUES (2, 'admin', '$2a$10$DOMDxjYyfZ/e7RcBfUpzqeaCs8pLgcizuiQWXPkU35nOhZlFcE9MS', 1);

/* Populate authorities */
INSERT INTO authorities (user_id, authority) VALUES (1, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (2, 'ROLE_ADMIN');
INSERT INTO authorities (user_id, authority) VALUES (2, 'ROLE_USER');