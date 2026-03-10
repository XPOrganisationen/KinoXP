package com.xp;

import com.xp.Model.*;
import com.xp.Repository.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@Transactional
public class TestTicketRepository {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    private Cinema cinema;
    private Theater theater;
    private Movie movie;
    private Show show;
    private Seat seat;
    private Reservation reservation;


    @BeforeEach
    void setUp() {
        cinema = new Cinema("test Cinema", "http.denmark");
        cinemaRepository.save(cinema);


        theater = new Theater("test theater 1", 12, 20);
        theater.setCinema(cinema);
        theaterRepository.save(theater);

       movie = new Movie();
       movie.setMovieTitle("tron");
       movie.setMovieDuration(134.00);
       movieRepository.save(movie);

        show = new Show();
        show.setMovie(movie);
        show.setTheater(theater);
        show.setStartTime(LocalDateTime.now().plusDays(1));
        showRepository.save(show);

        seat = new Seat(theater, 1, 1, SeatAvailability.VACANT, SeatType.NORMAL);
        seatRepository.save(seat);

        reservation = new Reservation();
        reservation.setShow(show);
        reservationRepository.save(reservation);
    }

    @Test
    public void saveTicket_persistsTicket() {
        MovieTicket ticket = new MovieTicket();
        ticket.setPrice(170.00);
        ticket.setSeat(seat);
        ticket.setShow(show);
        ticket.setReservation(reservation);

        MovieTicket saved = ticketRepository.save(ticket);

        assertNotNull(saved.getMovieTicketId());
        assertEquals(seat.getSeatId(), saved.getSeat().getSeatId());
        assertEquals(show.getShowId(), saved.getShow().getShowId());
        assertEquals(170.00, saved.getPrice());
    }

    @Test
    void findAll_returnsAlltickets() {

        MovieTicket t1 = new MovieTicket(125.00, show, seat);
        t1.setReservation(reservation);
        MovieTicket t2 = new MovieTicket(150.00, show, seat);
        t2.setReservation(reservation);
        MovieTicket t3 = new MovieTicket(165.00, show, seat);
        t3.setReservation(reservation);
        MovieTicket t4 = new MovieTicket(180.00, show, seat);
        t4.setReservation(reservation);

        ticketRepository.save(t1);
        ticketRepository.save(t2);
        ticketRepository.save(t3);
        ticketRepository.save(t4);

        List<MovieTicket> tickets = ticketRepository.findAll();

        assertEquals(4, tickets.size());
    }
    @Test
    void findById_returnsCorrectTicket() {
        MovieTicket ticket = new MovieTicket();
        ticket.setSeat(seat);
        ticket.setShow(show);
        ticket.setReservation(reservation);

        MovieTicket saved = ticketRepository.save(ticket);

        MovieTicket found = ticketRepository.findById(saved.getMovieTicketId()).orElseThrow();

        assertEquals(saved.getMovieTicketId(), found.getMovieTicketId());
    }

    @Test
    void deleteTicket_removesTicket() {

        MovieTicket ticket = new MovieTicket();
        ticket.setShow(show);
        ticket.setSeat(seat);
        ticket.setReservation(reservation);

       MovieTicket saved = ticketRepository.save(ticket);
       ticketRepository.delete(saved);

       assertEquals(1, ticketRepository.findAll().size());
    }
}
