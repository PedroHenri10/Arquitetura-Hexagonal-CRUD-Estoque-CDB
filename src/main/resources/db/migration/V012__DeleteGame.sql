DELIMITER $$
CREATE PROCEDURE excluir_game(IN g_id INT)
BEGIN
    DELETE FROM games
    WHERE id = g_id;
END$$
DELIMITER ;