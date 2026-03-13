USE xpdb;

INSERT INTO movies (movie_title, movie_description, movie_duration_minutes, movie_category, age_limit, is_3d) VALUES
                                                                                                                  ('Space Adventure', 'Sci-fi epic', 130, 'Sci-Fi', 12, TRUE),
                                                                                                                  ('Romantic Comedy', 'Light romance', 95, 'RomCom', 10, FALSE),
                                                                                                                  ('Horror Night', 'Spine-chilling horror', 100, 'Horror', 18, FALSE),
                                                                                                                  ('Documentary: Nature', 'Wildlife documentary', 60, 'Documentary', 0, FALSE),
                                                                                                                  ('Animated Fun', 'Family animation', 80, 'Animation', 0, TRUE);

INSERT INTO cinemas (cinema_name, cinema_address) VALUES
                                                      ('Downtown Cinema', '123 Main St'),
                                                      ('Mall Cinema', '45 Shopping Ave');


INSERT INTO employees (employee_username, employee_name, employee_password, employee_role) VALUES
                                                                                               ('mabr0011', 'Markus', '1234', 'admin'),
                                                                                               ('masc1001', 'Max-Emil', '2345', 'admin'),
                                                                                               ('fija0001', 'Filip', '3456', 'employee'),
                                                                                               ('pajo0001', 'Patrick', '4567', 'employee');

INSERT INTO theaters (theater_name, number_of_rows, seats_per_row, cinema_id) VALUES
                                                                                  ('Screen 1', 5, 6, 1),
                                                                                  ('Screen 2', 3, 4, 1),
                                                                                  ('Screen 1', 4, 5, 2);

-- Theater 1 seats (theater_id = 1)
INSERT IGNORE INTO seats (theater_id, ro_number, seat_number, seat_type)
SELECT 1, r.rn, s.sn,
       CASE
           WHEN r.rn = 1 THEN 'SOFA_seats'
           WHEN s.sn IN (1,6) THEN 'COWBOY_seats'
           ELSE 'NORMAL'
           END
FROM (SELECT 1 AS rn UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5) r
         CROSS JOIN (SELECT 1 AS sn UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6) s;

-- Theater 2 seats (theater_id = 2)
INSERT IGNORE INTO seats (theater_id, ro_number, seat_number, seat_type)
SELECT 2, r.rn, s.sn,
       CASE WHEN r.rn = 1 THEN 'SOFA_seats' ELSE 'NORMAL' END
FROM (SELECT 1 AS rn UNION ALL SELECT 2 UNION ALL SELECT 3) r
         CROSS JOIN (SELECT 1 AS sn UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4) s;

-- Theater 3 seats (theater_id = 3)
INSERT IGNORE INTO seats (theater_id, ro_number, seat_number, seat_type)
SELECT 3, r.rn, s.sn, 'NORMAL'
FROM (SELECT 1 AS rn UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4) r
         CROSS JOIN (SELECT 1 AS sn UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5) s;

-- Insert shows (ensure movies and theaters exist)
INSERT INTO shows (movie_id, theater_id, start_time) VALUES
                                                         (1, 1, DATE_ADD(NOW(), INTERVAL 1 HOUR)),
                                                         (1, 1, DATE_ADD(NOW(), INTERVAL 4 HOUR)),
                                                         (2, 1, DATE_SUB(NOW(), INTERVAL 2 DAY)),
                                                         (3, 2, DATE_ADD(NOW(), INTERVAL 30 MINUTE)),
                                                         (4, 2, DATE_ADD(NOW(), INTERVAL 3 HOUR)),
                                                         (5, 3, DATE_ADD(NOW(), INTERVAL 15 MINUTE)),
                                                         (2, 3, DATE_ADD(NOW(), INTERVAL 2 DAY));

-- Populate show_seats for each show (set availability = 'VACANT')
INSERT INTO show_seats (show_id, seat_id, seat_availability)
SELECT s.show_id, st.seat_id, 'VACANT'
FROM shows s
         JOIN seats st ON st.theater_id = s.theater_id;

