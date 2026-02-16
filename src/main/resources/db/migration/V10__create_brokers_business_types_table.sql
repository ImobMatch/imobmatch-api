CREATE TYPE business_type_enum AS ENUM ('SALE', 'RENT', 'SALE_AND_RENT');


CREATE TABLE brokers_business_types (
    broker_id UUID NOT NULL,
    business_type business_type_enum NOT NULL,

    CONSTRAINT pk_broker_business_type
        PRIMARY KEY (broker_id, business_type),

    CONSTRAINT fk_business_types_broker
        FOREIGN KEY (broker_id)
        REFERENCES brokers(id)
        ON DELETE CASCADE
);
