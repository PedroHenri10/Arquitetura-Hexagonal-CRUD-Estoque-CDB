CREATE OR REPLACE FUNCTION mudanca_de_estoque()
RETURNS TRIGGER AS $$
DECLARE
    mud_operacao VARCHAR(10);
    mud_qtd_movimentada INT;
BEGIN

    IF(TG_OP = "UPDATE" AND NEW.stock <> OLD.stock) THEN

    mud-qtd_movimentada := abs(NEW.stock - OLD.stock);

        IF NEW.stock > OLD.stock THEN
            mud_operacao := 'AUMENTO';
        ELSE
            MUD_OPERACAO := 'REDUCAO';
        END IF;

        INSERT INTO auditoria_estoque(game_id, estoque_antigo, estoque_novo, operacao, qtd_movimentada)
        values (OLD.id, OLD.stock, NEW.stock, MUD_operacao, mud_qtd_movimentada);
        END IF;

        RETURN NEW;
        END;
        $$ LANGUAGE plpgsql;