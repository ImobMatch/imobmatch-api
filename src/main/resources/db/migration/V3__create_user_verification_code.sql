CREATE TABLE user_verification_codes (
     id SERIAL PRIMARY KEY,
     user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
     code VARCHAR(6) NOT NULL,
     type VARCHAR(20) NOT NULL, -- "EMAIL" ou "PASSWORD_RESET"
     generated_at TIMESTAMP NOT NULL DEFAULT NOW(),
     verified BOOLEAN NOT NULL DEFAULT FALSE
);
