CREATE TABLE IF NOT EXISTS auditoria_estoque (
  id SERIAL PRIMARY KEY,
  game_id BIGINT NOT NULL,
  estoque_antigo INT,
  estoque_novo INT,
  operacao VARCHAR(20),
  qtd_movimentada INT,
  created_at TIMESTAMP DEFAULT NOW()
);
