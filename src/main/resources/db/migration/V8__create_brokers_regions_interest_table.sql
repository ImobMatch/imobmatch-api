CREATE TABLE brokers_regions_interest (
    broker_id UUID NOT NULL,
    region VARCHAR(2) NOT NULL,

    CONSTRAINT pk_broker_region
        PRIMARY KEY (broker_id, region),

    CONSTRAINT fk_regions_broker
        FOREIGN KEY (broker_id)
        REFERENCES brokers(id)
        ON DELETE CASCADE
);
