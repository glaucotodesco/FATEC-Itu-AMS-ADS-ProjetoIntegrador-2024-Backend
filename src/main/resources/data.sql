INSERT INTO TBL_SQUARES (NAME) VALUES ('Fritura');
INSERT INTO TBL_SQUARES (NAME) VALUES ('Assados');
INSERT INTO TBL_SQUARES (NAME) VALUES ('Bebidas');

INSERT INTO TBL_ITEM (NAME, SQUARE_ID) VALUES ('Pão de Forma', 1);
INSERT INTO TBL_ITEM (NAME, SQUARE_ID) VALUES ('Pão de Queijo', 1);
INSERT INTO TBL_ITEM (NAME, SQUARE_ID) VALUES ('Pão de Ovo', 1);
INSERT INTO TBL_ITEM (NAME, SQUARE_ID) VALUES ('Pão de Queijo e Ovo', 1);
INSERT INTO TBL_ITEM (NAME, SQUARE_ID) VALUES ('Bolo de Chocolate', 1);

INSERT INTO TBL_ITEM (NAME, SQUARE_ID) VALUES ('Coxinha', 2);
INSERT INTO TBL_ITEM (NAME, SQUARE_ID) VALUES ('Picanha', 2);

INSERT INTO TBL_USERS (NAME, LOGIN, PASSWORD, PROFILE) VALUES ('Jorge', 'jorge@email.com', '1234', 2);
INSERT INTO TBL_USERS (NAME, LOGIN, PASSWORD, PROFILE) VALUES ('Joao', 'joao@email.com', '1234', 1);

INSERT INTO TBL_USERS (NAME, LOGIN, PASSWORD, PROFILE) VALUES ('Jorge', 'jorge@email.com', '1234', 2);
INSERT INTO TBL_USERS (NAME, LOGIN, PASSWORD, PROFILE) VALUES ('Joao', 'joao@email.com', '1234', 1);

INSERT INTO TBL_CATEGORY (NAME, AVAILABILITY) VALUES ('Bebidas', true);
INSERT INTO TBL_CATEGORY (NAME, AVAILABILITY) VALUES ('Prato Principal', false);
INSERT INTO TBL_CATEGORY (NAME, AVAILABILITY) VALUES ('Sobremesa', true);

INSERT INTO Product (name, description, price, discount, availability, category)
VALUES ('Pizza Margherita', 'Pizza com molho de tomate, mussarela e manjericão fresco', 39.90, 0.0, TRUE, 'Pizzas');
INSERT INTO Product (name, description, price, discount, availability, category)
VALUES ('Hambúrguer Artesanal', 'Pão brioche, carne Angus, queijo cheddar e molho especial', 29.90, 5.0, TRUE, 'Lanches');
INSERT INTO Product (name, description, price, discount, availability, category)
VALUES ('Salada Caesar', 'Alface americana, croutons, frango grelhado e molho caesar', 24.90, 10.0, TRUE, 'Saladas');