-- Capture show_ids for later use
SET @show_space_1 = (SELECT show_id FROM shows WHERE movie_id=1 AND theater_id=1 ORDER BY start_time LIMIT 1);
SET @show_space_2 = (SELECT show_id FROM shows WHERE movie_id=1 AND theater_id=1 ORDER BY start_time DESC LIMIT 1);
SET @show_romcom_past = (SELECT show_id FROM shows WHERE movie_id=2 AND theater_id=1 AND start_time < NOW() LIMIT 1);
SET @show_horror = (SELECT show_id FROM shows WHERE movie_id=3 LIMIT 1);
SET @show_doc = (SELECT show_id FROM shows WHERE movie_id=4 LIMIT 1);
SET @show_anim = (SELECT show_id FROM shows WHERE movie_id=5 LIMIT 1);
SET @show_romcom_future = (SELECT show_id FROM shows WHERE movie_id=2 AND theater_id=3 LIMIT 1);

-- Pick show_seat_ids (ordered by row/seat) for seats we intend to reserve
SET @t1_showseat_1 = (SELECT ss.show_seat_id FROM show_seats ss JOIN seats s ON ss.seat_id = s.seat_id WHERE ss.show_id = @show_space_1 ORDER BY s.ro_number, s.seat_number LIMIT 1);
SET @t1_showseat_2 = (SELECT ss.show_seat_id FROM show_seats ss JOIN seats s ON ss.seat_id = s.seat_id WHERE ss.show_id = @show_space_1 ORDER BY s.ro_number, s.seat_number LIMIT 1 OFFSET 1);
SET @t1_showseat_3 = (SELECT ss.show_seat_id FROM show_seats ss JOIN seats s ON ss.seat_id = s.seat_id WHERE ss.show_id = @show_space_1 ORDER BY s.ro_number, s.seat_number LIMIT 1 OFFSET 2);
SET @t1_showseat_4 = (SELECT ss.show_seat_id FROM show_seats ss JOIN seats s ON ss.seat_id = s.seat_id WHERE ss.show_id = @show_space_1 ORDER BY s.ro_number, s.seat_number LIMIT 1 OFFSET 3);
SET @t1_showseat_5 = (SELECT ss.show_seat_id FROM show_seats ss JOIN seats s ON ss.seat_id = s.seat_id WHERE ss.show_id = @show_space_2 ORDER BY s.ro_number, s.seat_number LIMIT 1);
SET @t1_showseat_6 = (SELECT ss.show_seat_id FROM show_seats ss JOIN seats s ON ss.seat_id = s.seat_id WHERE ss.show_id = @show_space_2 ORDER BY s.ro_number, s.seat_number LIMIT 1 OFFSET 1);

SET @t2_showseat_1 = (SELECT ss.show_seat_id FROM show_seats ss JOIN seats s ON ss.seat_id = s.seat_id WHERE ss.show_id = @show_horror ORDER BY s.ro_number, s.seat_number LIMIT 1);
SET @t2_showseat_2 = (SELECT ss.show_seat_id FROM show_seats ss JOIN seats s ON ss.seat_id = s.seat_id WHERE ss.show_id = @show_horror ORDER BY s.ro_number, s.seat_number LIMIT 1 OFFSET 1);
SET @t2_showseat_3 = (SELECT ss.show_seat_id FROM show_seats ss JOIN seats s ON ss.seat_id = s.seat_id WHERE ss.show_id = @show_horror ORDER BY s.ro_number, s.seat_number LIMIT 1 OFFSET 2);

SET @t3_showseat_1 = (SELECT ss.show_seat_id FROM show_seats ss JOIN seats s ON ss.seat_id = s.seat_id WHERE ss.show_id = @show_romcom_future ORDER BY s.ro_number, s.seat_number LIMIT 1);
SET @t3_showseat_2 = (SELECT ss.show_seat_id FROM show_seats ss JOIN seats s ON ss.seat_id = s.seat_id WHERE ss.show_id = @show_anim ORDER BY s.ro_number, s.seat_number LIMIT 1);
SET @t3_showseat_3 = (SELECT ss.show_seat_id FROM show_seats ss JOIN seats s ON ss.seat_id = s.seat_id WHERE ss.show_id = @show_anim ORDER BY s.ro_number, s.seat_number LIMIT 1 OFFSET 1);
SET @t3_showseat_4 = (SELECT ss.show_seat_id FROM show_seats ss JOIN seats s ON ss.seat_id = s.seat_id WHERE ss.show_id = @show_anim ORDER BY s.ro_number, s.seat_number LIMIT 1 OFFSET 2);

