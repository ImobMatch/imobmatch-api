-- Cria o ENUM
CREATE TYPE verification_type AS ENUM ('EMAIL', 'PASSWORD_RESET');

-- Altera a coluna para usar o ENUM
ALTER TABLE user_verification_codes
    ALTER COLUMN type TYPE verification_type
        USING type::verification_type;
