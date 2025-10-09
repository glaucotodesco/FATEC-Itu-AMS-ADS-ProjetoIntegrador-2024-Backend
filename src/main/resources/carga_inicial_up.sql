-- =====================================================================
--                        SCRIPT COMPLETO DO BANCO DE DADOS
-- =====================================================================

-- Faz com que o script pare imediatamente se algum comando falhar
\set ON_ERROR_STOP on

-- ---------------------------------------------------------------------
-- LIMPEZA DO BANCO DE DADOS (DROP TABLES)
-- ---------------------------------------------------------------------
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


-- ---------------------------------------------------------------------
-- CRIAÇÃO DA ESTRUTURA (CREATE TABLES)
-- ---------------------------------------------------------------------
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


-- ---------------------------------------------------------------------
-- INSERÇÃO DE DADOS DO CARDÁPIO
-- ---------------------------------------------------------------------
-- 1. Categorias
INSERT INTO TBL_CATEGORY (name, availability, deleted) VALUES 
('Entradas', true, false), ('Pratos Principais', true, false), ('Bebidas', true, false), ('Sobremesas', true, false), ('Pizzas e Lanches', true, false);

-- 2. Subcategorias
INSERT INTO TBL_SUBCATEGORY (name, category_id, deleted) VALUES
('Saladas', 1, false), ('Sopas e Caldos', 1, false), ('Porções', 1, false), ('Bruschettas', 1, false),
('Carnes', 2, false), ('Aves', 2, false), ('Peixes', 2, false), ('Massas', 2, false),
('Sucos e Refrigerantes', 3, false), ('Drinks e Coquetéis', 3, false), ('Cervejas', 3, false), ('Vinhos', 3, false),
('Tortas', 4, false), ('Mousses', 4, false), ('Sorvetes', 4, false), ('Pudins', 4, false),
('Pizzas', 5, false ), ('Hambúrgueres', 5, false ), ('Saladas Especiais', 5, false ), ('Lanches Quentes', 5, false );

