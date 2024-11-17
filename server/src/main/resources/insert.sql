DELETE FROM person;
DELETE FROM location;
DELETE FROM coordinates;
DELETE FROM admin_bid;
DELETE FROM usr;

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
DO $$
DECLARE
    i INT;
BEGIN
    FOR i IN 1..20000 LOOP
        INSERT INTO coordinates (admin_access, created_at, created_by, x, y)
        VALUES (
            (i % 2 = 0),  -- alternate between true and false
            NOW(),        -- created_at time
            'user' || (i % 10 + 1),  -- creator from user1 to user10
            floor(random() * 1000),   -- random x value
            random() * 1000 - 725      -- random y value, ensuring it is > -725
        );
    END LOOP;
END $$;

-- Insert data into location table
DO $$
DECLARE
    i INT;
BEGIN
    FOR i IN 1..20000 LOOP
        INSERT INTO location (admin_access, created_at, created_by, name, x, y)
        VALUES (
            (i % 2 = 0),  -- alternate between true and false
            NOW(),        -- created_at time
            'user' || (i % 10 + 1),  -- creator from user1 to user10
            'Location ' || i,        -- name
            floor(random() * 1000),   -- random x value
            random() * 1000 - 725      -- random y value, ensuring it is > -725
        );
    END LOOP;
END $$;

-- Insert data into person table
DO $$
DECLARE
    i INT;
BEGIN
    FOR i IN 1..20000 LOOP
        INSERT INTO person (admin_access, created_at, created_by, eye_color, hair_color, height, name, nationality, passport_id, weight, coordinates_id, location_id)
        VALUES (
            (i % 2 = 0),  -- alternate between true and false for admin_access
            NOW(),        -- created_at time
            'user' || (i % 10 + 1),  -- creator from user1 to user10
            CASE (i % 5) 
                WHEN 0 THEN 'GREEN'
                WHEN 1 THEN 'BLACK'
                WHEN 2 THEN 'BLUE'
                WHEN 3 THEN 'ORANGE'
                WHEN 4 THEN 'WHITE'
            END,            -- random eye color
            CASE (i % 5) 
                WHEN 0 THEN 'GREEN'
                WHEN 1 THEN 'BLACK'
                WHEN 2 THEN 'BLUE'
                WHEN 3 THEN 'ORANGE'
                WHEN 4 THEN 'WHITE'
            END,            -- random hair color
            (random() * 70 + 150),  -- height between 150 cm and 220 cm
            'Person ' || i,           -- name
            CASE (i % 4)
                WHEN 0 THEN 'USA'
                WHEN 1 THEN 'FRANCE'
                WHEN 2 THEN 'CHINA'
                WHEN 3 THEN 'NORTH_KOREA'
            END,            -- random nationality
            'P' || lpad(i::text, 5, '0'),  -- passport_id e.g., P00001
            (random() * 50 + 50),  -- weight between 50 kg and 100 kg
            (SELECT id FROM coordinates ORDER BY random() LIMIT 1),  -- random coordinates_id from existing coordinates
            (SELECT id FROM location ORDER BY random() LIMIT 1)      -- random location_id from existing locations
        );
    END LOOP;
END $$;
