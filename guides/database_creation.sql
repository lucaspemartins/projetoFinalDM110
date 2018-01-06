-- Database: network

-- DROP DATABASE network;

CREATE DATABASE network
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Portuguese_Brazil.1252'
    LC_CTYPE = 'Portuguese_Brazil.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;


	
-- SCHEMA: public

-- DROP SCHEMA public ;

CREATE SCHEMA public
    AUTHORIZATION postgres;

COMMENT ON SCHEMA public
    IS 'standard public schema';

GRANT ALL ON SCHEMA public TO PUBLIC;

GRANT ALL ON SCHEMA public TO postgres;



-- Table: public.address

-- DROP TABLE public.address;

CREATE TABLE public.address
(
    ip character varying(15) COLLATE pg_catalog."default" NOT NULL,
    isactive boolean NOT NULL DEFAULT false,
    CONSTRAINT address_pkey PRIMARY KEY (ip)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.address
    OWNER to postgres;