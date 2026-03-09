package com.xp.Controller;


import com.xp.Model.Seat;
import com.xp.Service.SeatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/(seats?)")
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping
    public List<Seat> getAllSeats() {
        return seatService.findAllSeats();
    }

    @GetMapping("/{id}")
    public Seat getSeatById(@PathVariable Long id) {
    return seatService.findSeatById(id);
    }

    @PostMapping("/{id}/reserve")
    public String reserveSeat (@PathVariable Long id) {
        seatService.reserveSeat(id);
        return "seat reserved successfully!";
    }
}
