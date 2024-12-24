DELETE FROM person;
DELETE FROM location;
DELETE FROM coordinates;
DELETE FROM entity_history;
DELETE FROM abstract_entity;
DELETE FROM file_import;
DELETE FROM admin_bid;
DELETE FROM usr;

ALTER SEQUENCE abstract_entity_id_seq RESTART WITH 1;
ALTER SEQUENCE entity_history_id_seq RESTART WITH 1;

-- Insert data into admin_bid table
INSERT INTO admin_bid (login, password) VALUES
    ('admin1', '1ab60e110d41a9aac5e30d086c490819bfe3461b38c76b9602fe9686aa0aa3d28c63c96a1019e3788c40a14f4292e50f'),
    ('admin2', '1ab60e110d41a9aac5e30d086c490819bfe3461b38c76b9602fe9686aa0aa3d28c63c96a1019e3788c40a14f4292e50f'),
    ('admin3', '1ab60e110d41a9aac5e30d086c490819bfe3461b38c76b9602fe9686aa0aa3d28c63c96a1019e3788c40a14f4292e50f'),
    ('admin4', '1ab60e110d41a9aac5e30d086c490819bfe3461b38c76b9602fe9686aa0aa3d28c63c96a1019e3788c40a14f4292e50f'),
    ('admin5', '1ab60e110d41a9aac5e30d086c490819bfe3461b38c76b9602fe9686aa0aa3d28c63c96a1019e3788c40a14f4292e50f'),
    ('admin6', '1ab60e110d41a9aac5e30d086c490819bfe3461b38c76b9602fe9686aa0aa3d28c63c96a1019e3788c40a14f4292e50f'),
    ('admin7', '1ab60e110d41a9aac5e30d086c490819bfe3461b38c76b9602fe9686aa0aa3d28c63c96a1019e3788c40a14f4292e50f'),
    ('admin8', '1ab60e110d41a9aac5e30d086c490819bfe3461b38c76b9602fe9686aa0aa3d28c63c96a1019e3788c40a14f4292e50f'),
    ('admin9', '1ab60e110d41a9aac5e30d086c490819bfe3461b38c76b9602fe9686aa0aa3d28c63c96a1019e3788c40a14f4292e50f'),
    ('admin10', '1ab60e110d41a9aac5e30d086c490819bfe3461b38c76b9602fe9686aa0aa3d28c63c96a1019e3788c40a14f4292e50f');

-- Insert data into usr table
INSERT INTO usr (login, password, role) VALUES
    ('admin', '1ab60e110d41a9aac5e30d086c490819bfe3461b38c76b9602fe9686aa0aa3d28c63c96a1019e3788c40a14f4292e50f', 'ADMIN'),
    ('user1', '1ab60e110d41a9aac5e30d086c490819bfe3461b38c76b9602fe9686aa0aa3d28c63c96a1019e3788c40a14f4292e50f', 'USER'),
    ('user2', '1ab60e110d41a9aac5e30d086c490819bfe3461b38c76b9602fe9686aa0aa3d28c63c96a1019e3788c40a14f4292e50f', 'USER'),
    ('user3', '1ab60e110d41a9aac5e30d086c490819bfe3461b38c76b9602fe9686aa0aa3d28c63c96a1019e3788c40a14f4292e50f', 'USER'),
    ('user4', '1ab60e110d41a9aac5e30d086c490819bfe3461b38c76b9602fe9686aa0aa3d28c63c96a1019e3788c40a14f4292e50f', 'USER'),
    ('user5', '1ab60e110d41a9aac5e30d086c490819bfe3461b38c76b9602fe9686aa0aa3d28c63c96a1019e3788c40a14f4292e50f', 'USER'),
    ('user6', '1ab60e110d41a9aac5e30d086c490819bfe3461b38c76b9602fe9686aa0aa3d28c63c96a1019e3788c40a14f4292e50f', 'USER'),
    ('user7', '1ab60e110d41a9aac5e30d086c490819bfe3461b38c76b9602fe9686aa0aa3d28c63c96a1019e3788c40a14f4292e50f', 'USER'),
    ('user8', '1ab60e110d41a9aac5e30d086c490819bfe3461b38c76b9602fe9686aa0aa3d28c63c96a1019e3788c40a14f4292e50f', 'USER'),
    ('user9', '1ab60e110d41a9aac5e30d086c490819bfe3461b38c76b9602fe9686aa0aa3d28c63c96a1019e3788c40a14f4292e50f', 'USER'),
    ('user10', '1ab60e110d41a9aac5e30d086c490819bfe3461b38c76b9602fe9686aa0aa3d28c63c96a1019e3788c40a14f4292e50f', 'USER');

-- Insert data into coordinates table
INSERT INTO abstract_entity (created_by, created_at, admin_access) SELECT
    'user' || (floor(random() * 10) + 1),
    NOW(),
    floor(random() * 2)::int::bool
FROM generate_series(1, 300) i;

INSERT INTO coordinates (id, x, y) SELECT
    i,
    floor(random() * 2000) - 1000,   -- random x value
    round((random() * 2000 - 725)::numeric, 2)      -- random y value, ensuring it is > -725
FROM generate_series(1, 100) i;


-- Insert data into location table
INSERT INTO location (id, name, x, y) SELECT
  i,
  'Location ' || i,        -- name
  floor(random() * 2000) - 1000,   -- random x value
  round((random() * 2000 - 1000)::numeric, 2)
FROM generate_series(101, 200) i;

-- Insert data into person table
INSERT INTO person (id, eye_color, hair_color, height, name, nationality, passport_id, weight, coordinates_id, location_id) SELECT
    i,
    CASE (floor(random() * 5))
    WHEN 0 THEN 'GREEN'
    WHEN 1 THEN 'BLACK'
    WHEN 2 THEN 'BLUE'
    WHEN 3 THEN 'ORANGE'
    WHEN 4 THEN 'WHITE'
    END,            -- random eye color
    CASE (floor(random() * 5))
    WHEN 0 THEN 'GREEN'
    WHEN 1 THEN 'BLACK'
    WHEN 2 THEN 'BLUE'
    WHEN 3 THEN 'ORANGE'
    WHEN 4 THEN 'WHITE'
    END,            -- random hair color
    round((random() * 70 + 150)::numeric, 2),  -- height between 150 cm and 220 cm
    'Person ' || i,           -- name
    CASE (floor(random() * 4))
    WHEN 0 THEN 'USA'
    WHEN 1 THEN 'FRANCE'
    WHEN 2 THEN 'CHINA'
    WHEN 3 THEN 'NORTH_KOREA'
    END,            -- random nationality
    'P' || lpad(i::text, 7, '0'),  -- passport_id e.g., P0000001
    round((random() * 50 + 50)::numeric, 2),  -- weight between 50 kg and 100 kg
    (SELECT id FROM coordinates ORDER BY random() LIMIT 1 OFFSET i - 201),  -- random coordinates_id from existing coordinates
    (SELECT id FROM location ORDER BY random() LIMIT 1 OFFSET i - 201)  -- random location_id from existing locations
FROM generate_series(201, 300) i;