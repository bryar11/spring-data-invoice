/* Populate users */
INSERT INTO users (id, username, name, password, enabled) VALUES (1, 'admin', 'Bryan Arce', '$2a$10$OcvnN8OFOCv9eohbTDnrJe8USxD.LdXGGLuEMCZXlmxXoyGUiRntS', 1);
INSERT INTO users (id, username, name, password, enabled) VALUES (2, 'blancaarce', 'Blanca Arce', '$2a$10$eu.tegaj23s09qtcxAVYnOfi3kb0S.0t5usBoL.AOkQIAM8PHMBna', 1);

/* Populate authorities */
INSERT INTO authorities (user_id, authority) VALUES (1, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (1, 'ROLE_ADMIN');
INSERT INTO authorities (user_id, authority) VALUES (2, 'ROLE_USER');

/* Populate clients */
INSERT INTO clients (name, phone, email, enabled, created_by, updated_by, created_at, updated_at) VALUES('John Doe', null, 'john.doe@gmail.com', 1, 1, null, NOW(), null);
INSERT INTO clients (name, phone, email, enabled, created_by, updated_by, created_at, updated_at) VALUES('Linus Torvalds', null, 'linus.torvalds@gmail.com', 1, 1, null, NOW(), null);
INSERT INTO clients (name, phone, email, enabled, created_by, updated_by, created_at, updated_at) VALUES('Jane Doe', '88664477', null, 1, 1, null, NOW(), null);
INSERT INTO clients (name, phone, email, enabled, created_by, updated_by, created_at, updated_at) VALUES('Rasmus Lerdorf', null, null, 1, 1, null, NOW(), null);
INSERT INTO clients (name, phone, email, enabled, created_by, updated_by, created_at, updated_at) VALUES('Erich Gamma', '88997766', null, 1, 1, null, NOW(), null);
INSERT INTO clients (name, phone, email, enabled, created_by, updated_by, created_at, updated_at) VALUES('Richard Helm', '88774455', null, 1, 1, null, NOW(), null);
INSERT INTO clients (name, phone, email, enabled, created_by, updated_by, created_at, updated_at) VALUES('Ralph Johnson', '88445566', null, 1, 1, null, NOW(), null);
INSERT INTO clients (name, phone, email, enabled, created_by, updated_by, created_at, updated_at) VALUES('John Vlissides', '88332211', null, 1, 1, null, NOW(), null);
INSERT INTO clients (name, phone, email, enabled, created_by, updated_by, created_at, updated_at) VALUES('James Gosling', null, null, 1, 1, null, NOW(), null);
INSERT INTO clients (name, phone, email, enabled, created_by, updated_by, created_at, updated_at) VALUES('Bruce Lee', '88552266', 'bruce.lee@hotmail.com', 1, 1, null, NOW(), null);
INSERT INTO clients (name, phone, email, enabled, created_by, updated_by, created_at, updated_at) VALUES('Johnny Doe', null, null, 1, 1, null, NOW(), null);
INSERT INTO clients (name, phone, email, enabled, created_by, updated_by, created_at, updated_at) VALUES('John Roe', null, null, 1, 1, null, NOW(), null);
INSERT INTO clients (name, phone, email, enabled, created_by, updated_by, created_at, updated_at) VALUES('Jane Roe', null, null, 1, 1, null, NOW(), null);
INSERT INTO clients (name, phone, email, enabled, created_by, updated_by, created_at, updated_at) VALUES('Richard Doe', null, null, 1, 1, null, NOW(), null);
INSERT INTO clients (name, phone, email, enabled, created_by, updated_by, created_at, updated_at) VALUES('Janie Doe', null, null, 1, 1, null, NOW(), null);
INSERT INTO clients (name, phone, email, enabled, created_by, updated_by, created_at, updated_at) VALUES('Phillip Webb', null, null, 1, 1, null, NOW(), null);
INSERT INTO clients (name, phone, email, enabled, created_by, updated_by, created_at, updated_at) VALUES('Stephane Nicoll', null, null, 1, 1, null, NOW(), null);
INSERT INTO clients (name, phone, email, enabled, created_by, updated_by, created_at, updated_at) VALUES('Sam Brannen', null, null, 1, 1, null, NOW(), null);
INSERT INTO clients (name, phone, email, enabled, created_by, updated_by, created_at, updated_at) VALUES('Juergen Hoeller', null, null, 1, 1, null, NOW(), null);
INSERT INTO clients (name, phone, email, enabled, created_by, updated_by, created_at, updated_at) VALUES('Janie Roe', null, null, 1, 1, null, NOW(), null);
INSERT INTO clients (name, phone, email, enabled, created_by, updated_by, created_at, updated_at) VALUES('John Smith', null, null, 1, 1, null, NOW(), null);
INSERT INTO clients (name, phone, email, enabled, created_by, updated_by, created_at, updated_at) VALUES('Joe Bloggs', null, null, 1, 1, null, NOW(), null);
INSERT INTO clients (name, phone, email, enabled, created_by, updated_by, created_at, updated_at) VALUES('John Stiles', null, null, 1, 1, null, NOW(), null);
INSERT INTO clients (name, phone, email, enabled, created_by, updated_by, created_at, updated_at) VALUES('Richard Roe', null, null, 1, 1, null, NOW(), null);

/* Populate products */
INSERT INTO products (name, price, enabled, created_by, updated_by, created_at, updated_at) VALUES('Pantalla LCD Panasonic', 300000, 1, 1, null, NOW(), null);
INSERT INTO products (name, price, enabled, created_by, updated_by, created_at, updated_at) VALUES('Camara digital Sony', 125000, 1, 1, null, NOW(), null);
INSERT INTO products (name, price, enabled, created_by, updated_by, created_at, updated_at) VALUES('iPhone', 630000, 1, 1, null, NOW(), null);
INSERT INTO products (name, price, enabled, created_by, updated_by, created_at, updated_at) VALUES('Dell G5 15', 900000, 1, 1, null, NOW(), null);
INSERT INTO products (name, price, enabled, created_by, updated_by, created_at, updated_at) VALUES('Hewlett Packard Multifuncional F2280', 45000, 1, 1, null, NOW(), null);
INSERT INTO products (name, price, enabled, created_by, updated_by, created_at, updated_at) VALUES('Bianchi Bicicleta Aro 26', 45000, 1, 1, null, NOW(), null);
INSERT INTO products (name, price, enabled, created_by, updated_by, created_at, updated_at) VALUES('Comoda 5 Cajones', 65000, 1, 1, null, NOW(), null);

/* Populate invoices */
INSERT INTO invoices (client_id, enabled, created_by, updated_by, created_at, updated_at) VALUES(10, 1, 1, null, NOW(), null);
INSERT INTO invoices (client_id, enabled, created_by, updated_by, created_at, updated_at) VALUES(10, 1, 1, null, NOW(), null);
INSERT INTO invoices (client_id, enabled, created_by, updated_by, created_at, updated_at) VALUES(10, 1, 1, null, NOW(), null);

/* Populate invoice_items */
INSERT INTO invoice_items (invoice_id, product_id, quantity, price) VALUES(1, 1, 1, 300000);
INSERT INTO invoice_items (invoice_id, product_id, quantity, price) VALUES(1, 4, 2, 900000);
INSERT INTO invoice_items (invoice_id, product_id, quantity, price) VALUES(1, 5, 1, 45000);
INSERT INTO invoice_items (invoice_id, product_id, quantity, price) VALUES(1, 7, 1, 65000);
INSERT INTO invoice_items (invoice_id, product_id, quantity, price) VALUES(2, 6, 6, 45000);
INSERT INTO invoice_items (invoice_id, product_id, quantity, price) VALUES(3, 7, 2, 65000);

/* Populate invoice_payments */
INSERT INTO invoice_payments (invoice_id, amount, enabled, created_by, updated_by, created_at, updated_at) VALUES(1, 1800000, 1, 1, null, NOW(), null);
INSERT INTO invoice_payments (invoice_id, amount, enabled, created_by, updated_by, created_at, updated_at) VALUES(3, 130000, 1, 1, null, NOW(), null);