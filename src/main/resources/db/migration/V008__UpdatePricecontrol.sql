DELIMITER $$
CREATE PROCEDURE atualizar_preco(IN p_id INT, IN p_novo_preco DECIMAL(10,2))
BEGIN
    UPDATE games
    SET preco = p_novo_preco
    WHERE id = p_id;
END$$
DELIMITER ;
