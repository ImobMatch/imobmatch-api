ALTER TABLE user_verification_codes
    ALTER COLUMN code TYPE VARCHAR(6)
        USING type::VARCHAR(255);