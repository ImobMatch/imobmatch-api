-- Cria o tipo enum para os tipos de propiedades.
CREATE TYPE property_type_enum AS ENUM ('APARTMENT', 'HOUSE', 'LOFT', 'PENTHOUSE', 'STUDIO');

-- Cria o tipo enum para os tipos de negócios.
CREATE TYPE business_type_enum AS ENUM ('SALE', 'RENTAL', 'LEASE');

-- Cria o tipo enum para os status da conta.
CREATE TYPE account_status_enum AS ENUM ('VALID', 'INVALID', 'PENDING');

-- Cria a tabela brokers
CREATE TABLE brokers (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    creci VARCHAR(255) NOT NULL UNIQUE,
    cpf CHAR(11) NOT NULL UNIQUE,
    region_interest VARCHAR(255),
    property_type property_type_enum, -- New Table?
    operation_city VARCHAR(255),
    business_type business_type_enum, -- New Table?
    birth_date DATE NOT NULL,
    account_status account_status_enum NOT NULL,
    CONSTRAINT fk_user
        FOREIGN KEY(id)
            REFERENCES users(id)
            ON DELETE CASCADE
);