-- 3. Produtos
INSERT INTO TBL_PRODUCT (name, description, price, imageurl, subcategory_id, deleted) VALUES
-- Saladas (ID: 1 a 3)
('Salada Caprese', 'Mussarela de búfala, tomate fresco e manjericão.', 28.00, 'https://images.unsplash.com/photo-1608897013039-887f21d8c804?auto=format&fit=crop&q=80', 1, false),
('Salada Grega', 'Pepino, tomate, cebola roxa, azeitonas e queijo feta.', 32.00, 'https://alimentesebem.sesisp.org.br/app/uploads/2022/12/GettyImages-1161711740-729x410.jpg', 1, false),
('Salada de Quinoa', 'Quinoa com legumes frescos, abacate e molho de limão.', 34.00, 'https://assets.tmecosys.com/image/upload/t_web_rdp_recipe_584x480/img/recipe/ras/Assets/7361ECCF-057F-4B35-AE4A-EDD9B1C222F6/Derivates/df407dea-007e-4af9-81aa-b8e1c453aa0f.jpg', 1, false),
-- Sopas e Caldos (ID: 4 a 6)
('Sopa de Cebola Gratinada', 'Clássica sopa de cebola com queijo gratinado.', 25.00, 'https://www.receitasnestle.com.br/sites/default/files/styles/recipe_detail_desktop_new/public/srh_recipes/d3aa03b3fcbdd3e211f66cfbd58ac98c.webp?itok=wcEMreXP', 2, false),
('Creme de Mandioquinha', 'Creme aveludado de mandioquinha com um toque de gengibre.', 23.00, 'https://anamariareceitas.com.br/wp-content/uploads/2022/11/Sopa-creme-de-Mandioquinha-768x432.jpg', 2, false),
('Caldo Verde', 'Tradicional caldo português com couve e linguiça.', 26.00, 'https://s2-receitas.glbimg.com/d51cHB7NuxyXBrEkCpp1aKrQixc=/0x0:1366x768/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_1f540e0b94d8437dbbc39d567a1dee68/internal_photos/bs/2024/b/9/le3LrdQTu4r3xZvInLAA/caldo-verde-receita.jpg', 2, false),
-- Porções (ID: 7 a 9)
('Batata Frita com Cheddar e Bacon', 'Porção generosa de batatas fritas com cheddar cremoso e bacon.', 35.00, 'https://softpig.com.br/wp-content/uploads/2023/10/Batata-frita-com-cheddar-e-bacon-Receita-softpig-1200x600.jpg', 3, false),
('Anéis de Cebola', 'Anéis de cebola crocantes servidos com molho barbecue.', 29.00, 'https://th.bing.com/th/id/OIP.eoQR4gtG6tvXBTVbc75KNgHaEK', 3, false),
('Mandioca Frita', 'Mandioca cozida e frita, crocante por fora e macia por dentro.', 27.00, 'https://anamariabrogui.com.br/assets/uploads/receitas/fotos/usuario-2600-e1e93ed120648f800ef27e4a709540c5.jpg', 3, false),
-- Bruschettas (ID: 10 a 12)
('Bruschetta de Tomate Clássica', 'Pão italiano, tomate fresco, alho e manjericão.', 22.00, 'https://i.panelinha.com.br/i1/64-bk-8124-receita-bruschetta.webp', 4, false),
('Bruschetta de Cogumelos', 'Cogumelos salteados no azeite e ervas sobre pão italiano.', 26.00, 'https://www.estadao.com.br/resizer/v2/TJGCECLLHFDRFHU5PWZZARVVT4.jpg?quality=80&auth=87d627e72db7a72544f0a810d3bb3179aa4745def798604909977b62a4e98248&width=720&height=410&smart=true', 4, false),
('Bruschetta de Gorgonzola e Nozes', 'Creme de gorgonzola com nozes picadas e um fio de mel.', 29.00, 'https://padariavianney.com.br/web/image/product.product/3136/image_1024/%5B97230%5D%20Bruschetta%20de%20Gorgonzola%20com%20Nozes%20e%20Mel%20de%20Figo%20no%20P%C3%A3o%20Australiano?unique=3d53aa6', 4, false),
-- Carnes (ID: 13 a 15)
('Filé Mignon', 'Corte nobre com vinho e trufas.', 75.95, 'https://images.unsplash.com/photo-1546964124-0cce460f38ef?auto=format&fit=crop&q=80', 5, false),
('Bife Ancho Grelhado', 'Corte nobre com chimichurri.', 78.50, 'https://img.dicasdochefe.com.br/img/5659/32ee0478044142851525f5d14387bcaa73d866262b92f1c3cbe3c4332a7ced53.jpg', 5, false),
('Costela Barbecue', 'Costela suína defumada ao molho barbecue.', 69.00, 'https://msabores.com/wp-content/uploads/2025/01/Design-sem-nome.webp', 5, false),
-- Aves (ID: 16 a 18)
('Frango Grelhado', 'Peito de frango com ervas finas.', 42.00, 'https://static.vecteezy.com/ti/fotos-gratis/p2/4419646-file-frango-grelhado-com-massa-lacos-gratis-foto.jpg', 6, false),
('Frango à Parmegiana', 'Filé de frango empanado, coberto com molho de tomate e queijo.', 48.00, 'https://guiadacozinha.com.br/wp-content/uploads/2013/01/filedefrangoaparmegiana.jpg', 6, false),
('Risoto de Frango com Limão Siciliano', 'Arroz arbóreo cremoso com tiras de frango e raspas de limão siciliano.', 52.00, 'https://sabores-new.s3.amazonaws.com/public/2024/11/risoto-de-frango-1024x494.webp', 6, false),
-- Peixes (ID: 19 a 21)
('Salmão Grelhado', 'Ao molho de manteiga e limão.', 64.95, 'https://images.unsplash.com/photo-1519708227418-c8fd9a32b7a2?auto=format&fit=crop&q=80', 7, false),
('Moqueca de Peixe', 'Posta de peixe branco cozida com leite de coco, azeite de dendê e pimentões.', 72.00, 'https://conteudo.imguol.com.br/c/entretenimento/02/2020/03/31/moqueca-de-peixe-1585666205541_v2_900x506.jpg.webp', 7, false),
('Tilápia com Crosta de Ervas', 'Filé de tilápia assado com uma crosta crocante de ervas frescas.', 55.00, 'https://www.delicioso.com.br/wp-content/uploads/migration/crosta-castanhas-peixe-1222-140000.jpg.jpg', 7, false),
-- Massas (ID: 22 a 24)
('Spaghetti à Bolonhesa', 'Clássico molho de carne e tomate.', 39.90, 'https://cdn.pixabay.com/photo/2022/10/12/22/09/spaghetti-bolognese-7517639_1280.jpg', 8, false),
('Lasanha à Bolonhesa', 'Camadas de massa, molho bolonhesa, presunto, queijo e molho branco.', 45.00, 'https://cdn.pixabay.com/photo/2016/12/11/22/41/lasagna-1900529_1280.jpg', 8, false),
('Penne ao Molho Alfredo', 'Penne com molho cremoso de queijo parmesão e noz-moscada.', 42.00, 'https://receitasgalo.com.br/images/receitas/254/galo-imagem-receitas-macarrao-ao-molho-alfredo-com-frango-share.jpg', 8, false),
-- Sucos e Refrigerantes (ID: 25 a 27)
('Suco de Laranja', 'Natural, espremido na hora.', 9.00, 'https://images.unsplash.com/photo-1577803645773-f96470509666?auto=format&fit=crop&q=80', 9, false),
('Limonada Suíça', 'Limão, água, açúcar e leite condensado.', 12.00, 'https://www.receiteria.com.br/wp-content/uploads/limonada-suica-cremosa-730x914.jpg', 9, false),
('Coca-Cola Lata 350ml', 'Refrigerante de cola.', 7.00, 'https://blog.somostera.com/hubfs/Blog_free_images/Uma%20lata%20de%20coca%20cola%20em%20cima%20da%20mesa.jpg', 9, false),
-- Drinks e Coquetéis (ID: 28 a 30)
('Caipirinha de Limão', 'Clássica com cachaça, limão e açúcar.', 18.50, 'https://i.panelinha.com.br/i1/bk-8730-blog-caipirinha-de-limao.webp', 10, false),
('Mojito', 'Rum, hortelã, limão, açúcar e água com gás.', 22.00, 'https://www.saveur.com/uploads/2007/02/SAVEUR_Mojito_1149-Edit-scaled.jpg?format=auto&optimize=high&width=1440', 10, false),
('Gin Tônica', 'Gin, água tônica, limão e especiarias.', 25.00, 'https://blog.biglar.com.br/wp-content/uploads/2021/12/iStock-1310029561.jpg', 10, false),
-- Cervejas (ID: 31 a 33)
('Heineken Long Neck 330ml', 'Cerveja Premium Lager.', 12.00, 'https://zaffari.vtexassets.com/arquivos/ids/251014-800-450?v=638554610864900000&width=800&height=450&aspect=true', 11, false),
('Stella Artois Long Neck 275ml', 'Cerveja Premium American Lager.', 11.00, 'https://cdn.dooca.store/43635/products/nouzur1nkfwluhrkbgpmaogxwktr6w8rmtyv.jpg?v=1670687986&webp=0', 11, false),
('Budweiser Long Neck 330ml', 'Cerveja Premium American Lager.', 10.00, 'https://http2.mlstatic.com/D_NQ_NP_663782-MLB53038646150_122022-O-kit-cerveja-budweiser-long-neck-330ml-6-und.webp', 11, false),
-- Vinhos (ID: 34 a 36)
('Vinho Tinto (Taça)', 'Vinho tinto seco da casa.', 25.00, 'https://www.divinho.com.br/blog/wp-content/uploads/2020/08/Vinho-Tinto.jpg', 12, false),
('Vinho Branco (Taça)', 'Vinho branco seco da casa.', 25.00, 'https://www.evino.com.br/blog/wp-content/uploads/2021/08/vinho-tinto.jpg', 12, false),
('Vinho Rosé (Taça)', 'Vinho rosé seco da casa.', 25.00, 'https://vinholando.com/wp-content/uploads/2021/06/vinho-tinto-tipos.jpg', 12, false),
-- Tortas (ID: 37 a 39)
('Cheesecake de Morango', 'Cremoso com calda de morango.', 23.50, 'https://i.pinimg.com/1200x/8b/c4/e6/8bc4e6c9daa778c290fdb1722b6eb420.jpg', 13, false),
('Torta Holandesa', 'Base de biscoito, creme holandês e cobertura de chocolate.', 22.00, 'https://static.itdg.com.br/images/640-440/d67039c3ae791ed32e8d2912251c9495/312799-original-1-2-.jpg', 13, false),
('Torta de Limão', 'Massa doce com recheio de limão e cobertura de merengue.', 21.00, 'https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRmnTynbHX8wBHmdzmQQv90HZ5RwbEvrGNQxl-Lj8hNhPHb-sJ6NYZravLYfxBMnMVd33TTu_3tc00D7qT9RkXF5aWj5E-wyyAvRAfSXWOyAw', 13, false),
-- Mousses (ID: 40 a 42)
('Mousse de Chocolate', 'Mousse aerado de chocolate meio amargo.', 18.00, 'https://www.receitasnestle.com.br/sites/default/files/srh_recipes/369562012750bd46ceaeef5d59a23229.jpg', 14, false),
('Mousse de Maracujá', 'Mousse cremoso de maracujá com calda da fruta.', 17.00, 'https://guiadacozinha.com.br/wp-content/uploads/2019/11/Mousse-de-maracuja-com-cachaca.jpg', 14, false),
('Mousse de Limão', 'Mousse refrescante de limão com raspas da casca.', 17.00, 'https://static.itdg.com.br/images/1200-630/0de830d655f541cde758d7d65c943cc6/shutterstock-2307267609.jpg', 14, false),
-- Sorvetes (ID: 43 a 45)
('Sorvete de Creme (2 bolas)', 'Sorvete artesanal de creme.', 15.00, 'https://i.ytimg.com/vi/HVl-TSo1czg/hq720.jpg?sqp=-oaymwEhCK4FEIIDSFryq4qpAxMIARUAAAAAGAElAADIQj0AgKJD&rs=AOn4CLDvK06_rvFF7MLVIOcRUMQDUu3Nkw', 15, false),
('Sorvete de Chocolate (2 bolas)', 'Sorvete artesanal de chocolate belga.', 15.00, 'https://gastronomiacarioca.zonasul.com.br/wp-content/uploads/2024/02/destaque_mousse_chocolate_zona_sul_ilustrativo.jpg', 15, false),
('Sorvete de Morango (2 bolas)', 'Sorvete artesanal de morango.', 15.00, 'https://scontent.fsod1-2.fna.fbcdn.net/v/t1.6435-9/62445059_2114664675322504_8076625449679060992_n.jpg?_nc_cat=103&ccb=1-7&_nc_sid=0b6b33&_nc_eui2=AeFIe5HOcNN6szm393j4OEcxr6PKfT49Zsivo8p9Pj1myD7DsdCJ49zzgjdHCprvy6kOTHERQSsTUCspCmOQnW9L&_nc_ohc=bW3zJRdjBjYQ7kNvwEmbpMG&_nc_oc=Adn3NKxrFjJwaTZX2C7WTQjXJWZ6U2UeRWnm_0R4TYHe4b4lbxIf0iR8efi3hvSTynk&_nc_zt=23&_nc_ht=scontent.fsod1-2.fna&_nc_gid=bCIAStygY5waZwidD9uDzQ&oh=00_AffuxL9gK9iWGkRpQ1Zn4k57Xtl9qYU4HZZX5amOjLrpow&oe=690BDD8F', 15, false),
-- Pudins (ID: 46 a 48)
('Pudim de Leite Condensado', 'Pudim clássico com calda de caramelo.', 16.00, 'https://s2-receitas.glbimg.com/jK-kMTPr3Yzex9P93zqt4DSsFXo=/0x0:1366x768/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_1f540e0b94d8437dbbc39d567a1dee68/internal_photos/bs/2023/z/0/RsipkzTEu0Y1PGiavCpA/pudim-de-leite-condensado.jpg', 16, false),
('Manjar de Coco', 'Manjar de coco com calda de ameixa.', 18.00, 'https://www.receitasnestle.com.br/sites/default/files/srh_recipes/064c0d70737d4650c298e0331ecd8ae8.jpg', 16, false),
('Pudim de Pão', 'Pudim cremoso feito com pão e calda de caramelo.', 15.00, 'https://cozinhaglobo.com.br/wp-content/uploads/2023/07/pudim-de-leite-1024x682.jpg', 16, false),
-- Pizzas (ID: 49 a 51)
('Pizza Margherita', 'Molho de tomate, mussarela e manjericão fresco.', 49.90, 'https://abrir.link/gSCkw', 17, false),
('Pizza de Calabresa', 'Molho de tomate, mussarela, calabresa fatiada e cebola.', 52.00, 'https://cdn.pixabay.com/photo/2020/05/17/04/22/pizza-5179939_1280.jpg', 17, false),
('Pizza Quatro Queijos', 'Molho de tomate, mussarela, provolone, parmesão e gorgonzola.', 55.00, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRe0Amso1cpQDt2etL5i4o2a96AWsQhKAl-x5mCs00CWI6-WQ_KCAgk6fotyOTVRb4Ct7Se-Pk3PZ56Ehq7MAk62DCgGVmhOreRd9kwDkk', 17, false),
-- Hambúrgueres (ID: 52 a 54)
('Hambúrguer Artesanal', 'Pão brioche, carne Angus, queijo cheddar e molho especial.', 39.90, 'https://abrir.link/WrIHG', 18, false),
('Cheeseburger Clássico', 'Pão, carne, queijo, alface, tomate e picles.', 35.00, 'https://images.unsplash.com/photo-1568901346375-23c9450c58cd?auto=format&fit=crop&q=80', 18, false),
('Hambúrguer de Frango Crocante', 'Pão brioche, filé de frango empanado, alface e maionese.', 37.00, 'https://s2-vogue.glbimg.com/F4TMDrT0LsVvTElZUDevP_zGvZc=/0x0:640x413/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_5dfbcf92c1a84b20a5da5024d398ff2f/internal_photos/bs/2022/Q/K/4pDRJAQqAbvbTKCVKVsg/2017-07-27-smart-burguer-cocoricrok-creditos-wellington-nemeth.jpg', 18, false),
-- Saladas Especiais (ID: 55 a 57)
('Salada Caesar', 'Alface americana, croutons, frango grelhado e molho caesar.', 34.90, 'https://abrir.link/rbVqV', 19, false),
('Salada Cobb', 'Mix de folhas, frango, bacon, ovo cozido, abacate e tomate.', 38.00, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT6IZ3NHjcZOauHKOdUzt33J_bsFO5TSwKh6yZreEvWS5eY7IXMKpWotLeFmPbKDCyeJn_pn-jAQK9IZ9UniST6H1cxpxOjICLJjxjvzA', 19, false),
('Salada Waldorf', 'Maçã, aipo, nozes e maionese sobre uma cama de alface.', 36.00, 'https://paolacarosella.com.br/cms/wp-content/uploads/2024/02/Waldorf-Salad-scaled.jpg', 19, false),

-- Lanches Quentes (ID: 58 a 60)
('Misto Quente', 'Pão de forma, presunto e queijo na chapa.', 18.00, 'https://minhasreceitinhas.com.br/wp-content/uploads/2022/09/Misto-quente-de-padaria.png', 20, false),
('Bauru', 'Pão francês, rosbife, queijo, tomate e picles.', 25.00, 'https://img.estadao.com.br/resources/jpg/3/5/1543524203153.jpg', 20, false),
('Croque Monsieur', 'Misto quente gratinado com queijo e molho bechamel.', 28.00, 'https://portal.rioaliancafrancesa.com.br/wp-content/uploads/2023/11/croque-monsieur-blogaf-8.png', 20, false);


-- ---------------------------------------------------------------------
-- INSERÇÃO DE ADICIONAIS (ADDONS)
-- ---------------------------------------------------------------------
-- Adicionais Originais
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id) VALUES ('Ponto da Carne (Filé Mignon)', 0, 13), ('Molho Extra (Filé Mignon)', 1, 13);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id) VALUES ('Mal passado', 0.00, true, 1), ('Ao ponto', 0.00, true, 1), ('Bem passado', 0.00, true, 1), ('Molho Gorgonzola', 6.00, true, 2), ('Molho Barbecue', 3.00, true, 2);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id) VALUES ('Ponto da Carne (Bife Ancho)', 0, 14), ('Acompanhamentos (Bife Ancho)', 1, 14);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id) VALUES ('Mal passado', 0.00, true, 3), ('Ao ponto', 0.00, true, 3), ('Bem passado', 0.00, true, 3), ('Batata Frita', 4.00, true, 4), ('Purê de Batata', 4.50, true, 4), ('Legumes Assados', 4.50, true, 4);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id) VALUES ('Turbine sua Salada', 1, 1);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id) VALUES ('Azeitonas Pretas', 2.50, true, 5), ('Rúcula', 2.00, true, 5), ('Azeite Trufado', 5.00, true, 5);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id) VALUES ('Extras (Spaghetti)', 1, 22);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id) VALUES ('Queijo Parmesão Extra', 3.00, true, 6), ('Molho Extra', 1.50, true, 6), ('Pimenta Calabresa', 1.50, true, 6);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id) VALUES ('Cobertura Extra (Cheesecake)', 0, 37);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id) VALUES ('Calda de Morango', 4.00, true, 7), ('Raspas de Chocolate', 2.50, true, 7);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id) VALUES ('Tipo de Açúcar (Caipirinha)', 0, 28);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id) VALUES ('Com Açúcar', 0.00, true, 8), ('Com Adoçante', 0.00, true, 8);
INSERT INTO TBL_ADDONCATEGORY (NAME, TYPE, PRODUCT_ID, DELETED) VALUES ('Molhos Extras (Salada)', 1, 55, false);
INSERT INTO TBL_ADDON (NAME, PRICE, AVAILABILITY, ADDONCATEGORY_ID, DELETED) VALUES ('Molho Caesar Extra', 3.50, TRUE, 9, false), ('Parmesão Ralado', 2.00, TRUE, 9, false);
INSERT INTO TBL_ADDONCATEGORY (NAME, TYPE, PRODUCT_ID, DELETED) VALUES ('Adicionais do Hambúrguer', 1, 52, false);
INSERT INTO TBL_ADDON (NAME, PRICE, AVAILABILITY, ADDONCATEGORY_ID, DELETED) VALUES ('Bacon Crocante', 4.00, TRUE, 10, false), ('Ovo Frito', 3.00, TRUE, 10, false);
INSERT INTO TBL_ADDONCATEGORY (NAME, TYPE, PRODUCT_ID, DELETED) VALUES ('Recheios Extras (Pizza)', 1, 49, false);
INSERT INTO TBL_ADDON (NAME, PRICE, AVAILABILITY, ADDONCATEGORY_ID, DELETED) VALUES ('Recheio de Calabresa', 5.00, TRUE, 11, false), ('Recheio de Catupiry', 5.00, TRUE, 11, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Opções de Molho (Salmão)', 0, 19, false); 
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Molho de Maracujá', 7.00, true, 12, false), ('Molho de Alcaparras', 6.00, true, 12, false), ('Manteiga de Ervas', 5.00, true, 12, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Borda Recheada', 0, 50, false), ('Adicionais (Pizza)', 1, 50, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Borda com Catupiry', 8.00, true, 13, false), ('Borda com Cheddar', 8.00, true, 13, false), ('Extra Bacon', 5.00, true, 14, false), ('Azeitonas Pretas', 3.00, true, 14, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Toppings para Mousse', 1, 40, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Raspas de Chocolate Branco', 3.00, true, 15, false), ('Pedaços de Morango', 4.00, true, 15, false), ('Granulado Crocante', 2.00, true, 15, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Opções de Açúcar (Suco)', 0, 25, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Com Açúcar', 0.00, true, 16, false), ('Com Adoçante', 0.00, true, 16, false), ('Sem Açúcar', 0.00, true, 16, false);

-- Novos Adicionais (completando todos os produtos)
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Adicionais (Salada Grega)', 1, 2, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Frango Grelhado em Tiras', 8.00, true, 17, false), ('Croutons', 2.50, true, 17, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Adicionais (Salada de Quinoa)', 1, 3, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Tofu Grelhado', 7.00, true, 18, false), ('Mix de Sementes', 3.00, true, 18, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Acompanhamentos (Sopa)', 1, 4, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Torradas com Azeite', 4.00, true, 19, false), ('Queijo Parmesão Extra', 3.00, true, 19, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Acompanhamentos (Creme)', 1, 5, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Bacon em Cubos', 3.50, true, 20, false), ('Cheiro Verde', 1.50, true, 20, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Acompanhamentos (Caldo Verde)', 1, 6, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Pão Italiano (fatia)', 2.50, true, 21, false), ('Azeite Extra Virgem', 2.00, true, 21, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Molhos Extras (Batata Frita)', 1, 7, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Maionese da Casa', 3.00, true, 22, false), ('Molho Barbecue', 3.00, true, 22, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Molhos Extras (Anéis de Cebola)', 1, 8, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Molho Rosé', 3.00, true, 23, false), ('Geleia de Pimenta', 3.50, true, 23, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Adicionais (Mandioca Frita)', 1, 9, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Carne Seca Desfiada', 9.00, true, 24, false), ('Queijo Coalho Grelhado', 7.00, true, 24, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Extras (Bruschetta)', 1, 10, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Lascas de Parmesão', 4.00, true, 25, false), ('Redução de Balsâmico', 2.50, true, 25, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Extras (Bruschetta Cogumelos)', 1, 11, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Azeite Trufado', 5.00, true, 26, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Extras (Bruschetta Gorgonzola)', 1, 12, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Favo de Mel', 6.00, true, 27, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Acompanhamentos (Costela)', 1, 15, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Batatas Rústicas', 8.00, true, 28, false), ('Salada Coleslaw', 7.00, true, 28, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Acompanhamentos (Frango Grelhado)', 1, 16, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Arroz com Brócolis', 7.00, true, 29, false), ('Purê de Batata', 6.00, true, 29, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Acompanhamentos (Parmegiana)', 1, 17, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Porção Extra de Arroz', 6.00, true, 30, false), ('Porção Extra de Fritas', 8.00, true, 30, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Extras (Risoto)', 1, 18, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Crisps de Parma', 7.00, true, 31, false), ('Lascas de Amêndoas', 4.00, true, 31, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Acompanhamentos (Moqueca)', 1, 20, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Pirão Extra', 5.00, true, 32, false), ('Farofa de Dendê', 4.00, true, 32, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Acompanhamentos (Tilápia)', 1, 21, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Purê de Mandioquinha', 8.00, true, 33, false), ('Arroz Negro', 9.00, true, 33, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Extras (Lasanha)', 1, 23, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Porção de Pão de Alho', 6.00, true, 34, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Turbine seu Penne', 1, 24, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Tiras de Frango Grelhado', 8.00, true, 35, false), ('Bacon em Cubos', 4.00, true, 35, false), ('Brócolis', 4.00, true, 35, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Opções de Açúcar (Limonada)', 0, 26, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Com Açúcar', 0.00, true, 36, false), ('Com Adoçante', 0.00, true, 36, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Adicionais (Coca-Cola)', 1, 27, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Gelo e Limão', 1.00, true, 37, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Adicionais (Mojito)', 1, 29, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Dose Extra de Rum', 10.00, true, 38, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Especiarias', 1, 30, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Zimbro Extra', 2.00, true, 39, false), ('Pimenta Rosa', 2.00, true, 39, false), ('Anis Estrelado', 2.00, true, 39, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Acompanhamentos (Cerveja)', 1, 31, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Porção de Amendoim', 5.00, true, 40, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Acompanhamentos (Cerveja)', 1, 32, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Porção de Amendoim', 5.00, true, 41, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Acompanhamentos (Cerveja)', 1, 33, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Porção de Amendoim', 5.00, true, 42, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Harmonização (Vinho)', 1, 34, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Porção de Queijos Finos', 15.00, true, 43, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Harmonização (Vinho)', 1, 35, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Porção de Azeitonas Temperadas', 8.00, true, 44, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Harmonização (Vinho)', 1, 36, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Canapés', 12.00, true, 45, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Extras (Torta Holandesa)', 1, 38, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Calda de Chocolate Extra', 4.00, true, 46, false), ('Bola de Sorvete de Creme', 6.00, true, 46, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Extras (Torta de Limão)', 1, 39, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Raspas de Limão Siciliano', 2.00, true, 47, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Toppings (Mousse Maracujá)', 1, 41, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Calda de Maracujá Extra', 3.00, true, 48, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Toppings (Mousse Limão)', 1, 42, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Suspiros Quebrados', 2.50, true, 49, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Coberturas e Toppings', 1, 43, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Calda de Caramelo', 3.00, true, 50, false), ('Castanha de Caju', 4.00, true, 50, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Coberturas e Toppings', 1, 44, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Calda de Chocolate', 3.00, true, 51, false), ('Granulado', 2.00, true, 51, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Coberturas e Toppings', 1, 45, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Calda de Morango', 3.00, true, 52, false), ('Leite em Pó', 2.50, true, 52, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Extras (Pudim)', 1, 46, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Calda de Caramelo Extra', 3.00, true, 53, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Extras (Manjar)', 1, 47, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Calda de Ameixa Extra', 3.00, true, 54, false), ('Coco Ralado Fresco', 2.50, true, 54, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Extras (Pudim de Pão)', 1, 48, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Doce de Leite', 4.00, true, 55, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Borda Recheada', 0, 51, false), ('Adicionais (Pizza 4 Queijos)', 1, 51, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Borda com Catupiry', 8.00, true, 56, false), ('Borda com Cheddar', 8.00, true, 56, false), ('Bacon em Cubos', 5.00, true, 57, false), ('Alho Frito', 3.00, true, 57, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Turbine seu Burger', 1, 53, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Bacon Crocante', 4.00, true, 58, false), ('Ovo Frito', 3.00, true, 58, false), ('Cebola Caramelizada', 3.50, true, 58, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Turbine seu Frango Burger', 1, 54, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Queijo Extra', 3.00, true, 59, false), ('Picles', 2.00, true, 59, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Molhos (Salada Cobb)', 0, 56, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Molho Blue Cheese', 0.00, true, 60, false), ('Molho Ranch', 0.00, true, 60, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Extras (Salada Waldorf)', 1, 57, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Passas', 2.00, true, 61, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Adicionais (Misto Quente)', 1, 58, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Tomate e Orégano', 2.00, true, 62, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Adicionais (Bauru)', 1, 59, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Ovo Frito', 3.00, true, 63, false);
INSERT INTO TBL_ADDONCATEGORY (name, type, product_id, deleted) VALUES ('Adicionais (Croque Monsieur)', 1, 60, false);
INSERT INTO TBL_ADDON (name, price, availability, addoncategory_id, deleted) VALUES ('Porção de Fritas Palito', 10.00, true, 64, false);

