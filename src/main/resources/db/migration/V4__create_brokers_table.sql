CREATE TYPE business_type_enum AS ENUM ('SALE', 'RENT', 'SALE_AND_RENT');

CREATE TYPE account_status_enum AS ENUM ('ACTIVE', 'BLOCKED', 'PENDING');

CREATE TABLE brokers (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    creci VARCHAR(255) NOT NULL UNIQUE,
    cpf CHAR(11) NOT NULL UNIQUE,
    business_type business_type_enum,
    birth_date DATE NOT NULL,
    whats_app_phone_number VARCHAR(20) NOT NULL,
    personal_phone_number VARCHAR(20),
    account_status account_status_enum NOT NULL,
    CONSTRAINT fk_user
        FOREIGN KEY(id)
            REFERENCES users(id)
            ON DELETE CASCADE
);
