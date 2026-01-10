-- V3__create_owners.sql
-- Creates the owners table with shared primary key with users
CREATE TABLE owners (
    user_id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,

    CONSTRAINT fk_owner_user
        FOREIGN KEY (user_id)
            REFERENCES users(id)
            ON DELETE CASCADE
);
