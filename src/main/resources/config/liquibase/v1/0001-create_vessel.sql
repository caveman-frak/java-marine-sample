--liquibase formatted sql

--changeset cavemanfrak:01 contextFilter:ddl
CREATE TABLE IF NOT EXISTS vessel (
    id UUID NOT NULL PRIMARY KEY COMMENT 'Internal Identity',
    active BOOLEAN NOT NULL DEFAULT TRUE COMMENT 'In active service',
    tonnage DECIMAL(8,2) NULL COMMENT 'Gross Tonnage',
    beam DECIMAL(8,2) NULL COMMENT 'Width at widest point',
    len DECIMAL(8,2) NULL COMMENT 'Length overall',
    draft DECIMAL(8,2) NULL COMMENT 'Lowest point of keel to summer load line'
);