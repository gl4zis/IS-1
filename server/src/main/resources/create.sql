DROP TABLE IF EXISTS person, location, coordinates, entity_history, abstract_entity, admin_bid, usr;

CREATE TABLE admin_bid (
   login varchar PRIMARY KEY,
   password varchar NOT NULL
);

CREATE TABLE usr (
    login varchar PRIMARY KEY,
    password varchar NOT NULL,
    role varchar NOT NULL -- can be only one of ['ADMIN', 'USER']
);

CREATE TABLE abstract_entity (
    id serial PRIMARY KEY,
    created_by varchar NOT NULL,
    created_at timestamp with time zone NOT NULL,
    admin_access bool NOT NULL DEFAULT false,
    removed_by varchar,
    removed_at timestamp with time zone,

    CONSTRAINT fk_created FOREIGN KEY (created_by) REFERENCES usr (login),
    CONSTRAINT fk_removed FOREIGN KEY (removed_by) REFERENCES usr (login)
);

CREATE TABLE entity_history (
    id serial PRIMARY KEY,
    entity_id int NOT NULL,
    updated_by varchar NOT NULL,
    updated_at timestamp with time zone NOT NULL,

    CONSTRAINT fk_updated FOREIGN KEY (updated_by) REFERENCES usr (login),
    CONSTRAINT fk_entity_history FOREIGN KEY (entity_id) REFERENCES abstract_entity (id)
);

CREATE TABLE coordinates (
    id int PRIMARY KEY,
    x bigint NOT NULL,
    y double precision NOT NULL CHECK(y > -725),

    CONSTRAINT fk_coord_id FOREIGN KEY (id) REFERENCES abstract_entity (id)
);

CREATE TABLE location (
    id int PRIMARY KEY,
    name varchar(312) NOT NULL,
    x bigint NOT NULL,
    y double precision NOT NULL,

    CONSTRAINT fk_location_id FOREIGN KEY (id) REFERENCES abstract_entity (id)
);

CREATE TABLE person (
    id int PRIMARY KEY,
    eye_color varchar NOT NULL, -- can be only one of ['GREEN', 'BLACK', 'BLUE', 'ORANGE', 'WHITE']
    hair_color varchar NOT NULL,  -- can be only one of ['GREEN', 'BLACK', 'BLUE', 'ORANGE', 'WHITE']
    height real NOT NULL,
    name varchar NOT NULL,
    nationality varchar, -- can be null or one of ['USA', 'FRANCE', 'CHINA', 'NORTH_KOREA']
    passport_id varchar NOT NULL CHECK(length(passport_id) >= 4),
    weight bigint NOT NULL,
    coordinates_id int NOT NULL,
    location_id int,

    CONSTRAINT fk_person_id FOREIGN KEY (id) REFERENCES abstract_entity (id),
    CONSTRAINT fk_location FOREIGN KEY (location_id) REFERENCES location (id) ON DELETE SET NULL,
    CONSTRAINT fk_coordinates FOREIGN KEY (coordinates_id) REFERENCES coordinates (id)
);