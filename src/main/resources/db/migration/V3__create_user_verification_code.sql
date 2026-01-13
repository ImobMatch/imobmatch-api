CREATE TYPE verification_type AS ENUM ('EMAIL', 'PASSWORD_RESET');

CREATE TABLE user_verification_codes (
     id UUID PRIMARY KEY,
     user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
     code VARCHAR(6) NOT NULL,
     type verification_type NOT NULL,
     generated_at TIMESTAMP NOT NULL DEFAULT NOW(),
     verified BOOLEAN NOT NULL DEFAULT FALSE
);
