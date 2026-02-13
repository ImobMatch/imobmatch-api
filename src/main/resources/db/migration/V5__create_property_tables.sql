CREATE TABLE address (
     id UUID NOT NULL,
     street VARCHAR(255) NOT NULL,
     number INTEGER,
     complement VARCHAR(255),
     neighborhood VARCHAR(255) NOT NULL,
     city VARCHAR(255) NOT NULL,
     state VARCHAR(2) NOT NULL,
     zip_code VARCHAR(20) NOT NULL,
     reference_point VARCHAR(255),
     CONSTRAINT pk_address PRIMARY KEY (id)
);

CREATE TABLE property_characteristic (
    id UUID NOT NULL,
    description VARCHAR(255),
    area NUMERIC(19, 2) NOT NULL,
    land_area NUMERIC(19, 2),
    usable_area NUMERIC(19, 2),
    total_area NUMERIC(19, 2),
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
    cnpj VARCHAR(18),
    has_gym BOOLEAN,
    has_sauna BOOLEAN,
    has_spa BOOLEAN,
    has_party_room BOOLEAN,
    has_sports_courts BOOLEAN,
    has_playground BOOLEAN,
    has_coworking_space BOOLEAN,
    has_cinema BOOLEAN,
    has_game_room BOOLEAN,
    has_shared_terrace BOOLEAN,
    has_mini_market BOOLEAN,
    has_pet_area BOOLEAN,
    has_bike_storage BOOLEAN,
    has_restaurant BOOLEAN,
    has_24h_security BOOLEAN,
    has_cameras BOOLEAN,
    has_elevators BOOLEAN,
    has_electric_car_station BOOLEAN,
    CONSTRAINT pk_condominium PRIMARY KEY (id),
    CONSTRAINT uk_condominium_cnpj UNIQUE (cnpj)
);

CREATE TABLE property (
    id UUID NOT NULL,
    type VARCHAR(50) NOT NULL,
    managed_by VARCHAR(50) NOT NULL,
    is_available BOOLEAN NOT NULL,
    owner_cpf VARCHAR(14),

    publisher_id UUID,
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