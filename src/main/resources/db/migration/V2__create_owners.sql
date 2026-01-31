-- V2__create_owners.sql
-- Creates the owners table with shared primary key with users
CREATE TABLE owners (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    whats_app_phone_number VARCHAR(20) NOT NULL,
    personal_phone_number VARCHAR(20),
    birth_date DATE NOT NULL,

    CONSTRAINT fk_owner_user
        FOREIGN KEY (id)
            REFERENCES users(id)

);