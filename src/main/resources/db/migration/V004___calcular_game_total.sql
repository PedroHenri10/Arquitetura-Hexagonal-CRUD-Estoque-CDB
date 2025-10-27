DELIMITER $$
CREATE FUNCTION calcular_total_game(p_id INT, p_qtd INT)
RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    DECLARE preco DECIMAL(10,2);
    DECLARE cal_total DECIMAL(10,2);

    SELECT preco INTO preco FROM games WHERE id = p_id;

    IF preco IS NULL THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Game não encontrado!';
    END IF;

    SET cal_total = preco * p_qtd;
    RETURN cal_total;
END$$
DELIMITER ;
