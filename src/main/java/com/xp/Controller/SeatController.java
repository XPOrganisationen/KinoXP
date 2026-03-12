package com.xp.Controller;


import com.xp.Model.ShowSeat;
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
    public List<ShowSeat> getAllSeats() {
        return seatService.findAllSeats();
    }

    @GetMapping("/{id}")
    public ShowSeat getSeatById(@PathVariable Long id) {
    return seatService.findSeatById(id);
    }

    @GetMapping("/show/{showId}")
    public List<ShowSeat> getSeatsForShow(@PathVariable Long showId) {
        Show show = ticketService.findShowById(showId);
        return seatService.getSeatsForShow(show);
    }
}
