DELIMITER $$
CREATE FUNCTION jogo_mais_vendido()
RETURNS TABLE (game_id BIGINT, titleGame VARCHAR(255), total_vendido BIGINT)
READS SQL DATA
BEGIN
    RETURN
    SELECT g.id, g.titleGame, SUM(a.qtd_movimentada) AS total_vendido
    FROM auditoria_estoque a
    JOIN games g ON g.id = a.game_id
    WHERE a.operacao = 'REDUCAO'
    GROUP BY g.id, g.titleGame
    ORDER BY total_vendido DESC
    LIMIT 1;
END$$
DELIMITER ;