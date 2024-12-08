--DROP TABLE IF EXISTS databasechangelog;
--DROP TABLE IF EXISTS databasechangeloglock;

/*DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS Usersroles;
DROP TABLE IF EXISTS Ingredients;
DROP TABLE IF EXISTS Recepts;
DROP TABLE IF EXISTS Burgers;
DROP TABLE IF EXISTS Employees;
DROP TABLE IF EXISTS Orders;
DROP TABLE IF EXISTS OrdersList;*/
-- Создание таблицы users
CREATE TABLE IF NOT EXISTS Users
(
    user_id   SERIAL PRIMARY KEY,
    username  VARCHAR(255) UNIQUE,
    password  VARCHAR(255),
    name      VARCHAR(255),
    telephone VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS Roles
(
    role_id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);


CREATE TABLE IF NOT EXISTS UsersRoles
(
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES Users(user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    role_id INT REFERENCES Roles(role_id) ON DELETE CASCADE ON UPDATE CASCADE DEFAULT 2
);


-- Создание таблицы redactors
CREATE TABLE IF NOT EXISTS Employees
(
    employee_id  SERIAL PRIMARY KEY,
    name         VARCHAR(255),
    surname      VARCHAR(255),
    patronymic   VARCHAR(255),
    position     VARCHAR(255),
    phone_number VARCHAR(20)
);


-- Создание таблицы orders
CREATE TABLE IF NOT EXISTS Orders
(
    order_id    SERIAL PRIMARY KEY,
    datetime    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    adress      VARCHAR(255) DEFAULT 'Не указан',
    employee_id INT,
    user_id   INT,
    FOREIGN KEY (employee_id) REFERENCES Employees(employee_id) ON DELETE SET NULL ON UPDATE CASCADE,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE SET NULL ON UPDATE CASCADE
);

-- Создание таблицы Burger
CREATE TABLE IF NOT EXISTS Burgers
(
    burger_id SERIAL PRIMARY KEY,
    name      VARCHAR(255),
    cost      DECIMAL(10, 2)
);


-- Создание таблицы OrdersList
CREATE TABLE IF NOT EXISTS OrdersList
(
    id SERIAL PRIMARY KEY,
    order_id  INT,
    burger_id INT,
    count     INT,
    FOREIGN KEY (order_id) REFERENCES Orders (order_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (burger_id) REFERENCES Burgers (burger_id) ON DELETE CASCADE ON UPDATE CASCADE
    );


-- Создание таблицы Ingredient
CREATE TABLE IF NOT EXISTS Ingredients
(
    ingredient_id SERIAL PRIMARY KEY,
    name          VARCHAR(255),
    residue       INT
);


-- Создание таблицы Recept
CREATE TABLE IF NOT EXISTS Recepts
(
    burger_id     INT,
    ingredient_id INT,
    quantity      INT,
    PRIMARY KEY (burger_id, ingredient_id),
    FOREIGN KEY (burger_id) REFERENCES Burgers (burger_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (ingredient_id) REFERENCES Ingredients (ingredient_id) ON DELETE RESTRICT ON UPDATE CASCADE
);
