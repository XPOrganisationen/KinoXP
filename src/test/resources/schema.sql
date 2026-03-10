DROP TABLE IF EXISTS movie_tickets;
DROP TABLE IF EXISTS reservations;
DROP TABLE IF EXISTS show_seats;
DROP TABLE IF EXISTS seats;
DROP TABLE IF EXISTS shows;
DROP TABLE IF EXISTS theaters;
DROP TABLE IF EXISTS cinemas;
DROP TABLE IF EXISTS movies;

CREATE TABLE movies (
        movie_id BIGINT PRIMARY KEY AUTO_INCREMENT,
        movie_title CLOB,
        movie_description CLOB,
        movie_duration_minutes DOUBLE,
        movie_category CLOB,
        age_limit INT,
        is_3d BOOLEAN
);

CREATE TABLE cinemas (
        cinema_id BIGINT PRIMARY KEY AUTO_INCREMENT,
        cinema_name CLOB,
        cinema_address CLOB
);

CREATE TABLE theaters (
        theater_id BIGINT PRIMARY KEY AUTO_INCREMENT,
        theater_name CLOB,
        number_of_rows INT,
        seats_per_row INT,
        cinema_id BIGINT,
        FOREIGN KEY (cinema_id) REFERENCES cinemas(cinema_id) ON DELETE CASCADE
);

CREATE TABLE shows (
        show_id BIGINT PRIMARY KEY AUTO_INCREMENT,
        movie_id BIGINT,
        theater_id BIGINT,
        start_time TIMESTAMP,
        FOREIGN KEY (movie_id) REFERENCES movies(movie_id) ON DELETE CASCADE,
        FOREIGN KEY (theater_id) REFERENCES theaters(theater_id) ON DELETE CASCADE,
        CONSTRAINT ONE_SHOW_PER_THEATER_AT_ONE_TIME UNIQUE (theater_id, start_time)
);

CREATE TABLE seats (
        seat_id BIGINT PRIMARY KEY AUTO_INCREMENT,
        theater_id BIGINT,
        ro_number INT,
        seat_number INT,
        seat_type VARCHAR(20) CHECK (seat_type IN ('COWBOY_seats', 'NORMAL', 'SOFA_seats')),
        FOREIGN KEY (theater_id) REFERENCES theaters(theater_id),
        CONSTRAINT NO_DUPLICATE_seats_IN_ONE_THEATER UNIQUE (theater_id, ro_number, seat_number)
);

CREATE TABLE show_seats (
            show_seat_id BIGINT PRIMARY KEY AUTO_INCREMENT,
            seat_id BIGINT,
            show_id BIGINT,
            seat_availability VARCHAR(20) CHECK (seat_availability IN ('VACANT', 'RESERVED', 'HANDICAP')),
            FOREIGN KEY (seat_id) REFERENCES seats(seat_id),
            FOREIGN KEY (show_id) REFERENCES shows(show_id),
            CONSTRAINT no_duplicate_show_seats UNIQUE (show_seat_id, seat_id, show_id)
);

CREATE TABLE reservations (
            reservation_id BIGINT PRIMARY KEY AUTO_INCREMENT,
            show_id BIGINT,
            customer_name CLOB,
            customer_email CLOB,
            reservation_time TIMESTAMP,
            total_price DOUBLE,
            FOREIGN KEY (show_id) REFERENCES shows(show_id)
);

CREATE TABLE movie_tickets (
            movie_ticket_id BIGINT PRIMARY KEY AUTO_INCREMENT,
            price DOUBLE,
            show_id BIGINT,
            show_seat_id BIGINT,
            reservation_id BIGINT,
            FOREIGN KEY (show_id) REFERENCES shows(show_id),
            FOREIGN KEY (show_seat_id) REFERENCES show_seats(show_seat_id),
            FOREIGN KEY (reservation_id) REFERENCES reservations(reservation_id)
);