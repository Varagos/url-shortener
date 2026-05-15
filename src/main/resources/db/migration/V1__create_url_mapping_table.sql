-- V1__create_url_mapping_table.sql

CREATE TABLE url_mapping (
    id UUID PRIMARY KEY,
    "short_code" varchar(10) NOT NULL UNIQUE,
    "original_url" varchar NOT NULL
);

CREATE INDEX idx_short_code ON url_mapping(short_code);