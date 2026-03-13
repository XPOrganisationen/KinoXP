package com.xp.Service;

import com.xp.Model.*;
import com.xp.Repository.ReservationRepository;
import com.xp.Repository.SeatRepository;
import com.xp.Repository.ShowRepository;
import com.xp.Repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    public final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository, SeatRepository seatRepository, ShowRepository showRepository, ReservationRepository reservationRepository) {
        this.ticketRepository = ticketRepository;
    }


    public List <MovieTicket> findAllTickets() {
        return ticketRepository.findAll();
    }

    public MovieTicket findMovieTicketById(Long id) {
        return ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie ticket not found"));
    }

    @Transactional
    public MovieTicket createTicket(ShowSeat showSeat, TicketType ticketType) {

        boolean taken = showSeat.getSeatAvailability() == SeatAvailability.RESERVED;

        if (taken) {
            throw new IllegalStateException("Seat already taken for this viewing");
        }

        double seatPrice = showSeat.getSeat().getSeatType().getPriceAdjustment();
        double ticketPrice = ticketType.getPrice();
        Movie movie = showSeat.getShow().getMovie();
        double longMovieFee = movie.getMovieDuration() >= 170 ? 15 : 0;
        double is3dFee = movie.is_3d() ? 10 : 0;
        double price = seatPrice + ticketPrice + longMovieFee + is3dFee;

        MovieTicket ticket = new MovieTicket();
        ticket.setSeat(showSeat);
        ticket.setPrice(price);
        ticket.setTicketType(ticketType);
        return ticketRepository.save(ticket);
    }
}
