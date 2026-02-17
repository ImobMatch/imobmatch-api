--create view table used in broker recommendation

CREATE TABLE broker_property_view_history (
    id UUID NOT NULL,
    viewed_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    broker_id UUID NOT NULL,
    property_id UUID NOT NULL,

    CONSTRAINT pk_broker_property_view_history PRIMARY KEY (id)
);

ALTER TABLE broker_property_view_history
    ADD CONSTRAINT fk_broker_view_history_on_broker
        FOREIGN KEY (broker_id) REFERENCES brokers (id);

ALTER TABLE broker_property_view_history
    ADD CONSTRAINT fk_broker_view_history_on_property
        FOREIGN KEY (property_id) REFERENCES property (id);

CREATE INDEX idx_history_broker_date ON broker_property_view_history (broker_id, viewed_at DESC);