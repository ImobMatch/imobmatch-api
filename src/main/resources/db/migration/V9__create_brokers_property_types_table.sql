CREATE TYPE property_type_enum AS ENUM ('HOUSE', 'APARTMENT', 'LAND', 'LOT', 'PENTHOUSE', 
'STUDIO_APARTMENT', 'LOFT', 'TOWNHOUSE', 'STUDIO', 
'VILLAGE', 'ROOM', 'SHOP', 'SALON', 'POINT', 'WAREHOUSE', 
'SLAB', 'AREA', 'HOTEL', 'INN', 'BUILDING', 'RESORT', 
'HALL', 'SITE', 'SMALL_FARM', 'FARM', 'RANCH', 'HORSE_FARM');

CREATE TABLE brokers_property_types (
    broker_id UUID NOT NULL,
    property_type property_type_enum NOT NULL,

    CONSTRAINT pk_broker_property_type
        PRIMARY KEY (broker_id, property_type),

    CONSTRAINT fk_property_types_broker
        FOREIGN KEY (broker_id)
        REFERENCES brokers(id)
        ON DELETE CASCADE
);
