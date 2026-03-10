package com.xp.Service;

import com.xp.Model.*;
import com.xp.Model.DTOs.Seat;
import com.xp.Repository.SeatRepository;
import com.xp.Repository.ShowRepository;
import com.xp.Repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    public final TicketRepository ticketRepository;
    private final SeatRepository seatRepository;
    private final ShowRepository showRepository;

    public TicketService(TicketRepository ticketRepository, SeatRepository seatRepository, ShowRepository showRepository) {
        this.ticketRepository = ticketRepository;
        this.seatRepository = seatRepository;
        this.showRepository = showRepository;
    }


    public List <MovieTicket> findAllTickets() {
        return ticketRepository.findAll();
    }

    public MovieTicket findMovieTicketById(Long id) {
        return ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie ticket not found"));
    }

    public double calculateTotalPrice(TicketType ticketType, SeatType seatType, int quantity, Movie movie) {

        double pricePerTicket = ticketType.getPrice(); // priser for de diverse biletter findes i TicketType Enum :)

        pricePerTicket += seatType.getPriceAdjustment(); // vi tilføjer prisen af typen af sæde valgt med prisen for movie ticket

        if(movie.getMovieDuration() >= 170) { // hvis film er længere en 170 minutter tilføjes der ekstra gebyr på biletten
            pricePerTicket += 15;
        }

        if (movie.is_3d()) { // samme som før, en 3D film koster lidt ekstra
            pricePerTicket +=10;
        }

        double total = pricePerTicket * quantity; // pris for 1 billet ganget (*) nummeret af biletter valgt

        if (quantity <=5) { // hvis 5 eller færre biletter er købt
            total *= 1.05; // Registration fee: + 5% af total price
        } else if (quantity >=10) { // hvis 10 eller flere biletter er købt
            total *= 0.93; // discount: - 7% af total price
        }

        return total; // returnerer det endelige beløb
    }

    @Transactional
    public MovieTicket createTicket(Show show, ShowSeat showSeat, TicketType ticketType) {

        boolean taken = ticketRepository.existsByShowAndSeat(show, showSeat);

        if (taken) {
            throw new IllegalStateException("Seat already taken for this viewing");
        }

        double price = calculateTotalPrice(ticketType, showSeat.getSeat().getSeatType(), 1, show.getMovie());

        MovieTicket ticket = new MovieTicket();
        ticket.setSeat(showSeat);
        ticket.setShow(show);
        ticket.setPrice(price);

        return ticketRepository.save(ticket);
    }

    public Show findShowById(Long showId) {
        return showRepository.findById(showId).orElseThrow(() -> new RuntimeException("Show not found with id -> " + showId));
    }

    public ShowSeat findSeatById(Long seatId) {
        return seatRepository.findById(seatId).orElseThrow(() -> new RuntimeException("Seat not found with it ->" + seatId));
    }
}
