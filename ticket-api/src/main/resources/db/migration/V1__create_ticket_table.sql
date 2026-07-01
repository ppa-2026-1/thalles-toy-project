CREATE TABLE ticket (
  id SERIAL PRIMARY KEY,
  acao VARCHAR(255) NOT NULL,
  objeto VARCHAR(255) NOT NULL,
  detalhes TEXT,
  criador VARCHAR(255) NOT NULL,
  destinatario VARCHAR(255),
  responsavel VARCHAR(255),
  status VARCHAR(50) NOT NULL,
  motivo TEXT,
  observadores TEXT,
  created_at TIMESTAMP,
  updated_at TIMESTAMP
);