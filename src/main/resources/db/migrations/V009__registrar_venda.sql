create or replace procedure registrar_venda(v_id INT, v_qtd INT)
AS $$
DECLARE
    g_QTD INT;
BEGIN
    SELECT quantida INTO g_qtd FROM game WHERE id = v_id;

    if g_qtd IS NULL THEN
        RAISE EXCEPTION "Game not found";
    end if;

    if g_qtd < v_qtd then
        RAISE EXCEPTION "ESTOQUE INSUUFIIENTE"
    END IF;

        UPDATE game
        SET quantidade = quantidade - g_qtd
        WHERE id = v_qtd;
    END;

    $$ LANGUAGE plpgsql;