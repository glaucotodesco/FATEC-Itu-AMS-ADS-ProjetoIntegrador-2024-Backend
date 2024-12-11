INSERT INTO TBL_SQUARES (NAME) VALUES ('Frituras');
INSERT INTO TBL_SQUARES (NAME) VALUES ('Assados');
INSERT INTO TBL_SQUARES (NAME) VALUES ('Bebidas');

INSERT INTO TBL_ITEM (NAME, SQUARE_ID) VALUES ('Arroz', 1);
INSERT INTO TBL_ITEM (NAME, SQUARE_ID) VALUES ('Feijão', 1);
INSERT INTO TBL_ITEM (NAME, SQUARE_ID) VALUES ('Macarrão', 1);
INSERT INTO TBL_ITEM (NAME, SQUARE_ID) VALUES ('Azeitonas', 1);

INSERT INTO TBL_ITEM (NAME, SQUARE_ID) VALUES ('Frango Frito', 2);
INSERT INTO TBL_ITEM (NAME, SQUARE_ID) VALUES ('Filé Mignon', 2);
INSERT INTO TBL_ITEM (NAME, SQUARE_ID) VALUES ('Picanha', 2);
INSERT INTO TBL_ITEM (NAME, SQUARE_ID) VALUES ('Peixe', 2);

INSERT INTO TBL_ITEM (NAME, SQUARE_ID) VALUES ('Cerveja', 3);
INSERT INTO TBL_ITEM (NAME, SQUARE_ID) VALUES ('Refrigerante', 3);
INSERT INTO TBL_ITEM (NAME, SQUARE_ID) VALUES ('Suco', 3);
INSERT INTO TBL_ITEM (NAME, SQUARE_ID) VALUES ('Água', 3);




INSERT INTO TBL_USERS (NAME, LOGIN, PASSWORD, PROFILE) VALUES ('Jorge', 'jorge@email.com', '1234', 2);
INSERT INTO TBL_USERS (NAME, LOGIN, PASSWORD, PROFILE) VALUES ('Joao', 'joao@email.com', '1234', 1);

INSERT INTO TBL_USERS (NAME, LOGIN, PASSWORD, PROFILE) VALUES ('Jorge', 'jorge@email.com', '1234', 2);
INSERT INTO TBL_USERS (NAME, LOGIN, PASSWORD, PROFILE) VALUES ('Joao', 'joao@email.com', '1234', 1);

INSERT INTO TBL_CATEGORY (NAME, AVAILABILITY) VALUES ('Bebidas', true);
INSERT INTO TBL_CATEGORY (NAME, AVAILABILITY) VALUES ('Prato Principal', false);
INSERT INTO TBL_CATEGORY (NAME, AVAILABILITY) VALUES ('Sobremesa', true);



INSERT INTO TBL_PRODUCT(name, description, price, discount, availability, category, imageUrl)
VALUES ('Pizza Margherita', 'Pizza com molho de tomate, mussarela e manjericão fresco', 39.90, 0.0, TRUE, 'Pizzas', 'https://abrir.link/gSCkw');
INSERT INTO TBL_PRODUCT (name, description, price, discount, availability, category, imageUrl)
VALUES ('Hambúrguer Artesanal', 'Pão brioche, carne Angus, queijo cheddar e molho especial', 29.90, 5.0, TRUE, 'Lanches', 'https://abrir.link/WrIHG');
INSERT INTO TBL_PRODUCT (name, description, price, discount, availability, category, imageUrl)
VALUES ('Salada Caesar', 'Alface americana, croutons, frango grelhado e molho caesar', 24.90, 10.0, TRUE, 'Saladas', 'https://abrir.link/rbVqV');

INSERT INTO TBL_ADDON (NAME, PRICE, AVAILABILITY, PRODUCT_ID, ITEM_ID) VALUES ('SAAS', 100, TRUE, 1, 1)
