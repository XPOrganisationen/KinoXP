package com.xp.Controller;


import com.xp.Model.Movie;
import com.xp.Model.MovieTicket;
import com.xp.Model.SeatType;
import com.xp.Model.TicketType;
import com.xp.Service.TicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/(tickets?)")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public List<MovieTicket> getAllTickets() {
        return ticketService.findAllTickets();
    }

    @GetMapping("/{id}")
    public MovieTicket getTicketsById(@PathVariable Long id) {
        return ticketService.findMovieTicketById(id);
    }

    @PostMapping("/calculate")
    public double calculateTotalPrice(@RequestParam TicketType ticketType,
                                      @RequestParam SeatType seatType,
                                      @RequestParam int quantity,
                                      @RequestBody Movie movie)
    {
        return ticketService.calculateTotalPrice(ticketType, seatType, quantity, movie);
    }
}