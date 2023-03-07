--liquibase formatted sql

--changeset cavemanfrak:01 contextFilter:ddl
CREATE TABLE IF NOT EXISTS marine.vessel (
    id UUID NOT NULL PRIMARY KEY COMMENT 'Internal Identifier',
    active BOOLEAN NOT NULL DEFAULT TRUE COMMENT 'In active service',
    name CHAR(255) NOT NULL COMMENT 'Registered Name',
    tonnage DECIMAL(8,2) NULL COMMENT 'Gross Tonnage',
    beam DECIMAL(8,2) NULL COMMENT 'Width at widest point',
    len DECIMAL(8,2) NULL COMMENT 'Length overall',
    draft DECIMAL(8,2) NULL COMMENT 'Lowest point of keel to summer load line'
);