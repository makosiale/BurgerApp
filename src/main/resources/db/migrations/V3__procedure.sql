-- Процедуры для таблицы Users
CREATE OR REPLACE PROCEDURE insert_user(p_username VARCHAR, p_password VARCHAR, p_name VARCHAR, p_telephone VARCHAR)
LANGUAGE plpgsql AS $$
BEGIN
INSERT INTO Users (username, password, name, telephone)
VALUES (p_username, p_password, p_name, p_telephone);
END
$$;

CREATE OR REPLACE PROCEDURE update_user(p_user_id INT, p_username VARCHAR, p_password VARCHAR, p_name VARCHAR, p_telephone VARCHAR)
LANGUAGE plpgsql AS $$
BEGIN
UPDATE Users
SET username  = p_username,
    password  = p_password,
    name      = p_name,
    telephone = p_telephone
WHERE user_id = p_user_id;
END
$$;

CREATE OR REPLACE PROCEDURE delete_user(p_user_id INT)
LANGUAGE plpgsql AS $$
BEGIN
DELETE FROM Users
WHERE user_id = p_user_id;
END
$$;

-- Процедуры для таблицы Roles
CREATE OR REPLACE PROCEDURE insert_role(p_name VARCHAR)
LANGUAGE plpgsql AS $$
BEGIN
INSERT INTO Roles (name)
VALUES (p_name);
END
$$;

CREATE OR REPLACE PROCEDURE update_role(p_role_id INT, p_name VARCHAR)
LANGUAGE plpgsql AS $$
BEGIN
UPDATE Roles
SET name = p_name
WHERE role_id = p_role_id;
END
$$;

CREATE OR REPLACE PROCEDURE delete_role(p_role_id INT)
LANGUAGE plpgsql AS $$
BEGIN
DELETE FROM Roles
WHERE role_id = p_role_id;
END
$$;

-- Процедуры для таблицы UsersRoles
CREATE OR REPLACE PROCEDURE insert_user_role(p_user_id INT, p_role_id INT)
LANGUAGE plpgsql AS $$
BEGIN
INSERT INTO UsersRoles (user_id, role_id)
VALUES (p_user_id, p_role_id);
END
$$;

CREATE OR REPLACE PROCEDURE delete_user_role(p_id INT)
LANGUAGE plpgsql AS $$
BEGIN
DELETE FROM UsersRoles
WHERE id = p_id;
END
$$;

-- Процедуры для таблицы Employees
CREATE OR REPLACE PROCEDURE insert_employee(p_name VARCHAR, p_surname VARCHAR, p_patronymic VARCHAR, p_position VARCHAR, p_phone_number VARCHAR)
LANGUAGE plpgsql AS $$
BEGIN
INSERT INTO Employees (name, surname, patronymic, position, phone_number)
VALUES (p_name, p_surname, p_patronymic, p_position, p_phone_number);
END
$$;

CREATE OR REPLACE PROCEDURE update_employee(p_employee_id INT, p_name VARCHAR, p_surname VARCHAR, p_patronymic VARCHAR, p_position VARCHAR, p_phone_number VARCHAR)
LANGUAGE plpgsql AS $$
BEGIN
UPDATE Employees
SET name         = p_name,
    surname      = p_surname,
    patronymic   = p_patronymic,
    position     = p_position,
    phone_number = p_phone_number
WHERE employee_id = p_employee_id;
END
$$;

CREATE OR REPLACE PROCEDURE delete_employee(p_employee_id INT)
LANGUAGE plpgsql AS $$
BEGIN
DELETE FROM Employees
WHERE employee_id = p_employee_id;
END
$$;

-- Процедуры для таблицы Orders
CREATE OR REPLACE PROCEDURE insert_order(p_adress VARCHAR, p_employee_id INT, p_user_id INT)
LANGUAGE plpgsql AS $$
BEGIN
INSERT INTO Orders (adress, employee_id, user_id)
VALUES (p_adress, p_employee_id, p_user_id);
END
$$;

CREATE OR REPLACE PROCEDURE update_order(p_order_id INT, p_adress VARCHAR, p_employee_id INT, p_user_id INT)
LANGUAGE plpgsql AS $$
BEGIN
UPDATE Orders
SET adress      = p_adress,
    employee_id = p_employee_id,
    user_id     = p_user_id
