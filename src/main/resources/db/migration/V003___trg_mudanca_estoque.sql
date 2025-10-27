DELIMITER $$
CREATE TRIGGER trg_mudanca_estoque
AFTER UPDATE ON games
FOR EACH ROW
BEGIN
    DECLARE mud_operacao VARCHAR(10);
    DECLARE mud_qtd_movimentada INT;

    IF NEW.stock <> OLD.stock THEN
        SET mud_qtd_movimentada = ABS(NEW.stock - OLD.stock);

        IF NEW.stock > OLD.stock THEN
            SET mud_operacao = 'AUMENTO';
        ELSE
            SET mud_operacao = 'REDUCAO';
        END IF;

        INSERT INTO auditoria_estoque (game_id, estoque_antigo, estoque_novo, operacao, qtd_movimentada)
        VALUES (OLD.id, OLD.stock, NEW.stock, mud_operacao, mud_qtd_movimentada);
    END IF;
END$$
DELIMITER ;
