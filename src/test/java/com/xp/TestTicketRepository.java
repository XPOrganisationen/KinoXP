package com.xp;

import com.xp.Model.*;
import com.xp.Model.DTOs.Seat;
import com.xp.Repository.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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

    private Cinema cinema;
    private Theater theater;
    private Movie movie;
    private Show show;
    private ShowSeat showSeat;


    @BeforeEach
    @Transactional
    @Rollback
    void setUp() {
        cinema = new Cinema("test Cinema", "http.denmark");
        cinemaRepository.save(cinema);


        theater = new Theater(cinema, "test theater 1", 12, 20);
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


        Seat seat = new Seat(theater, 1, 1, SeatType.NORMAL);


        showSeat = new ShowSeat(seat, show, SeatAvailability.VACANT);
        seatRepository.save(showSeat);
    }

    @Test
    public void saveTicket_persistsTicket() {
        MovieTicket ticket = new MovieTicket();
        ticket.setPrice(170.00);
        ticket.setSeat(showSeat);

        MovieTicket saved = ticketRepository.save(ticket);

        assertNotNull(saved.getMovieTicketId());
        assertEquals(showSeat.getShowSeatId(), saved.getSeat().getShowSeatId());
        assertEquals(170.00, saved.getPrice());
    }

    @Test
    void findAll_returnsAlltickets() {
        List<MovieTicket> ticketsBefore =  ticketRepository.findAll();
        MovieTicket t1 = new MovieTicket(125.00, showSeat, TicketType.ADULT);
        MovieTicket t2 = new MovieTicket(150.00, showSeat, TicketType.ADULT);
        MovieTicket t3 = new MovieTicket(165.00, showSeat, TicketType.ADULT);
        MovieTicket t4 = new MovieTicket(180.00, showSeat, TicketType.ADULT);

        ticketRepository.save(t1);
        ticketRepository.save(t2);
        ticketRepository.save(t3);
        ticketRepository.save(t4);

        List<MovieTicket> tickets = ticketRepository.findAll();

        assertEquals(ticketsBefore.size() + 4, tickets.size());
    }
    @Test
    void findById_returnsCorrectTicket() {
        MovieTicket ticket = new MovieTicket();
        ticket.setSeat(showSeat);

        MovieTicket saved = ticketRepository.save(ticket);

        MovieTicket found = ticketRepository.findById(saved.getMovieTicketId()).orElseThrow();

        assertEquals(saved.getMovieTicketId(), found.getMovieTicketId());
    }

    @Test
    void deleteTicket_removesTicket() {
        List<MovieTicket> ticketsBefore = ticketRepository.findAll();
        MovieTicket ticket = new MovieTicket();
        ticket.setSeat(showSeat);

       MovieTicket saved = ticketRepository.save(ticket);
       assertEquals(ticketsBefore.size() + 1, ticketRepository.findAll().size());
       ticketRepository.delete(saved);
       assertEquals(ticketsBefore.size(), ticketRepository.findAll().size());
    }
}