-- ---------------------------------------------------------------------
-- INSERÇÃO DE DADOS GERAIS DO SISTEMA
-- ---------------------------------------------------------------------
INSERT INTO TBL_RESTAURANT (NAME, ZIP_CODE, NEIGHBORHOOD, ADDRESS, NUMBER, STATE, CITY, WHO_ARE_WE, SEATS) VALUES ('Polvo Provençal', '12345-678', 'Jose Andrade', 'R. tal tal', '123', 'SP', 'SOROCABA', 'Um ótimo restaurante', 0);
INSERT INTO TBL_RESTAURANT_LINK (SITE, URL, RESTAURANT_ID) VALUES ('Facebook', 'https://www.facebook.com', 1), ('Instagram', 'https://www.instagram.com', 1);
INSERT INTO TBL_RESTAURANT_SCHEDULING (NAME, STARTING_TIME, ENDING_TIME, AVAILABLE_DAYS, RESTAURANT_ID) VALUES ('Almoço', '13:00:00', '15:00:00', '{false, true, true, true, false, true, false}', 1), ('Jantar', '18:00:00', '21:30:00', '{false, true, true, true, false, true, false}', 1);

INSERT INTO TBL_SEAT (STATUS) VALUES (0), (0), (0), (0), (0), (0), (0), (0);
INSERT INTO TBL_SQUARE (NAME, DELETED) VALUES ('Frituras', false), ('Assados', false), ('Bebidas', false);
INSERT INTO TBL_ITEM (NAME, SQUARE_ID, DELETED) VALUES ('Arroz', 1, false), ('Feijão', 1, false), ('Macarrão', 1, false), ('Azeitonas', 1, false), ('Frango Frito', 2, false), ('Filé Mignon', 2, false), ('Picanha', 2, false), ('Peixe', 2, false), ('Cerveja', 3, false), ('Refrigerante', 3, false), ('Suco', 3, false), ('Água', 3, false);

