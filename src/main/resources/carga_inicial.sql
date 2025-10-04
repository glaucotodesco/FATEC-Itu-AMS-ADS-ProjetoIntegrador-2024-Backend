-- Faz com que o script pare imediatamente se algum comando falhar
\set ON_ERROR_STOP on

-- Limpa todas as tabelas na ordem correta de dependência
DROP TABLE IF EXISTS TBL_ORDER CASCADE;
DROP TABLE IF EXISTS TBL_SCHEDULING CASCADE;
DROP TABLE IF EXISTS TBL_RESTAURANT_SCHEDULING CASCADE;
DROP TABLE IF EXISTS TBL_RESTAURANT_LINK CASCADE;
DROP TABLE IF EXISTS TBL_RESTAURANT CASCADE;
DROP TABLE IF EXISTS TBL_ADDON CASCADE;
DROP TABLE IF EXISTS TBL_ADDONCATEGORY CASCADE;
DROP TABLE IF EXISTS TBL_PRODUCT CASCADE;
DROP TABLE IF EXISTS TBL_SUBCATEGORY CASCADE;
DROP TABLE IF EXISTS TBL_CATEGORY CASCADE;
DROP TABLE IF EXISTS TBL_ITEM CASCADE;
DROP TABLE IF EXISTS TBL_SQUARE CASCADE;
DROP TABLE IF EXISTS TBL_CARD CASCADE;
DROP TABLE IF EXISTS TBL_SEAT CASCADE;
DROP TABLE IF EXISTS TBL_EMPLOYEE CASCADE;
DROP TABLE IF EXISTS TBL_CUSTOMER CASCADE;


-- Criação das tabelas --

CREATE TABLE TBL_CATEGORY (id SERIAL PRIMARY KEY, name VARCHAR(255) NOT NULL UNIQUE, availability BOOLEAN NOT NULL DEFAULT true, deleted BOOLEAN NOT NULL DEFAULT false);
CREATE TABLE TBL_SUBCATEGORY (id SERIAL PRIMARY KEY, name VARCHAR(255) NOT NULL, availability BOOLEAN NOT NULL DEFAULT true, category_id INTEGER NOT NULL REFERENCES TBL_CATEGORY(id), deleted BOOLEAN NOT NULL DEFAULT false);
CREATE TABLE TBL_PRODUCT (id SERIAL PRIMARY KEY, name VARCHAR(255) NOT NULL, description TEXT, price DECIMAL(10, 2) NOT NULL, discount DECIMAL(10, 2) DEFAULT 0.0, availability BOOLEAN NOT NULL DEFAULT true, imageurl TEXT, subcategory_id INTEGER NOT NULL REFERENCES TBL_SUBCATEGORY(id), deleted BOOLEAN NOT NULL DEFAULT false);
CREATE TABLE TBL_SQUARE (id SERIAL PRIMARY KEY, name VARCHAR(255), deleted BOOLEAN NOT NULL DEFAULT false);
CREATE TABLE TBL_ADDONCATEGORY (id SERIAL PRIMARY KEY, name VARCHAR(255) NOT NULL, type INTEGER NOT NULL, product_id INTEGER REFERENCES TBL_PRODUCT(id), deleted BOOLEAN NOT NULL DEFAULT false);
CREATE TABLE TBL_ADDON (id SERIAL PRIMARY KEY, name VARCHAR(255) NOT NULL, price DECIMAL(10, 2), availability BOOLEAN NOT NULL, square_id INTEGER REFERENCES TBL_SQUARE(id), addoncategory_id INTEGER NOT NULL REFERENCES TBL_ADDONCATEGORY(id), deleted BOOLEAN NOT NULL DEFAULT false);
CREATE TABLE TBL_RESTAURANT (id SERIAL PRIMARY KEY, name VARCHAR(255), zip_code VARCHAR(10), neighborhood VARCHAR(255), address VARCHAR(255), number VARCHAR(10), state VARCHAR(2), city VARCHAR(255), who_are_we TEXT, seats INTEGER);
CREATE TABLE TBL_RESTAURANT_LINK (id SERIAL PRIMARY KEY, site VARCHAR(255), url VARCHAR(255), restaurant_id INTEGER REFERENCES TBL_RESTAURANT(id));
CREATE TABLE TBL_RESTAURANT_SCHEDULING (id SERIAL PRIMARY KEY, name VARCHAR(255), starting_time TIME, ending_time TIME, available_days BOOLEAN[], restaurant_id INTEGER REFERENCES TBL_RESTAURANT(id));
CREATE TABLE TBL_SEAT (id SERIAL PRIMARY KEY, status INTEGER);
CREATE TABLE TBL_ITEM (id SERIAL PRIMARY KEY, name VARCHAR(255), square_id INTEGER REFERENCES TBL_SQUARE(id), deleted BOOLEAN);
CREATE TABLE TBL_CARD (id SERIAL PRIMARY KEY, active BOOLEAN, copy INTEGER);
CREATE TABLE TBL_EMPLOYEE (id SERIAL PRIMARY KEY, name VARCHAR(255), login VARCHAR(255) UNIQUE, phone VARCHAR(20), password VARCHAR(255), profile INTEGER, blocked BOOLEAN, deleted BOOLEAN);
CREATE TABLE TBL_CUSTOMER (id SERIAL PRIMARY KEY, name VARCHAR(255), phone VARCHAR(20), birthdate TIMESTAMPTZ, email VARCHAR(255) UNIQUE, deleted BOOLEAN);
CREATE TABLE TBL_ORDER (id SERIAL PRIMARY KEY, opening_time TIMESTAMPTZ, closing_time TIMESTAMPTZ, total DECIMAL(10, 2), card_id INTEGER REFERENCES TBL_CARD(id), seat_id INTEGER REFERENCES TBL_SEAT(id), employee_id INTEGER REFERENCES TBL_EMPLOYEE(id));
CREATE TABLE TBL_SCHEDULING (id SERIAL PRIMARY KEY, starts_at TIMESTAMPTZ, quantity INTEGER, customer_id INTEGER REFERENCES TBL_CUSTOMER(id), seat_id INTEGER REFERENCES TBL_SEAT(id));