-- Create reservations (must reference existing show_ids)
INSERT INTO reservations (show_id, customer_name, customer_email, reservation_time, total_price) VALUES
                                                                                                     (@show_horror, 'Alice Example', 'alice@example.com', NOW(), 12.50),
                                                                                                     (@show_horror, 'Frank Guest', 'frank@example.com', NOW(), 12.50),
                                                                                                     (@show_horror, 'Gina Guest', 'gina@example.com', NOW(), 12.50),
                                                                                                     (@show_space_1, 'Bob Test', 'bob@example.com', NOW(), 15.00),
                                                                                                     (@show_space_1, 'Olivia Guest', 'olivia@example.com', NOW(), 15.00),
                                                                                                     (@show_space_2, 'Carol Demo', 'carol@example.com', NOW(), 15.00),
                                                                                                     (@show_romcom_future, 'Dave Sample', 'dave@example.com', NOW(), 10.00),
                                                                                                     (@show_anim, 'Eve Sample', 'eve@example.com', NOW(), 9.00);

-- Capture reservation ids (after insert)
SET @res_horror_1 = (SELECT reservation_id FROM reservations WHERE customer_email='alice@example.com' LIMIT 1);
SET @res_horror_2 = (SELECT reservation_id FROM reservations WHERE customer_email='frank@example.com' LIMIT 1);
SET @res_horror_3 = (SELECT reservation_id FROM reservations WHERE customer_email='gina@example.com' LIMIT 1);

SET @res_space1_1 = (SELECT reservation_id FROM reservations WHERE customer_email='bob@example.com' LIMIT 1);
SET @res_space1_2 = (SELECT reservation_id FROM reservations WHERE customer_email='olivia@example.com' LIMIT 1);
SET @res_space2_1 = (SELECT reservation_id FROM reservations WHERE customer_email='carol@example.com' LIMIT 1);

SET @res_romcom_future = (SELECT reservation_id FROM reservations WHERE customer_email='dave@example.com' LIMIT 1);
SET @res_anim = (SELECT reservation_id FROM reservations WHERE customer_email='eve@example.com' LIMIT 1);

-- Insert movie_tickets with non-NULL reservation_id (each ticket assigned to an existing reservation)
INSERT INTO movie_tickets (price, show_id, show_seat_id, reservation_id) VALUES
-- Horror show tickets (each with its own reservation)
(165.00, @show_horror, @t2_showseat_1, @res_horror_1),
(165.00, @show_horror, @t2_showseat_2, @res_horror_2),
(165.00, @show_horror, (SELECT ss.show_seat_id FROM show_seats ss WHERE ss.show_id=@show_horror ORDER BY ss.show_seat_id LIMIT 1 OFFSET 2), @res_horror_3),

-- Space Adventure tickets (two reservations, multiple seats)
(175.00, @show_space_1, @t1_showseat_1, @res_space1_1),
(175.00, @show_space_1, @t1_showseat_2, @res_space1_2),
(175.00, @show_space_1, @t1_showseat_3, @res_space1_1),
(175.00, @show_space_1, @t1_showseat_4, @res_space1_2),

-- Space Adventure (second show)
(175.00, @show_space_2, @t1_showseat_5, @res_space2_1),
(175.00, @show_space_2, @t1_showseat_6, @res_space2_1),

-- RomCom past/future and Animated
(150.00, @show_romcom_past, (SELECT ss.show_seat_id FROM show_seats ss WHERE ss.show_id=@show_romcom_past ORDER BY ss.show_seat_id LIMIT 1), @res_romcom_future),
(150.00, @show_romcom_future, @t3_showseat_1, @res_romcom_future),
(160.00, @show_anim, @t3_showseat_2, @res_anim),
(160.00, @show_anim, @t3_showseat_3, @res_anim);

-- Mark corresponding show_seats as RESERVED (safe WHERE uses primary key non-NULL)
UPDATE show_seats ss
    JOIN movie_tickets mt ON mt.show_seat_id = ss.show_seat_id
    SET ss.seat_availability = 'RESERVED'
WHERE mt.reservation_id IS NOT NULL
  AND ss.show_seat_id IS NOT NULL;