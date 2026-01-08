-- Cria o tipo enum para os papéis
CREATE TYPE role_type AS ENUM ('ADMIN', 'OWNER', 'BROKER');

-- Cria a tabela users
CREATE TABLE users (
       id UUID PRIMARY KEY,
       email VARCHAR(255) NOT NULL UNIQUE,
       role role_type NOT NULL,
       password VARCHAR(255) NOT NULL,
       is_email_verified BOOLEAN NOT NULL DEFAULT TRUE,
       created_at TIMESTAMP NOT NULL DEFAULT now(),
       updated_at TIMESTAMP NOT NULL DEFAULT now()
);
