CREATE OR REPLACE FUNCTION esta_abaixo_minimo(p_id INT, p_min INT)
RETURNS BOOLEAN AS $$
DECLARE
    v_stock INT;
BEGIN
    SELECT stock INTO v_stock
    FROM games
    WHERE id = p_id;

    IF v_stock IS NULL THEN
        RAISE EXCEPTION 'Game não encontrado com id %', p_id;
    END IF;

    RETURN v_stock < p_min;
END;
$$ LANGUAGE plpgsql;