-- ###############################################################
-- ## INÍCIO: DADOS DO CARDÁPIO PRINCIPAL (10 PRODUTOS)
-- ###############################################################

-- 1. Categorias e Subcategorias do Cardápio Principal
INSERT INTO TBL_CATEGORY (name, availability, deleted) VALUES ('Entradas', true, false), ('Pratos Principais', true, false), ('Bebidas', true, false), ('Sobremesas', true, false);
INSERT INTO TBL_SUBCATEGORY (name, category_id, deleted) VALUES
('Saladas', 1, false), ('Carnes', 2, false), ('Aves', 2, false), ('Peixes', 2, false), ('Massas', 2, false), ('Sucos', 3, false), ('Drinks', 3, false), ('Sobremesas', 4, false);

-- 2. Produtos do Cardápio Principal (10 itens com imagem)
INSERT INTO TBL_PRODUCT (name, description, price, imageurl, subcategory_id, deleted) VALUES
('Filé Mignon', 'Corte nobre com vinho e trufas.', 35.95, 'https://images.unsplash.com/photo-1546964124-0cce460f38ef?auto=format&fit=crop&q=80', 2, false),
('Bife Ancho Grelhado', 'Corte nobre com chimichurri.', 38.50, 'https://www.minhareceita.com.br/app/uploads/2024/05/mobile-Bife-ancho-com-pure-de-batatas.jpg', 2, false),
('Frango Grelhado', 'Peito de frango com ervas.', 22.00, 'https://static.vecteezy.com/ti/fotos-gratis/p2/4419646-file-frango-grelhado-com-massa-lacos-gratis-foto.jpg', 3, false),
('Salmão Grelhado', 'Ao molho de manteiga e limão.', 24.95, 'https://images.unsplash.com/photo-1519708227418-c8fd9a32b7a2?auto=format&fit=crop&q=80', 4, false),
('Salada Caprese', 'Mussarela, tomate e manjericão.', 13.00, 'https://images.unsplash.com/photo-1608897013039-887f21d8c804?auto=format&fit=crop&q=80', 1, false),
('Spaghetti à Bolonhesa', 'Clássico molho de carne.', 19.90, 'https://cdn.pixabay.com/photo/2022/10/12/22/09/spaghetti-bolognese-7517639_1280.jpg', 5, false),
('Cheesecake de Morango', 'Cremoso com calda de morango.', 13.50, 'https://i.pinimg.com/1200x/8b/c4/e6/8bc4e6c9daa778c290fdb1722b6eb420.jpg', 8, false),
('Caipirinha de Limão', 'Clássica com cachaça, limão e açúcar.', 14.50, 'https://i.panelinha.com.br/i1/bk-8730-blog-caipirinha-de-limao.webp', 7, false),
('Suco de Laranja', 'Natural espremido na hora.', 8.00, 'https://images.unsplash.com/photo-1577803645773-f96470509666?auto=format&fit=crop&q=80', 6, false),
('Costela Barbecue', 'Costela ao molho barbecue.', 32.00, 'https://msabores.com/wp-content/uploads/2025/01/Design-sem-nome.webp', 2, false);

