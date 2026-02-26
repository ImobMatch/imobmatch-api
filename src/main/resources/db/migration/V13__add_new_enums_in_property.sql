CREATE TYPE brazilian_state AS ENUM (
'AC','AL','AP','AM','BA','CE','DF','ES','GO','MA','MT','MS',
'MG','PA','PB','PR','PE','PI','RJ','RN','RS','RO','RR','SC',
'SP','SE','TO'
);

CREATE TYPE property_business_type AS ENUM (
'SALE',
'RENT',
'SALE_AND_RENT'
);

CREATE TYPE property_manager AS ENUM (
'OWNER',
'BROKER'
);

CREATE TYPE property_purpose AS ENUM (
'RESIDENTIAL',
'COMMERCIAL',
'INDUSTRIAL',
'RURAL',
'SEASONAL',
'CORPORATE'
);

CREATE TYPE property_type AS ENUM (
'HOUSE',
'APARTMENT',
'LAND',
'LOT',
'PENTHOUSE',
'STUDIO_APARTMENT',
'LOFT',
'TOWNHOUSE',
'STUDIO',
'VILLAGE',
'ROOM',
'SHOP',
'SALON',
'POINT',
'WAREHOUSE',
'SLAB',
'AREA',
'HOTEL',
'INN',
'BUILDING',
'RESORT',
'HALL',
'SITE',
'SMALL_FARM',
'FARM',
'RANCH',
'HORSE_FARM'
);

ALTER TABLE address
    ALTER COLUMN state TYPE brazilian_state
    USING state::brazilian_state;

ALTER TABLE property
    ALTER COLUMN purpose TYPE property_purpose
        USING purpose::property_purpose,

    ALTER COLUMN business_type TYPE property_business_type
        USING business_type::property_business_type,

    ALTER COLUMN type TYPE property_type
        USING type::property_type,

    ALTER COLUMN managed_by TYPE property_manager
        USING managed_by::property_manager;

ALTER TABLE brokers_property_types
    ALTER COLUMN property_type TYPE property_type
    USING property_type::text::property_type;

DROP TYPE property_type_enum;