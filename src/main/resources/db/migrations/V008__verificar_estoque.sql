Create or replace function verificar_estoque (g_id INT , g_qtd int)
returns boolean AS $$
DECLARE
    v_qtd int;
BEGIN
    SELECT quantidade INTO v_qtd FROM game Where id = g_id;

    if v_qtd IS NULL THEN
        RAISE EXCEPTION 'Game not found', g_id;
        END IF;

        RETURN v_id >= g_id;

    end;
    $$ LANGUAGE plpgsql;