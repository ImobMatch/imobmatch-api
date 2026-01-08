-- V2__create_phones.sql
-- Cria a tabela phones
CREATE TABLE phones (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    ddd VARCHAR(5) NOT NULL,
    number VARCHAR(20) NOT NULL,
    is_primary BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_user
        FOREIGN KEY(user_id)
            REFERENCES users(id)
            ON DELETE CASCADE
);
