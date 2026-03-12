package com.xp.Controller;


import com.xp.Model.SeatAvailability;
import com.xp.Model.ShowSeat;
import com.xp.Model.Show;
import com.xp.Service.SeatService;
import com.xp.Service.ShowService;
import com.xp.Service.TicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats/")
public class SeatController {

    private final SeatService seatService;
    private final TicketService ticketService;
    private final ShowService showService;

    public SeatController(SeatService seatService, TicketService ticketService, ShowService showService) {
        this.seatService = seatService;
        this.ticketService = ticketService;
        this.showService = showService;
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
        Show show = showService.findById(showId);
        return seatService.getSeatsForShow(show);
    }

    @GetMapping("get-all-seat-availability-options")
    public List<SeatAvailability> getAllSeatAvailabilityOptions() {
        return List.of(SeatAvailability.values());
    }
}
