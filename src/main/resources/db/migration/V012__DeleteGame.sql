CREATE OR REPLACE PROCEDURE excluir_game(g_id INT)
AS $$
BEGIN
    DELETE FROM game
    WHERE id = g_id;
END;
$$ LANGUAGE plpgsql;