-- 3. Adicionais do Cardápio Principal (IDs dos produtos são de 1 a 10)
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id) VALUES ('Ponto da Carne', 0, 1), ('Molho Extra', 1, 1);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id) VALUES ('Mal passado', 0.00, true, 1), ('Ao ponto', 0.00, true, 1), ('Bem passado', 0.00, true, 1), ('Molho Gorgonzola', 6.00, true, 2), ('Molho Barbecue', 3.00, true, 2);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id) VALUES ('Ponto da Carne', 0, 2), ('Acompanhamentos', 1, 2);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id) VALUES ('Mal passado', 0.00, true, 3), ('Ao ponto', 0.00, true, 3), ('Bem passado', 0.00, true, 3), ('Batata Frita', 4.00, true, 4), ('Purê de Batata', 4.50, true, 4), ('Legumes Assados', 4.50, true, 4);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id) VALUES ('Turbine sua Salada', 1, 5);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id) VALUES ('Azeitonas Pretas', 2.50, true, 5), ('Rúcula', 2.00, true, 5), ('Azeite Trufado', 5.00, true, 5);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id) VALUES ('Extras', 1, 6);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id) VALUES ('Queijo Parmesão Extra', 3.00, true, 6), ('Molho Extra', 1.50, true, 6), ('Pimenta Calabresa', 1.50, true, 6);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id) VALUES ('Cobertura Extra', 0, 7);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id) VALUES ('Calda de Morango', 4.00, true, 7), ('Raspas de Chocolate', 2.50, true, 7);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id) VALUES ('Tipo de Açúcar', 0, 8);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id) VALUES ('Com Açúcar', 0.00, true, 8), ('Com Adoçante', 0.00, true, 8);


-- ###############################################################
-- ## INÍCIO: DADOS ADICIONAIS DE TESTE (FORNECIDOS POR VOCÊ)
-- ###############################################################

INSERT INTO TBL_RESTAURANT (NAME, ZIP_CODE, NEIGHBORHOOD, ADDRESS, NUMBER, STATE, CITY, WHO_ARE_WE, SEATS) VALUES ('Polvo Provençal', '12345-678', 'Jose Andrade', 'R. tal tal', '123', 'SP', 'SOROCABA', 'Um ótimo restaurante', 0);
INSERT INTO TBL_RESTAURANT_LINK (SITE, URL, RESTAURANT_ID) VALUES ('Facebook', 'https://www.facebook.com', 1), ('Instagram', 'https://www.instagram.com', 1);
INSERT INTO TBL_RESTAURANT_SCHEDULING (NAME, STARTING_TIME, ENDING_TIME, AVAILABLE_DAYS, RESTAURANT_ID) VALUES ('Almoço', '13:00:00', '15:00:00', '{false, true, true, true, false, true, false}', 1), ('Jantar', '18:00:00', '21:30:00', '{false, true, true, true, false, true, false}', 1);

INSERT INTO TBL_SEAT (STATUS) VALUES (0), (0), (0), (0), (0), (0), (0), (0);
INSERT INTO TBL_SQUARE (NAME, DELETED) VALUES ('Frituras', false), ('Assados', false), ('Bebidas', false);
INSERT INTO TBL_ITEM (NAME, SQUARE_ID, DELETED) VALUES ('Arroz', 1, false), ('Feijão', 1, false), ('Macarrão', 1, false), ('Azeitonas', 1, false), ('Frango Frito', 2, false), ('Filé Mignon', 2, false), ('Picanha', 2, false), ('Peixe', 2, false), ('Cerveja', 3, false), ('Refrigerante', 3, false), ('Suco', 3, false), ('Água', 3, false);

