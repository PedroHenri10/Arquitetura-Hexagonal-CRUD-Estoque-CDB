DELIMITER $$
CREATE FUNCTION esta_abaixo_minimo(p_id INT, p_min INT)
RETURNS BOOLEAN
DETERMINISTIC
BEGIN
    DECLARE v_stock INT;

    SELECT stock INTO v_stock
    FROM games
    WHERE id = p_id;

    IF v_stock IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = CONCAT('Game não encontrado com id ', p_id);
    END IF;

    RETURN v_stock < p_min;
END$$
DELIMITER ;
