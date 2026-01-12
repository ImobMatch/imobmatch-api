-- V3__create_owners.sql
-- Creates the owners table with shared primary key with users
CREATE TABLE owners (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    phone_ddd VARCHAR(3) NOT NULL,
    phone_number VARCHAR(9) NOT NULL,

    CONSTRAINT fk_owner_user
        FOREIGN KEY (id)
            REFERENCES users(id)

);