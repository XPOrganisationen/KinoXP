package com.xp.Repository;

import com.xp.Model.SeatAvailability;
import com.xp.Model.Show;
import com.xp.Model.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatRepository extends JpaRepository <ShowSeat, Long> {

    List<ShowSeat> findAllByShow(Show show);

    List<ShowSeat> findAllByShowAndSeatAvailability(Show show, SeatAvailability seatAvailability);
}
