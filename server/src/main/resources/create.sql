DROP TABLE IF EXISTS person, location, coordinates, admin_bid, usr;

CREATE TABLE admin_bid (
	login varchar PRIMARY KEY,
	password varchar NOT NULL
);
-- please, set in password only this string: '1ab60e110d41a9aac5e30d086c490819bfe3461b38c76b9602fe9686aa0aa3d28c63c96a1019e3788c40a14f4292e50f'

CREATE TABLE usr (
	login varchar PRIMARY KEY,
	password varchar NOT NULL,
	role varchar NOT NULL -- can be only one of ['ADMIN', 'USER']
);
-- please, set in password only this string: '1ab60e110d41a9aac5e30d086c490819bfe3461b38c76b9602fe9686aa0aa3d28c63c96a1019e3788c40a14f4292e50f'

CREATE TABLE coordinates (
	id serial PRIMARY KEY,
	admin_access boolean NOT NULL,
	created_at timestamp without time zone NOT NULL,
	updated_at timestamp without time zone,
	created_by varchar NOT NULL,
	updated_by varchar,
	x bigint NOT NULL,
	y double precision NOT NULL CHECK(y > -725),

	CONSTRAINT fk_creater FOREIGN KEY (created_by) REFERENCES usr (login),
	CONSTRAINT fk_updater FOREIGN KEY (updated_by) REFERENCES usr (login)
);

CREATE TABLE location (
	id serial PRIMARY KEY,
	admin_access boolean NOT NULL,
	created_at timestamp without time zone NOT NULL,
	updated_at timestamp without time zone,
	created_by varchar NOT NULL,
	updated_by varchar,
	name varchar(312) NOT NULL,
	x bigint NOT NULL,
	y double precision NOT NULL,

	CONSTRAINT fk_creater FOREIGN KEY (created_by) REFERENCES usr (login),
	CONSTRAINT fk_updater FOREIGN KEY (updated_by) REFERENCES usr (login)
);

CREATE TABLE person (
	id serial PRIMARY KEY,
	admin_access boolean NOT NULL,
	created_at timestamp without time zone NOT NULL,
	updated_at timestamp without time zone,
	created_by varchar NOT NULL,
	updated_by varchar,
	eye_color varchar NOT NULL, -- can be only one of ['GREEN', 'BLACK', 'BLUE', 'ORANGE', 'WHITE']
	hair_color varchar NOT NULL,  -- can be only one of ['GREEN', 'BLACK', 'BLUE', 'ORANGE', 'WHITE']
	height real NOT NULL,
	name varchar NOT NULL,
	nationality varchar, -- can be null or one of ['USA', 'FRANCE', 'CHINA', 'NORTH_KOREA']
	passport_id varchar NOT NULL CHECK(length(passport_id) >= 4),
	weight bigint NOT NULL,
	coordinates_id int NOT NULL,
	location_id int,
	
	CONSTRAINT fk_creater FOREIGN KEY (created_by) REFERENCES usr (login),
	CONSTRAINT fk_updater FOREIGN KEY (updated_by) REFERENCES usr (login),
	CONSTRAINT fk_location FOREIGN KEY (location_id) REFERENCES location (id) ON DELETE SET NULL,
	CONSTRAINT fk_coordinates FOREIGN KEY (coordinates_id) REFERENCES coordinates (id)
);