-- Rename for patter class_name
ALTER TYPE role_type RENAME TO user_role;
ALTER TYPE account_status_enum RENAME TO broker_account_status;

-- add value
ALTER TYPE broker_account_status ADD VALUE 'REJECTED';
ALTER TABLE brokers
    ALTER COLUMN business_type TYPE property_business_type
        USING business_type::text::property_business_type;

DROP TYPE business_type_enum;

--transform current data to enum
ALTER TABLE brokers_regions_interest
    ALTER COLUMN region TYPE brazilian_state
        USING region::brazilian_state;