INSERT INTO TBL_CARD (ACTIVE, COPY) VALUES (true, 1), (false, 2), (false, 1), (true, 1), (false, 2);
INSERT INTO TBL_EMPLOYEE (NAME, LOGIN, PHONE, PASSWORD, PROFILE, BLOCKED, DELETED) VALUES ('Jorge Silva', 'jorge@email.com', '100001030', '1234', 2, FALSE, FALSE), ('Marina Costa', 'marina@email.com', '100001031', '3213', 3, TRUE, FALSE), ('Rafael Lima', 'rafael@email.com', '100001032', '483827', 2, FALSE, FALSE);
INSERT INTO TBL_CUSTOMER (NAME, PHONE, BIRTHDATE, EMAIL, DELETED) VALUES ('John Doe', '100001020', '2000-11-25T00:00:00Z', 'johndoe@gmail.com', false), ('Alice Smith', '100001021', '1995-08-15T00:00:00Z', 'alice.smith@gmail.com', false), ('Carlos Mendes', '100001022', '1988-04-03T00:00:00Z', 'carlos.mendes@gmail.com', false);

-- ---------------------------------------------------------------------
-- EXEMPLOS COMENTADOS
-- ---------------------------------------------------------------------
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

-- ---------------------------------------------------------------------
-- FINALIZAÇÃO DO SCRIPT
-- ---------------------------------------------------------------------
\echo 'Script combinado executado com sucesso! ✅'