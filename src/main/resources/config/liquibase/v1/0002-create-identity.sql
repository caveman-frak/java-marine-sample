--liquibase formatted sql

--changeset cavemanfrak:01 contextFilter:ddl
CREATE TABLE IF NOT EXISTS identifier (
    vessel UUID NOT NULL COMMENT 'Vessel Internal Identity',
    provider ENUM('MMSI','IMO','IRCS','REGISTRY','NICKNAME','OTHER')
        NOT NULL COMMENT 'Provider of Identifier',
    name CHAR(255) NOT NULL COMMENT 'Public Identifier (Name or Value)',
    CONSTRAINT  fk_identifier_vessel
        FOREIGN KEY (vessel) REFERENCES vessel (id)
        ON DELETE CASCADE
        ON UPDATE RESTRICT
);

--changeset cavemanfrak:02 contextFilter:ddl
CREATE OR REPLACE UNIQUE INDEX idx_identifier_vessel_source
    ON identifier (
        vessel,
        provider
    );

--changeset cavemanfrak:03 contextFilter:ddl
CREATE OR REPLACE UNIQUE INDEX idx_identifier_source_name
    ON identifier (
        provider,
        name
    );

--changeset cavemanfrak:04 contextFilter:ddl
CREATE OR REPLACE INDEX idx_identifier_name
    ON identifier (
        name
    );