package com.xp.Controller;


import com.xp.Model.*;
import com.xp.Service.SeatService;
import com.xp.Service.TicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets/")
public class TicketController {

    private final TicketService ticketService;
    private final SeatService seatService;

    public TicketController(TicketService ticketService, SeatService seatService) {
        this.ticketService = ticketService;
        this.seatService = seatService;
    }

    @GetMapping
    public List<MovieTicket> getAllTickets() {
        return ticketService.findAllTickets();
    }

    @GetMapping("/{id}")
    public MovieTicket getTicketsById(@PathVariable Long id) {
        return ticketService.findMovieTicketById(id);
    }

    @PostMapping("/reserve")
    public MovieTicket reserveTicket(@RequestParam Long seatId,
                                     @RequestParam TicketType ticketType) {

        ShowSeat showSeat = seatService.findSeatById(seatId);
        return ticketService.createTicket(showSeat, ticketType);
    }

    @PostMapping("/show/{showId}/seat/{seatId}/override")
    public String overrideSeatForShow(@PathVariable Long showId,
                                      @PathVariable Long seatId,
                                      @RequestParam SeatAvailability newAvailability) {

        //check for admin here (if we get to it)

        seatService.changeSeatTypeIfAdmin(showId, seatId, newAvailability);
        return "Seat-type changed for this show";
    }

    @GetMapping("all-ticket-types")
    public List<TicketType> getAllTicketTypes() {
        return List.of(TicketType.values());
    }

    @PostMapping
    public Double getTicketPrice(@RequestBody MovieTicket movieTicket) {
        return ticketService.getTicketPrice(movieTicket.getSeat(), movieTicket.getTicketType());
    }
}