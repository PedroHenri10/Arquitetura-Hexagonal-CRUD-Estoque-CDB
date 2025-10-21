DELIMITER $$
CREATE PROCEDURE registrar_venda(IN v_id INT, IN v_qtd INT)
BEGIN
    DECLARE g_qtd INT;

    SELECT stock INTO g_qtd FROM games WHERE id = v_id;

    IF g_qtd IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Game não encontrado!';
    END IF;

    IF g_qtd < v_qtd THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Estoque insuficiente!';
    END IF;

    UPDATE games
    SET stock = stock - v_qtd
    WHERE id = v_id;
END$$
DELIMITER ;