WHERE order_id = p_order_id;
END
$$;

CREATE OR REPLACE PROCEDURE delete_order(p_order_id INT)
LANGUAGE plpgsql AS $$
BEGIN
DELETE FROM Orders
WHERE order_id = p_order_id;
END
$$;

-- Процедуры для таблицы Burgers
CREATE OR REPLACE PROCEDURE insert_burger(p_name VARCHAR, p_cost DECIMAL)
LANGUAGE plpgsql AS $$
BEGIN
INSERT INTO Burgers (name, cost)
VALUES (p_name, p_cost);
END
$$;

CREATE OR REPLACE PROCEDURE update_burger(p_burger_id INT, p_name VARCHAR, p_cost DECIMAL)
LANGUAGE plpgsql AS $$
BEGIN
UPDATE Burgers
SET name = p_name,
    cost = p_cost
WHERE burger_id = p_burger_id;
END
$$;

CREATE OR REPLACE PROCEDURE delete_burger(p_burger_id INT)
LANGUAGE plpgsql AS $$
BEGIN
DELETE FROM Burgers
WHERE burger_id = p_burger_id;
END
$$;

-- Процедуры для таблицы OrdersList
CREATE OR REPLACE PROCEDURE insert_order_list(p_order_id INT, p_burger_id INT, p_count INT)
LANGUAGE plpgsql AS $$
BEGIN
INSERT INTO OrdersList (order_id, burger_id, count)
VALUES (p_order_id, p_burger_id, p_count);
END
$$;

CREATE OR REPLACE PROCEDURE update_order_list(p_id INT, p_order_id INT, p_burger_id INT, p_count INT)
LANGUAGE plpgsql AS $$
BEGIN
UPDATE OrdersList
SET order_id  = p_order_id,
    burger_id = p_burger_id,
    count     = p_count
WHERE id = p_id;
END
$$;

CREATE OR REPLACE PROCEDURE delete_order_list(p_id INT)
LANGUAGE plpgsql AS $$
BEGIN
DELETE FROM OrdersList
WHERE id = p_id;
END
$$;

-- Процедуры для таблицы Ingredients
CREATE OR REPLACE PROCEDURE insert_ingredient(p_name VARCHAR, p_residue INT)
LANGUAGE plpgsql AS $$
BEGIN
INSERT INTO Ingredients (name, residue)
VALUES (p_name, p_residue);
END
$$;

CREATE OR REPLACE PROCEDURE update_ingredient_residue(p_ingredient_id INT, p_residue INT)
LANGUAGE plpgsql AS $$
BEGIN
UPDATE Ingredients
SET residue = p_residue+residue
WHERE ingredient_id = p_ingredient_id;
END
$$;

CREATE OR REPLACE PROCEDURE delete_ingredient(p_ingredient_id INT)
LANGUAGE plpgsql AS $$
BEGIN
DELETE FROM Ingredients
WHERE ingredient_id = p_ingredient_id;
END
$$;

-- Процедуры для таблицы Recepts
CREATE OR REPLACE PROCEDURE insert_recept(p_burger_id INT, p_ingredient_id INT, p_quantity INT)
LANGUAGE plpgsql AS $$
BEGIN
INSERT INTO Recepts (burger_id, ingredient_id, quantity)
VALUES (p_burger_id, p_ingredient_id, p_quantity);
END
$$;

CREATE OR REPLACE PROCEDURE update_recept(p_burger_id INT, p_ingredient_id INT, p_quantity INT)
LANGUAGE plpgsql AS $$
BEGIN
UPDATE Recepts
SET quantity = p_quantity
WHERE burger_id = p_burger_id
  AND ingredient_id = p_ingredient_id;
END
$$;

CREATE OR REPLACE PROCEDURE delete_recept(p_burger_id INT, p_ingredient_id INT)
LANGUAGE plpgsql AS $$
BEGIN
DELETE FROM Recepts
WHERE burger_id = p_burger_id
  AND ingredient_id = p_ingredient_id;
END
$$;

