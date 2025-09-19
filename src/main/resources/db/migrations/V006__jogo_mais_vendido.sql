CREATE OR REPLACE FUNCTION jogo_mais_vendido()
RETURNS TABLE (game_id BIGINT, titleGame TEXT, total_vendido BIGINT) AS $$
BEGIN
    IF to_regclass('public.auditoria_estoque') IS NULL THEN
        RAISE EXCEPTION 'Tabela auditoria_estoque não encontrada.';
    END IF;

    RETURN QUERY
    SELECT g.id,
           g."titleGame",
           SUM(a.qtd_movimentada)::bigint AS total_vendido
    FROM auditoria_estoque a
    JOIN games g ON g.id = a.game_id
    WHERE a.operacao = 'REDUCAO'
    GROUP BY g.id, g."titleGame"
    ORDER BY total_vendido DESC
    LIMIT 1;
END;
$$ LANGUAGE plpgsql;
