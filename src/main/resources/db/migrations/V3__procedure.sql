/*CREATE OR REPLACE PROCEDURE create_order_with_burgers(
    p_username VARCHAR,
    p_burgersJson JSON
)
LANGUAGE plpgsql AS $$
DECLARE
v_user_id INT;
    v_order_id INT;
    burger JSON;
    burger_name VARCHAR;
    burger_quantity INT;
BEGIN
SELECT id INTO v_user_id
FROM Users
WHERE username = p_username;

IF v_user_id IS NULL THEN
        RAISE EXCEPTION 'User with username % not found', p_username;
END IF;

INSERT INTO Orders (user_id, address, employee_id)
VALUES (v_user_id, 'Some Address', NULL)
    RETURNING id INTO v_order_id;

FOR burger IN SELECT * FROM json_array_elements(p_burgersJson)
                                LOOPв
    burger_name := burger->>'name';
burger_quantity := (burger->>'quantity')::INT;

        IF burger_name IS NULL OR burger_quantity IS NULL THEN
            RAISE EXCEPTION 'Invalid burger data: %', burger;
END IF;

INSERT INTO Burgers (order_id, name, quantity)
VALUES (v_order_id, burger_name, burger_quantity);
END LOOP;
END
$$;*/



CREATE OR REPLACE PROCEDURE delete_order(p_order_id INT)
LANGUAGE plpgsql AS $$
BEGIN
DELETE FROM Orders
WHERE order_id = p_order_id;
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




CREATE OR REPLACE FUNCTION check_ingredient_availability()
RETURNS TRIGGER AS $$
DECLARE
required_quantity INT;
    available_quantity INT;
    ingredient_name VARCHAR;
    burger_name VARCHAR;
BEGIN
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
        IF available_quantity < required_quantity THEN
            RAISE EXCEPTION 'Недостаточно ингредиентов для бургера: % (Ингредиент: %, Требуется: %, Остаток: %)',
            burger_name, ingredient_name, required_quantity, available_quantity;
END IF;
END LOOP;


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




CREATE OR REPLACE FUNCTION get_user_orders(usernamee character varying)
RETURNS TABLE (
    id INTEGER,
    positions JSONB
) AS
$$
BEGIN
RETURN QUERY
SELECT o.order_id AS id,
       to_jsonb(
               JSON_AGG(
                       JSON_BUILD_OBJECT(
                               'burgerName', b.name,
                               'quantity', ol.count
                       )
               )
       ) AS positions
FROM orders o
         JOIN orderslist ol ON o.order_id = ol.order_id
         JOIN Burgers b ON ol.burger_id = b.burger_id
         JOIN users u ON u.user_id = o.user_id
WHERE u.username = usernamee
GROUP BY o.order_id
ORDER BY o.order_id;
END;
$$ LANGUAGE plpgsql;