package com.xp;

import com.xp.Model.Cinema;
import com.xp.Model.Seat;
import com.xp.Model.SeatAvailability;
import com.xp.Model.Theater;
import com.xp.Repository.CinemaRepository;
import com.xp.Repository.SeatRepository;
import com.xp.Repository.TheaterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class TestSeatRepository {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    private Cinema cinema;
    private Theater bigTheater;
    private Theater smallTheater;

    @BeforeEach
    void setUp() {
        cinema = new Cinema();
        cinema.setCinemaAddress("test Cinema");
        cinemaRepository.save(cinema);

        bigTheater = new Theater("big Theater", 20, 12);
        bigTheater.setCinema(cinema);
        theaterRepository.save(bigTheater);

        smallTheater = new Theater("small Theater", 15, 10);
        smallTheater.setCinema(cinema);
        theaterRepository.save(smallTheater);
    }

    @Test
    void findAll_returnsSeats() {
        Seat s1 = new Seat(bigTheater, 2, 2, SeatAvailability.VACANT);
        Seat s2 = new Seat(bigTheater, 2, 5, SeatAvailability.VACANT);

        seatRepository.save(s1);
        seatRepository.save(s2);

        List<Seat> seats = seatRepository.findAll();

        assertEquals(70, seats.size());
    }

    @Test
    void findByTheater_returnsCorrectSeats() {
        Seat s1 = new Seat(bigTheater, 1, 1, SeatAvailability.VACANT);
        Seat s2 = new Seat(bigTheater, 1, 2, SeatAvailability.RESERVED);
        Seat s3 = new Seat(smallTheater, 1, 1, SeatAvailability.VACANT);

        seatRepository.save(s1);
        seatRepository.save(s2);
        seatRepository.save(s3);


        List<Seat> bigSeats = seatRepository.findByTheater(bigTheater);

        assertEquals(2, bigSeats.size());
        for (Seat s : bigSeats) {
            assertEquals(bigTheater, s.getTheater());
        }
    }

    @Test
    void findByTheaterAndAvailability_returnsVacantSeatsOnly() {
        Seat s1 = new Seat(bigTheater, 1, 1, SeatAvailability.VACANT);
        Seat s2 = new Seat(bigTheater, 1, 2, SeatAvailability.RESERVED);
        Seat s3 = new Seat(bigTheater, 1, 3, SeatAvailability.VACANT);

        seatRepository.save(s1);
        seatRepository.save(s2);
        seatRepository.save(s3);

        List<Seat> availableSeats = seatRepository.findByTheaterAndSeatAvailability(bigTheater, SeatAvailability.VACANT);

        assertEquals(2, availableSeats.size());

        for (Seat s: availableSeats) {
            assertEquals(SeatAvailability.VACANT, s.getSeatAvailability());
        }
    }
    @Test
    void save_seat_isPersisted() {
        Seat seat = new Seat(bigTheater, 3, 4, SeatAvailability.VACANT);

        Seat savedSeat = seatRepository.save(seat);

        assertEquals(3, savedSeat.getRowNumber());
        assertEquals(4, savedSeat.getSeatNumber());
        assertEquals(SeatAvailability.VACANT, savedSeat.getSeatAvailability());
    }

    @Test
    void findByTheater_returnsEmptyList_whenNoSeatsExist() {

        List<Seat> seats = seatRepository.findByTheater(bigTheater);

        assertEquals(0, seats.size());
    }

    @Test
    void findByTheater_doesNotReturnSeatsFromOtherTheaters() {
        Seat s1 = new Seat(bigTheater, 1, 1, SeatAvailability.VACANT);
        Seat s2 = new Seat(smallTheater, 1, 1, SeatAvailability.VACANT);

        seatRepository.save(s1);
        seatRepository.save(s2);

        List<Seat> bigSeats = seatRepository.findByTheater(bigTheater);

        assertEquals(1, bigSeats.size());
    }

}
