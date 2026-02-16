CREATE TABLE address (
     id UUID NOT NULL,
     street VARCHAR(255) NOT NULL,
     number INTEGER,
     complement VARCHAR(255),
     neighborhood VARCHAR(255) NOT NULL,
     city VARCHAR(255) NOT NULL,
     state VARCHAR(20) NOT NULL,
     zip_code VARCHAR(20) NOT NULL,
     reference_point VARCHAR(255),
     CONSTRAINT pk_address PRIMARY KEY (id)
);

CREATE TABLE property_characteristic (
     id UUID NOT NULL,
     description VARCHAR(1200),
     area NUMERIC(19, 2) NOT NULL,
     num_bedrooms SMALLINT,
     num_suites SMALLINT,
     num_bathrooms SMALLINT,
     num_garage_spaces SMALLINT,
     num_living_rooms SMALLINT,
     num_kitchens SMALLINT,

     has_laundry BOOLEAN,
     has_closet BOOLEAN,
     has_office BOOLEAN,
     has_balcony BOOLEAN,
     has_terrace BOOLEAN,
     has_wine_cellar BOOLEAN,
     has_pantry BOOLEAN,
     has_yard BOOLEAN,
     has_garden BOOLEAN,
     has_barbecue BOOLEAN,
     has_storage BOOLEAN,

     CONSTRAINT pk_property_characteristic PRIMARY KEY (id)
);

CREATE TABLE condominium (
     id UUID NOT NULL,
     name VARCHAR(255) NOT NULL,
     price NUMERIC(19, 2),
     CONSTRAINT pk_condominium PRIMARY KEY (id)
);

CREATE TABLE property (
    id UUID NOT NULL,
    publisher_id UUID,
    title VARCHAR(255) NOT NULL,
    sale_price NUMERIC(19, 2),
    rent_price NUMERIC(19, 2),
    iptu_value NUMERIC(19, 2),
    purpose VARCHAR(50) NOT NULL,
    business_type VARCHAR(50) NOT NULL,
    type VARCHAR(50) NOT NULL,
    managed_by VARCHAR(50) NOT NULL,
    is_available BOOLEAN NOT NULL,
    owner_cpf VARCHAR(14),
    publication_date DATE NOT NULL,
    update_date DATE NOT NULL,

    address_id UUID NOT NULL,
    characteristic_id UUID NOT NULL,
    condominium_id UUID,

    CONSTRAINT pk_property PRIMARY KEY (id),
    CONSTRAINT fk_property_publisher FOREIGN KEY (publisher_id) REFERENCES users(id),
    CONSTRAINT fk_property_address FOREIGN KEY (address_id) REFERENCES address(id),
    CONSTRAINT fk_property_characteristic FOREIGN KEY (characteristic_id) REFERENCES property_characteristic(id),
    CONSTRAINT fk_property_condominium FOREIGN KEY (condominium_id) REFERENCES condominium(id),
    CONSTRAINT uk_property_address UNIQUE (address_id),
    CONSTRAINT uk_property_characteristic UNIQUE (characteristic_id),
    CONSTRAINT uk_property_condominium UNIQUE (condominium_id)
);