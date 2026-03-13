package com.xp.Controller;

import com.xp.Model.MovieTicket;
import com.xp.Model.Reservation;
import com.xp.Service.ReservationService;
import com.xp.Service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    private final ReservationService reservationService;
    private final TicketService ticketService;

    public ReservationController(ReservationService reservationService, TicketService ticketService) {
        this.reservationService = reservationService;
        this.ticketService = ticketService;
    }

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/{reservationId}")
    public Reservation getReservationById(@PathVariable Long reservationId) {
        return reservationService.getReservationById(reservationId);
    }

    @PostMapping
    public Reservation addReservation(@RequestBody Reservation reservation) {
        List<MovieTicket> savedMovieTickets = new ArrayList<>();
        for (MovieTicket movieTicket : reservation.getMovieTickets()) {
            savedMovieTickets.add(ticketService.createTicket(movieTicket.getSeat(), movieTicket.getTicketType()));
        }

        reservation.setMovieTickets(savedMovieTickets);
        return reservationService.addReservation(reservation);
    }

    @PutMapping
    public ResponseEntity<Reservation> updateReservation(@RequestBody Reservation reservation) {
        return ResponseEntity.created(URI.create("/api/reservations/" + reservation.getReservationId())).body(reservationService.updateReservation(reservation));
    }

    @DeleteMapping
    public ResponseEntity<Void>  deleteReservation(@RequestBody Long reservationId) {
        reservationService.deleteReservation(reservationId);
        return ResponseEntity.noContent().build();
    }
}
