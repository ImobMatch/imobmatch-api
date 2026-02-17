-- V7__create_table_property_images.sql
-- Migration to create the properties_imagens table

CREATE TABLE IF NOT EXISTS property_images (
    id UUID PRIMARY KEY,
    property_id UUID NOT NULL,
    imagen_key VARCHAR(255) NOT NULL UNIQUE,
    CONSTRAINT fk_property
    FOREIGN KEY(property_id)
    REFERENCES property(id)
    ON DELETE CASCADE
    );

CREATE INDEX idx_properties_imagens_property_id ON property_images(property_id);
