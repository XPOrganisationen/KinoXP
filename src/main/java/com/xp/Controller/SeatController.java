package com.xp.Controller;


import com.xp.Model.Seat;
import com.xp.Model.Show;
import com.xp.Service.SeatService;
import com.xp.Service.TicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats/")
public class SeatController {

    private final SeatService seatService;
    private final TicketService ticketService;

    public SeatController(SeatService seatService, TicketService ticketService) {
        this.seatService = seatService;
        this.ticketService = ticketService;
    }

    @GetMapping
    public List<Seat> getAllSeats() {
        return seatService.findAllSeats();
    }

    @GetMapping("/{id}")
    public Seat getSeatById(@PathVariable Long id) {
    return seatService.findSeatById(id);
    }

    @GetMapping("/show/{showId}")
    public List<Seat> getSeatsForShow(@PathVariable Long showId) {
        Show show = ticketService.findShowById(showId);
        return ticketService.getSeatsForShow(show);
    }
}