INSERT INTO TBL_CARD (ACTIVE, COPY) VALUES (true, 1), (false, 2), (false, 1), (true, 1), (false, 2);
INSERT INTO TBL_EMPLOYEE (NAME, LOGIN, PHONE, PASSWORD, PROFILE, BLOCKED, DELETED) VALUES ('Jorge Silva', 'jorge@email.com', '100001030', '1234', 2, FALSE, FALSE), ('Marina Costa', 'marina@email.com', '100001031', '3213', 3, TRUE, FALSE), ('Rafael Lima', 'rafael@email.com', '100001032', '483827', 2, FALSE, FALSE);
INSERT INTO TBL_CUSTOMER (NAME, PHONE, BIRTHDATE, EMAIL, DELETED) VALUES ('John Doe', '100001020', '2000-11-25T00:00:00Z', 'johndoe@gmail.com', false), ('Alice Smith', '100001021', '1995-08-15T00:00:00Z', 'alice.smith@gmail.com', false), ('Carlos Mendes', '100001022', '1988-04-03T00:00:00Z', 'carlos.mendes@gmail.com', false);

-- CORREÇÃO: Nova categoria "Pizzas e Lanches" para os produtos de teste. Categoria ID = 5 (depois das 4 que já criamos)
INSERT INTO TBL_CATEGORY (NAME, AVAILABILITY, DELETED) VALUES ('Pizzas e Lanches', true, false);

-- CORREÇÃO: Subcategorias apontando para a nova categoria "Pizzas e Lanches" (ID 5)
INSERT INTO TBL_SUBCATEGORY (NAME, AVAILABILITY, CATEGORY_ID, DELETED) VALUES ('Pizzas', true, 5, false ), ('Hambúrgueres', false, 5, false ), ('Saladas Especiais', true, 5, false ); -- Subcategorias IDs 9, 10, 11

-- CORREÇÃO: Produtos apontando para as novas subcategorias (IDs 9, 10, 11) e com a coluna `imageurl` corrigida.
INSERT INTO TBL_PRODUCT (name, description, price, discount, availability, SUBCATEGORY_ID, imageurl, DELETED) VALUES
('Pizza Margherita', 'Pizza com molho de tomate, mussarela e manjericão fresco', 39.90, 0.0, TRUE, 9, 'https://abrir.link/gSCkw', false),
('Hambúrguer Artesanal', 'Pão brioche, carne Angus, queijo cheddar e molho especial', 29.90, 5.0, TRUE, 10, 'https://abrir.link/WrIHG', false),
('Salada Caesar de Teste', 'Alface americana, croutons, frango grelhado e molho caesar', 24.90, 10.0, TRUE, 11 , 'https://abrir.link/rbVqV', false);

-- CORREÇÃO: IDs dos produtos de teste agora são 11, 12 e 13.
INSERT INTO TBL_ADDONCATEGORY (NAME, TYPE, PRODUCT_ID, DELETED) VALUES ('Molhos Extras (Salada)', 1, 13, false), ('Adicionais do Hambúrguer', 1, 12, false), ('Recheios Extras (Pizza)', 1, 11, false); -- AddonCat IDs 9, 10, 11

-- CORREÇÃO: IDs de AddonCategory agora são 9, 10, 11.
INSERT INTO TBL_ADDON (NAME, PRICE, AVAILABILITY, SQUARE_ID, ADDONCATEGORY_ID, DELETED) VALUES
('Molho Caesar Extra', 3.50, TRUE, 3, 9, false), ('Parmesão Ralado', 2.00, TRUE, 3, 9, false),
('Bacon Crocante', 4.00, TRUE, 2, 10, false), ('Ovo Frito', 3.00, TRUE, 2, 10, false),
('Recheio de Calabresa', 5.00, TRUE, 1, 11, false), ('Recheio de Catupiry', 5.00, TRUE, 1, 11, false);


/*
INSERT INTO TBL_ORDER (OPENING_TIME, CLOSING_TIME, TOTAL, CARD_ID, SEAT_ID, EMPLOYEE_ID)
VALUES ('1992-12-30 00:00:00', '1992-12-30 01:30:00', 49.99, 1, 5, 2);
...
*/

/*
INSERT INTO TBL_SCHEDULING (STARTS_AT, QUANTITY, CUSTOMER_ID, SEAT_ID)
VALUES ('2023-05-04 14:30:00', 21, 1, 1);
...
*/


\echo 'Script combinado executado com sucesso! ✅'