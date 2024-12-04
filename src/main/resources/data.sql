INSERT INTO TBL_SQUARES (NAME) VALUES ('Pratos');
INSERT INTO TBL_SQUARES (NAME) VALUES ('Sobremesas');
INSERT INTO TBL_SQUARES (NAME) VALUES ('Bebidas');

INSERT INTO TBL_ITEM (NAME, SQUARE_ID) VALUES ('Coca-Cola 500ml', 3);
INSERT INTO TBL_ITEM (NAME, SQUARE_ID) VALUES ('Pepsi 500ml', 3);
INSERT INTO TBL_ITEM (NAME, SQUARE_ID) VALUES ('Sprite 500ml', 3);
INSERT INTO TBL_ITEM (NAME, SQUARE_ID) VALUES ('Fanta 350ml', 3);
INSERT INTO TBL_ITEM (NAME, SQUARE_ID) VALUES ('Guarana 500ml', 3);


INSERT INTO TBL_ITEM (NAME, SQUARE_ID) VALUES ('Macarronada', 1);
INSERT INTO TBL_ITEM (NAME, SQUARE_ID) VALUES ('Pizza', 1);
INSERT INTO TBL_ITEM (NAME, SQUARE_ID) VALUES ('Hamburger', 1);
INSERT INTO TBL_ITEM (NAME, SQUARE_ID) VALUES ('Arroz com feijão', 1);
INSERT INTO TBL_ITEM (NAME, SQUARE_ID) VALUES ('Esfiha', 1);
INSERT INTO TBL_ITEM (NAME, SQUARE_ID) VALUES ('Lasanha', 1);

INSERT INTO TBL_ITEM (NAME, SQUARE_ID) VALUES ('Brownie', 2);
INSERT INTO TBL_ITEM (NAME, SQUARE_ID) VALUES ('Bolo de cereja (1 fatia)', 2);
INSERT INTO TBL_ITEM (NAME, SQUARE_ID) VALUES ('Torta de chocolate (1 fatia)', 2);
INSERT INTO TBL_ITEM (NAME, SQUARE_ID) VALUES ('Cookie', 2);
INSERT INTO TBL_ITEM (NAME, SQUARE_ID) VALUES ('Pavlova', 2);
INSERT INTO TBL_ITEM (NAME, SQUARE_ID) VALUES ('Mousse de chocolate', 2);


INSERT INTO TBL_USERS (NAME, LOGIN, PASSWORD, PROFILE) VALUES ('Jorge', 'jorge@email.com', '1234', 2);
INSERT INTO TBL_USERS (NAME, LOGIN, PASSWORD, PROFILE) VALUES ('Joao', 'joao@email.com', '1234', 1);

INSERT INTO TBL_USERS (NAME, LOGIN, PASSWORD, PROFILE) VALUES ('Jorge', 'jorge@email.com', '1234', 2);
INSERT INTO TBL_USERS (NAME, LOGIN, PASSWORD, PROFILE) VALUES ('Joao', 'joao@email.com', '1234', 1);

INSERT INTO TBL_CATEGORY (NAME, AVAILABILITY) VALUES ('Bebidas', true);
INSERT INTO TBL_CATEGORY (NAME, AVAILABILITY) VALUES ('Prato Principal', false);
INSERT INTO TBL_CATEGORY (NAME, AVAILABILITY) VALUES ('Sobremesa', true);

INSERT INTO TBL_PRODUCT(name, description, price, discount, availability, category)
VALUES ('Pizza Margherita', 'Pizza com molho de tomate, mussarela e manjericão fresco', 39.90, 0.0, TRUE, 'Pizzas');
INSERT INTO TBL_PRODUCT (name, description, price, discount, availability, category)
VALUES ('Hambúrguer Artesanal', 'Pão brioche, carne Angus, queijo cheddar e molho especial', 29.90, 5.0, TRUE, 'Lanches');
INSERT INTO TBL_PRODUCT (name, description, price, discount, availability, category)
VALUES ('Salada Caesar', 'Alface americana, croutons, frango grelhado e molho caesar', 24.90, 10.0, TRUE, 'Saladas');