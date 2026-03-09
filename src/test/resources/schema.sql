DROP SCHEMA IF EXISTS xpdb;
CREATE SCHEMA IF NOT EXISTS xpdb;
USE xpdb;
DROP TABLE IF EXISTS reservations;
DROP TABLE IF EXISTS movie_tickets;
DROP TABLE IF EXISTS seats;
DROP TABLE IF EXISTS shows;
DROP TABLE IF EXISTS theaters;
DROP TABLE IF EXISTS cinemas;
DROP TABLE IF EXISTS movies;

CREATE TABLE IF NOT EXISTS movies (
	movie_id INT PRIMARY KEY AUTO_INCREMENT,
    movie_title TEXT,
    movie_description TEXT,
    movie_duration_minutes DOUBLE,
    movie_category TEXT,
    age_limit INT,
    is_3d BOOL
);

CREATE TABLE IF NOT EXISTS cinemas (
	cinema_id INT PRIMARY KEY AUTO_INCREMENT,
    cinema_name TEXT,
    cinema_address TEXT
);

CREATE TABLE IF NOT EXISTS theaters (
	theater_id INT PRIMARY KEY AUTO_INCREMENT,
    theater_name TEXT,
    number_of_rows INT,
    seats_per_row INT,
    cinema_id INT,
    FOREIGN KEY (cinema_id) REFERENCES cinemas(cinema_id) ON DELETE CASCADE
);

CREATE TABLE shows (
	show_id INT PRIMARY KEY AUTO_INCREMENT,
    movie_id INT,
    theater_id INT,
    start_time DATETIME,
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id) ON DELETE CASCADE,
    FOREIGN KEY (theater_id) REFERENCES theaters(theater_id) ON DELETE CASCADE,
    CONSTRAINT ONE_SHOW_PER_THEATER_AT_ONE_TIME UNIQUE (theater_id, start_time)
);

CREATE TABLE seats (
	seat_id INT PRIMARY KEY AUTO_INCREMENT,
    theater_id INT,
    ro_number INT,
    seat_number INT,
    seat_type ENUM('COWBOY_seats', 'NORMAL', 'SOFA_seats'),
    seat_availability ENUM('VACANT', 'RESERVED', 'HANDICAP'),
    FOREIGN KEY (theater_id) REFERENCES theaters(theater_id),
    CONSTRAINT NO_DUPLICATE_seats_IN_ONE_THEATER UNIQUE (theater_id, ro_number, seat_number)
);

CREATE TABLE reservations (
    reservation_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    show_id BIGINT,
    customer_name TEXT,
    customer_email TEXT,
    reservation_time DATETIME,
    total_price double,
    FOREIGN KEY (show_id) REFERENCES shows(show_id)
);

CREATE TABLE movie_tickets (
    movie_ticket_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    price DOUBLE,
    show_id BIGINT,
    seat_id BIGINT,
    reservation_id BIGINT,
    FOREIGN KEY (show_id) REFERENCES shows(show_id),
    FOREIGN KEY (seat_id) REFERENCES seats(seat_id),
    FOREIGN KEY (reservation_id) REFERENCES reservations(reservation_id)
);