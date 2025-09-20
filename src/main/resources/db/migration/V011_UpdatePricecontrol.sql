CREATE OR REPLACE PROCEDURE atualizar_preco(p_id INT, p_novo_preco NUMERIC)
AS $$
BEGIN
    UPDATE game
    SET preco = p_novo_preco
    WHERE id = p_id;
END;
$$ LANGUAGE plpgsql;
