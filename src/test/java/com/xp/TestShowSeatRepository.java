package com.xp;

import com.xp.Model.*;
import com.xp.Model.DTOs.Seat;
import com.xp.Repository.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
public class TestShowSeatRepository {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private MovieRepository movieRepository;

    private Cinema cinema;
    private Theater bigTheater;
    private Theater smallTheater;
    private Show testShow;
    private Movie movie;

    @BeforeEach
    void setUp() {
        cinema = new Cinema();
        cinema.setCinemaAddress("Test Cinema");
        cinemaRepository.save(cinema);

        bigTheater = new Theater(cinema, "Big Theater", 20, 12);
        theaterRepository.save(bigTheater);

        smallTheater = new Theater(cinema, "Small Theater", 15, 10);
        theaterRepository.save(smallTheater);

        movie = new Movie();
        movie.setMovieTitle("Transformers");
        movie.setMovieDuration(134.0);
        movie.setMovieCategory("Action");
        movie.setAgeLimit(12);
        movie.setIs_3d(false);

        movieRepository.save(movie);

        testShow = new Show(movie, bigTheater, LocalDateTime.now().plusHours(1));
        showRepository.save(testShow);
    }

    @Test
    void findAll_returnsShowSeats() {
        List<ShowSeat> showSeatsBefore = seatRepository.findAll();
        Seat seat1 = new Seat(bigTheater, 2, 2, SeatType.NORMAL);
        Seat seat2 = new Seat(bigTheater, 2, 5, SeatType.NORMAL);

        ShowSeat s1 = new ShowSeat(seat1, testShow, SeatAvailability.VACANT);
        ShowSeat s2 = new ShowSeat(seat2, testShow, SeatAvailability.VACANT);
        seatRepository.save(s1);
        seatRepository.save(s2);

        List<ShowSeat> showSeats = seatRepository.findAll();

        assertEquals(showSeatsBefore.size() + 2, showSeats.size());
    }

    @Test
    void findAllByShow_returnsCorrectShowSeats() {
        Seat seat1 = new Seat(bigTheater, 1, 1, SeatType.NORMAL);
        Seat seat2 = new Seat(bigTheater, 1, 2, SeatType.NORMAL);
        Seat seat3 = new Seat(smallTheater, 1, 1, SeatType.NORMAL);

        ShowSeat s1 = new ShowSeat(seat1, testShow, SeatAvailability.VACANT);
        ShowSeat s2 = new ShowSeat(seat2, testShow, SeatAvailability.RESERVED);
        ShowSeat s3 = new ShowSeat(seat3, testShow, SeatAvailability.VACANT);
        seatRepository.save(s1);
        seatRepository.save(s2);
        seatRepository.save(s3);

        List<ShowSeat> bigShowSeats = seatRepository.findAllByShow(testShow);

        assertEquals(3, bigShowSeats.size());
        for (ShowSeat s : bigShowSeats) {
            assertEquals(testShow, s.getShow());
        }
    }

    @Test
    void findAllByShowAndAvailability_returnsVacantSeatsOnly() {
        Seat seat1 = new Seat(bigTheater, 1, 1, SeatType.NORMAL);
        Seat seat2 = new Seat(bigTheater, 1, 2, SeatType.NORMAL);
        Seat seat3 = new Seat(bigTheater, 1, 3, SeatType.NORMAL);

        ShowSeat s1 = new ShowSeat(seat1, testShow, SeatAvailability.VACANT);
        ShowSeat s2 = new ShowSeat(seat2, testShow, SeatAvailability.RESERVED);
        ShowSeat s3 = new ShowSeat(seat3, testShow, SeatAvailability.VACANT);
        seatRepository.save(s1);
        seatRepository.save(s2);
        seatRepository.save(s3);

        List<ShowSeat> availableShowSeats = seatRepository.findAllByShowAndSeatAvailability(testShow, SeatAvailability.VACANT);

        assertEquals(2, availableShowSeats.size());
        for (ShowSeat s : availableShowSeats) {
            assertEquals(SeatAvailability.VACANT, s.getSeatAvailability());
        }
    }

    @Test
    void save_showSeat_isPersisted() {
        Seat seat = new Seat(bigTheater, 3, 4, SeatType.NORMAL);

        ShowSeat showSeat = new ShowSeat(seat, testShow, SeatAvailability.VACANT);
        ShowSeat savedShowSeat = seatRepository.save(showSeat);

        assertEquals(3, savedShowSeat.getSeat().getRowNumber());
        assertEquals(4, savedShowSeat.getSeat().getSeatNumber());
        assertEquals(SeatAvailability.VACANT, savedShowSeat.getSeatAvailability());
    }

    @Test
    void findAllByShow_returnsEmptyList_whenNoSeatsExist() {

        Movie anotherMovie = new Movie();
        anotherMovie.setMovieTitle("Random");
        movieRepository.save(anotherMovie);

        Show anotherShow = new Show(anotherMovie, smallTheater, LocalDateTime.now());
        showRepository.save(anotherShow);

        List<ShowSeat> showSeats = seatRepository.findAllByShow(anotherShow);

        assertEquals(0, showSeats.size());
    }

    @Test
    void findAllByShow_doesNotReturnSeatsFromOtherShows() {
        Seat seat1 = new Seat(bigTheater, 1, 1, SeatType.NORMAL);
        Seat seat2 = new Seat(smallTheater, 1, 1, SeatType.NORMAL);

        Movie testMovie = new Movie();
        testMovie.setMovieTitle("Testing");
        movieRepository.save(testMovie);

        ShowSeat s1 = new ShowSeat(seat1, testShow, SeatAvailability.VACANT);
        Show anotherShow = new Show(testMovie, smallTheater, LocalDateTime.now());
        showRepository.save(anotherShow);
        ShowSeat s2 = new ShowSeat(seat2, anotherShow, SeatAvailability.VACANT);
        seatRepository.save(s1);
        seatRepository.save(s2);

        List<ShowSeat> bigShowSeats = seatRepository.findAllByShow(testShow);

        assertEquals(1, bigShowSeats.size());
    }
}