package com.xp.Repository;

import com.xp.Model.SeatAvailability;
import com.xp.Model.Show;
import com.xp.Model.ShowSeat;
import com.xp.Model.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository <ShowSeat, Long> {

    List<ShowSeat> findAllByShow(Show show);

    List<ShowSeat> findAllByShowAndSeatAvailability(Show show, SeatAvailability seatAvailability);

    List<ShowSeat> findAllBySeatTheater(Theater theater);

    List<ShowSeat> findAllBySeatTheaterAndSeatAvailability(Theater theater, SeatAvailability seatAvailability);
}
