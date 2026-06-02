CREATE TABLE auth_tokens (
  id SERIAL PRIMARY KEY,
  token VARCHAR(255) UNIQUE NOT NULL,
  expires_at TIMESTAMP NOT NULL,
  user_id INTEGER NOT NULL,

  CONSTRAINT fk_auth_token_user
    FOREIGN KEY (user_id)
    REFERENCES users(id)
);