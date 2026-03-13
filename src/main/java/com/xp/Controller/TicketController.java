package com.xp.Controller;


import com.xp.Model.*;
import com.xp.Model.DTOs.TicketSales;
import com.xp.Service.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tickets/")
public class TicketController {

    private final TicketService ticketService;
    private final SeatService seatService;
    private final MovieService movieService;
    private final ShowService showService;

    public TicketController(TicketService ticketService, SeatService seatService, MovieService movieService, ShowService showService) {
        this.ticketService = ticketService;
        this.seatService = seatService;
        this.movieService = movieService;
        this.showService = showService;
    }

    @GetMapping
    public List<MovieTicket> getAllTickets() {
        return ticketService.findAllTickets();
    }

    @GetMapping("/id/{id}")
    public MovieTicket getTicketsById(@PathVariable Long id) {
        return ticketService.findMovieTicketById(id);
    }

    @GetMapping("/salesPerMovie")
    public List<TicketSales> getTicketSalesPerMovie() {
        List<Movie> movies = movieService.findAll();
        List<TicketSales> salesList = new ArrayList<>();

        List<MovieTicket> allTickets = ticketService.findAllTickets();

        for (Movie movie : movies) {
            List<Show> shows = showService.findAllByMovieId(movie.getMovieId());
            int totalTicketsSold = 0;
            double totalRevenue = 0;

            for (Show show : shows) {
                for (MovieTicket ticket : allTickets) {
                    if (ticket.getSeat().getShow().getShowId().equals(show.getShowId())) {
                        totalTicketsSold++;
                        totalRevenue += ticket.getPrice();
                    }
                }
            }
            salesList.add(new TicketSales(movie.getMovieTitle(), totalTicketsSold, totalRevenue));
        }
        return salesList;
    }

    @PostMapping("/reserve")
    public MovieTicket reserveTicket(@RequestParam Long seatId,
                                     @RequestParam TicketType ticketType) {

        ShowSeat showSeat = seatService.findSeatById(seatId);
        return ticketService.createTicket(showSeat, ticketType);
                }

    @GetMapping("all-ticket-types")
    public List<TicketType> getAllTicketTypes() {
     return List.of(TicketType.values());
    }
}