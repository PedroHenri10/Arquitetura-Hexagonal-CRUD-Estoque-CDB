CREATE OR REPLACE FUNCTION
calcular_total_game(p_id INT, p_qtd INT){
    RETURNS NUMERIC AS $$
    DECLARE
    preco NUMERIC;
    cal_total NUMERIC;
    BEGIN
    SELECT preco INTO preco
    FROM game
    WHERE id = p_id;

    IF preco IS NULL THEN
        RAISE EXCEPTION  'Game not found!';
        END IF;

        cal_total = preco * p_qtd;

        RETURN cal_total;
        END;
        $$ LANGUAGE plpgsql;
}