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

    public List <Seat> findAllSeats() {
        return seatRepository.findAll();
    }

    public Seat findSeatById(Long seatId) {
        return seatRepository.findById(seatId).orElseThrow(() -> new IllegalStateException("seat " + seatId + " not found"));
    }
}
