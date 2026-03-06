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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource(locations = "classpath:application.properties")
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

        assertEquals(2, seats.size());
    }
}
