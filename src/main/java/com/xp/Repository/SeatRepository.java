package com.xp.Repository;

import com.xp.Model.Seat;
import com.xp.Model.SeatAvailability;
import com.xp.Model.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository <Seat, Long> {
    List<Seat> findByTheater(Theater theater);

    List<Seat> findByTheaterAndSeatAvailability(Theater theater, SeatAvailability seatAvailability);
}
