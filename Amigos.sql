drop database if exists Tabacco;
create database Tabacco;
use Tabacco;

CREATE TABLE Clients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(100) UNIQUE,
    added_date timestamp NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Admin (
    id INT AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(100),
    lastname VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(100) UNIQUE
);

CREATE TABLE Products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    brand VARCHAR(100),
    category VARCHAR(100),
    name VARCHAR(100),
    description TEXT,
    price NUMERIC,
    min_price NUMERIC,
    quantity INT
);

CREATE TABLE Orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_no int(11) DEFAULT NULL,
    sendToClient INT,
    sendToProduct INT,
    order_status varchar(100) DEFAULT NULL,
    order_date timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    payment_mode varchar(100) DEFAULT NULL,
    payment_id int(11) DEFAULT NULL,
    FOREIGN KEY (sendToClient) REFERENCES Clients(id),
    FOREIGN KEY (sendToProduct) REFERENCES Products(id)
);

CREATE TABLE cart (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    quantity INT,
    total_price varchar(200) DEFAULT NULL,
    client_id INT,
    product_id INT,
    FOREIGN KEY (client_id) REFERENCES Clients(id),
    FOREIGN KEY (product_id) REFERENCES Products(id)
);

insert into Products (brand, category, name, description, price, min_price, quantity)
values ('RELX', 'Vape', 'Lush Red', 'Lush Red е микс от банан и ягода. Електронна цигара с никотин 2мл.', 12.99, 8.49, 31),
	   ('Holster', 'Vape', 'Passion Grapefruit', 'Passion Grapefruit е с вкус на грейфрут. Електронна цигара с никотин 20мл.', 20, 14.99, 31),
       ('ELF BAR', 'Vape', 'Elfbull Ice', 'Elfbull Ice е с вкус на тонизираща енергийна напитка. Електронна цигара с никотин 2мл.', 13.99, 9.49, 31),
       ('Holster', 'Tabacco', 'Bono', 'Сладък лешников крем, който не се нуждае от нищо друго за да се насладите на неповторимо сладко наргиле изживяване. Съдържанието е 20гр.', 9.90, 6.99, 31),
       ('HOOKAIN', 'Tabacco', 'Bert ehre', 'Естествената киселинност и леко тръпчив вкус се срещат със сладък ягодов сироп и се допълват от нотка на бърбън ванилия. Въпреки това, ванилията трудно може да се усети сама и по същество поддържа сладкия вкус на нашия вкусен ягодов сироп. Съдържанието е 20гр.', 74, 55, 31),
       ('Lirra Dark', 'Tabacco', 'Pineapp', 'Вкус на сочен и сладък ананас, който усещате с всяка дръпка. Неповторимо усещане. Съдържанието е 25гр.', 13.90, 9, 31),
       ('Contraband', 'Shisha', 'Pablo Escobar Silver/Black', 'Contraband Pablo Escobar е наргиле подобаващо на един истински крал. Моделът е изработен в Германия от неръждаема стомана и е завършен с обвивка от епоксидна смола. Застоелият дим в наргилето се издухва от горната част, което добавя допълнителен ефект при пушене. Сетът е допълнен с ваза от кристално стъкло, за да подчертае визията на наргилето. Височината на наргилето е 38см., а тегло е 5кг.', 330, 270, 31),
       ('Aladin', 'Shisha', 'Alux 380', 'Aladin Alux 380 са компактни наргилета изработени от алуминий. Въпреки малкия си размер, наргилетата притежават изключително добри качества и гарантират много и професионални сесии. Произведен е в Китай. Височината на наргилето е 35см., а тегло е 3 кг.', 130, 90, 31),
       ('DSH', 'Shisha', 'Exclusive Coral Red', 'Произведено в Русия. DSH Exclusive е изработено от неръждаема стомана в комбинация със стабилизирано дърво и епоксидна смола. Тази комбинация придава на наргилето високо качество, както по отношение на покритието, така и по отношение на здравината и визията му. Сетът се предлага с накрайник, с еднотипна на основата визия, изработен от същите висококачествени материали. Височината на наргилето е 58см., а тежестта е едва 3,6кг.', 640, 570, 31),
       ('DOPE', 'Pouch', 'Freeze', 'Никотиновите паучове са иновативен метод за подтискане на никотиновия глад и задоволяване на нуждите на хората без да е необходимо да пушат цигари и продукти свързани с дим. Теглото е 0.02кг. Произходът е от Чехия. Силата на тютюна е силно. Грамажът е 50г.', 11.49, 6.99, 31),
       ('DOPE', 'Pouch', 'Lime Smash', 'Никотиновите паучове са иновативен метод за подтискане на никотиновия глад и задоволяване на нуждите на хората без да е необходимо да пушат цигари и продукти свързани с дим. Теглото е 0.02кг. Произходът е от Чехия. Силата на тютюна е средно. Грамажът е 28.5г.', 11.49, 6.99, 31),
       ('DOPE', 'Pouch', 'Melon', 'Никотиновите паучове са иновативен метод за подтискане на никотиновия глад и задоволяване на нуждите на хората без да е необходимо да пушат цигари и продукти свързани с дим. Теглото е 0.02 kg. Произходът е от Чехия. Силата на тютюна е средно. Грамажът е 16г.', 11.49, 6.99, 31),
       ('One Nation', 'Consumable', 'Кашон въглени', 'Висококалорични въглени, гарантиращи постоянна температура. Размерът на един е 26мм. Предлагат се в кутия от 1кг. Произходът е от Индонезия.', 200, 160, 31),
       ('KS', 'Consumable', 'Персонален мундщук', 'Персонален мундщук, изработен от силикон. Напълно универсален и подходящ за всички накрайници за наргиле. Произходът е от Китай.', 15, 10, 31),
       ('HOOKAIN', 'Consumable', 'Фолио Koka Foil', 'Алуминиевото фолио с най-добро поведение при пушене. Ролката KOKA FOIL е дълга 25 м и широка 14 см и ви предлага възможността да режете перфектно фолиото си с вграден трион и да не губите нищо. Дебелината на фолиото е над средната 40 микрона и точно затова е толкова устойчиво. Произходът е от Германия.', 20, 16, 31);
       
