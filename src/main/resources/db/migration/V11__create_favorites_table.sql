CREATE TABLE favorites (
    id UUID PRIMARY KEY,
    broker_id UUID NOT NULL,
    property_id UUID NOT NULL,
    favorited_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_favorite_broker
        FOREIGN KEY (broker_id)
        REFERENCES broker (id)
        ON DELETE CASCADE,

    CONSTRAINT fk_favorite_property
        FOREIGN KEY (property_id)
        REFERENCES property (id)
        ON DELETE CASCADE,

    CONSTRAINT uk_favorite_broker_property
        UNIQUE (broker_id, property_id)
);
