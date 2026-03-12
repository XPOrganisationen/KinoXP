INSERT INTO movies (movie_title, movie_description, movie_duration_minutes, movie_category, age_limit, is_3d) VALUES
('Space Adventure', 'Sci-fi epic', 130, 'Science Fiction', 12, TRUE),
('Romantic Comedy', 'Light romance', 95, 'Romantic Comedy', 10, FALSE),
('Horror Night', 'Spine-chilling horror', 100, 'Horror', 18, FALSE),
('Documentary: Nature', 'Wildlife documentary', 60, 'Documentary', 0, FALSE),
('Animated Fun', 'Family animation', 80, 'Animation', 0, TRUE);


INSERT INTO cinemas (cinema_name, cinema_address) VALUES
('Downtown Cinema', '123 Main St'),
('Mall Cinema', '45 Shopping Ave');


INSERT INTO theaters (theater_name, number_of_rows, seats_per_row, cinema_id) VALUES
('Screen 1', 5, 6, 1),
('Screen 2', 3, 4, 1),
('Screen 1', 4, 5, 2);

-- Theater 1
INSERT INTO seats (theater_id, ro_number, seat_number, seat_type)
SELECT 1, r.rn, s.sn,
       CASE
         WHEN r.rn = 1 THEN 'SOFA_seats'
         WHEN s.sn IN (1,6) THEN 'COWBOY_seats'
         ELSE 'NORMAL'
       END
FROM (SELECT 1 AS rn UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5) r
CROSS JOIN (SELECT 1 AS sn UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6) s;

-- Theater 2
INSERT INTO seats (theater_id, ro_number, seat_number, seat_type)
SELECT 2, r.rn, s.sn,
       CASE WHEN r.rn = 1 THEN 'SOFA_seats' ELSE 'NORMAL' END
FROM (SELECT 1 AS rn UNION ALL SELECT 2 UNION ALL SELECT 3) r
CROSS JOIN (SELECT 1 AS sn UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4) s;

-- Theater 3
INSERT INTO seats (theater_id, ro_number, seat_number, seat_type)
SELECT 3, r.rn, s.sn, 'NORMAL'
FROM (SELECT 1 AS rn UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4) r
CROSS JOIN (SELECT 1 AS sn UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5) s;


INSERT INTO shows (movie_id, theater_id, start_time) VALUES
(1, 1, CURRENT_TIMESTAMP + INTERVAL '1' HOUR),
(1, 1, CURRENT_TIMESTAMP + INTERVAL '4' HOUR),
(2, 1, CURRENT_TIMESTAMP - INTERVAL '2' DAY),
(3, 2, CURRENT_TIMESTAMP + INTERVAL '30' MINUTE),
(4, 2, CURRENT_TIMESTAMP + INTERVAL '3' HOUR),
(5, 3, CURRENT_TIMESTAMP + INTERVAL '15' MINUTE),
(2, 3, CURRENT_TIMESTAMP + INTERVAL '2' DAY);


INSERT INTO show_seats (show_id, seat_id, seat_availability)
SELECT s.show_id, st.seat_id, 'VACANT'
FROM shows s
JOIN seats st ON st.theater_id = s.theater_id;


INSERT INTO reservations (show_id, customer_name, customer_email, reservation_time, total_price) VALUES
((SELECT show_id FROM shows WHERE movie_id=3 LIMIT 1), 'Alice Example', 'alice@example.com', CURRENT_TIMESTAMP, 12.50),
((SELECT show_id FROM shows WHERE movie_id=3 LIMIT 1 OFFSET 1), 'Frank Guest', 'frank@example.com', CURRENT_TIMESTAMP, 12.50),
((SELECT show_id FROM shows WHERE movie_id=3 LIMIT 1 OFFSET 2), 'Gina Guest', 'gina@example.com', CURRENT_TIMESTAMP, 12.50),
((SELECT show_id FROM shows WHERE movie_id=1 ORDER BY start_time LIMIT 1), 'Bob Test', 'bob@example.com', CURRENT_TIMESTAMP, 15.00),
((SELECT show_id FROM shows WHERE movie_id=1 ORDER BY start_time LIMIT 1 OFFSET 1), 'Olivia Guest', 'olivia@example.com', CURRENT_TIMESTAMP, 15.00),
((SELECT show_id FROM shows WHERE movie_id=1 ORDER BY start_time DESC LIMIT 1), 'Carol Demo', 'carol@example.com', CURRENT_TIMESTAMP, 15.00),
((SELECT show_id FROM shows WHERE movie_id=2 ORDER BY start_time DESC LIMIT 1), 'Dave Sample', 'dave@example.com', CURRENT_TIMESTAMP, 10.00),
((SELECT show_id FROM shows WHERE movie_id=5 LIMIT 1), 'Eve Sample', 'eve@example.com', CURRENT_TIMESTAMP, 9.00);


INSERT INTO movie_tickets (price, show_seat_id, reservation_id, ticket_type)
SELECT 12.50,
       (SELECT ss.show_seat_id FROM show_seats ss JOIN seats s ON ss.seat_id = s.seat_id WHERE ss.show_id = (SELECT show_id FROM shows WHERE movie_id=3 LIMIT 1) ORDER BY s.ro_number, s.seat_number LIMIT 1),
    (SELECT reservation_id FROM reservations WHERE customer_email='alice@example.com' LIMIT 1),
    'ADULT'
UNION ALL
SELECT 12.50,
       (SELECT ss.show_seat_id FROM show_seats ss JOIN seats s ON ss.seat_id = s.seat_id WHERE ss.show_id = (SELECT show_id FROM shows WHERE movie_id=3 LIMIT 1) ORDER BY s.ro_number, s.seat_number LIMIT 1 OFFSET 1),
    (SELECT reservation_id FROM reservations WHERE customer_email='frank@example.com' LIMIT 1),
    'ADULT'
UNION ALL
SELECT 12.50,
       (SELECT ss.show_seat_id FROM show_seats ss JOIN seats s ON ss.seat_id = s.seat_id WHERE ss.show_id = (SELECT show_id FROM shows WHERE movie_id=3 LIMIT 1) ORDER BY s.ro_number, s.seat_number LIMIT 1 OFFSET 2),
    (SELECT reservation_id FROM reservations WHERE customer_email='gina@example.com' LIMIT 1),
    'ADULT';


UPDATE show_seats
SET seat_availability = 'RESERVED'
WHERE show_seat_id IN (
    SELECT show_seat_id FROM movie_tickets WHERE reservation_id IS NOT NULL
);