CREATE VIEW order_summary AS
SELECT
    o.order_id,
    u.username,
    CONCAT( e.surname,' ',e.name,  ' ', e.patronymic) as employee,
    JSON_AGG(
            JSON_BUILD_OBJECT(
                    'name', b.name,
                    'quantity', ol.count
            )
    ) AS burgers,
    SUM(ol.count * b.cost) AS total_price
FROM Orders o
         JOIN OrdersList ol ON o.order_id = ol.order_id
         JOIN Burgers b ON ol.burger_id = b.burger_id
         JOIN users u ON u.user_id = o.user_id
         LEFT JOIN employees e ON e.employee_id = o.employee_id
GROUP BY o.order_id,u.username,e.name,e.surname,e.patronymic;

/*CREATE VIEW order_summary AS
SELECT
    o.order_id,
    u.username,
    CONCAT( e.surname,' ',e.name,  ' ', e.patronymic) AS employee,
    b.name as name,
    ol.count as quantity,
    b.cost as price
FROM Orders o
         JOIN OrdersList ol ON o.order_id = ol.order_id
         JOIN Burgers b ON ol.burger_id = b.burger_id
         JOIN users u ON u.user_id = o.user_id
         LEFT JOIN employees e ON e.employee_id = o.employee_id;*/


CREATE OR REPLACE FUNCTION getTotalRevenue()
RETURNS INT AS $$
DECLARE
total_revenue int;
BEGIN
SELECT SUM(b.cost*ol.count) INTO total_revenue
FROM burgers b
         JOIN orderslist ol on ol.burger_id = b.burger_id;
RETURN total_revenue;
END;
$$ LANGUAGE plpgsql;


-- Создание функции для проверки остатков ингредиентов
CREATE OR REPLACE FUNCTION check_ingredient_availability()
RETURNS TRIGGER AS $$
DECLARE
required_quantity INT;
    available_quantity INT;
    ingredient_name VARCHAR;
    burger_name VARCHAR;
BEGIN
    -- Перебираем все ингредиенты рецепта для каждого бургера в заказе
FOR ingredient_name, required_quantity, available_quantity, burger_name IN
SELECT i.name AS ingredient_name,
       r.quantity * NEW.count AS required_quantity,
       i.residue AS available_quantity,
       b.name AS burger_name
FROM Recepts r
         JOIN Ingredients i ON r.ingredient_id = i.ingredient_id
         JOIN Burgers b ON r.burger_id = b.burger_id
WHERE r.burger_id = NEW.burger_id
    LOOP
        -- Если остатка недостаточно, выбрасываем исключение
        IF available_quantity < required_quantity THEN
            RAISE EXCEPTION 'Недостаточно ингредиентов для бургера: % (Ингредиент: %, Требуется: %, Остаток: %)',
            burger_name, ingredient_name, required_quantity, available_quantity;
END IF;
END LOOP;

    -- Уменьшаем остатки ингредиентов, если проверка прошла успешно
UPDATE Ingredients
SET residue = residue - r.quantity * NEW.count
    FROM Recepts r
WHERE r.ingredient_id = Ingredients.ingredient_id
  AND r.burger_id = NEW.burger_id;

RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Создание триггера
CREATE TRIGGER before_order_insert
    BEFORE INSERT ON OrdersList
    FOR EACH ROW
    EXECUTE FUNCTION check_ingredient_availability();


/*CREATE OR REPLACE PROCEDURE create_order_with_burgers(
    p_user_name VARCHAR,
    p_burgers_text VARCHAR
)
LANGUAGE plpgsql AS $$
DECLARE
v_order_id INT;
    v_user_id INT;
    burger RECORD;
    p_burgers JSON;
BEGIN
    -- Преобразуем текстовый параметр в JSON
    p_burgers := p_burgers_text::JSON;

    -- Получаем user_id
SELECT user_id
INTO v_user_id
FROM users
WHERE username = p_user_name;

-- Вставляем заказ в таблицу orders
INSERT INTO Orders(user_id)
VALUES (v_user_id)
    RETURNING order_id INTO v_order_id;

-- Обрабатываем JSON с бургером
FOR burger IN SELECT * FROM JSON_TO_RECORDSET(p_burgers) AS x(burgerId INT, quantity INT) LOOP
              INSERT INTO OrdersList(order_id, burger_id, count)
              VALUES (v_order_id, burger.burgerId, burger.quantity);
END LOOP;
END
$$;*/