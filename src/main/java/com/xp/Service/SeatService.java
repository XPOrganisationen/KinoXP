package com.xp.Service;

import com.xp.Model.Seat;
import com.xp.Model.SeatAvailability;
import com.xp.Repository.SeatRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class SeatService {

    public final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }
    @Transactional
    public void reserveSeat(Long seatId) {
        Seat seat = findSeatById(seatId);

        if (seat.getSeatAvailability() == SeatAvailability.RESERVED) { // tjekker om sædet som er valgt har value = RESERVED (optaget)
            throw new IllegalStateException("seat already taken"); // hvis ja, kommer der besked om at den er optaget
        } else if (seat.getSeatAvailability() == SeatAvailability.HANDICAP) { // særlig besked hvis handicap sæde er valgt som forklarer brugeren
            throw new RuntimeException("Dette er et Handicap sæde, hver klar til at vise Handicap kort ved indgangen til salen");
        } else if (seat.getSeatAvailability() == SeatAvailability.OUT_OF_SERVICE) {
            throw new IllegalStateException("This seat is out of service");
        }

        seat.setSeatAvailability(SeatAvailability.RESERVED); // hvis sædet != RESERVED (ikke er optaget) så ville det sæde som er valgt få typen Reserved (fordi du lige har valgt den)
        seatRepository.save(seat); // gemmer de(t) valgte sæde(r) i databasen
    }

    public List <Seat> findAllSeats() {
        return seatRepository.findAll();
    }

    public Seat findSeatById(Long seatId) {
        return seatRepository.findById(seatId).orElseThrow(() -> new IllegalStateException("seat " + seatId + " not found"));
    }
}
