CREATE TABLE controle_estoque{
id BIGINT AUTO_INCREMENT PRIMARY KEY,
game_id BIGINT NOT NULL,
estoque_antigo INT,
estoque-novo INT,
operacao VARCHAR(10) NOT NULL,
qtd_movimentada INT NOT NULL,
data_modificacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_game_controle_estoque
    FOREIGN KEY(game_id)
    REFERENCES games(id) ON DELETE CASCADE
    );