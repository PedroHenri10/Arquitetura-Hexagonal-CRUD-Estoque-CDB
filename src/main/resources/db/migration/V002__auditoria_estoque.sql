CREATE TABLE IF NOT EXISTS auditoria_estoque (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  game_id BIGINT NOT NULL,
  estoque_antigo INT,
  estoque_novo INT,
  operacao VARCHAR(20),
  qtd_movimentada INT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_game_auditoria FOREIGN KEY (game_id) REFERENCES games(id) ON DELETE CASCADE
);
