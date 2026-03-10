package com.xp.Repository;

import com.xp.Model.MovieTicket;
import com.xp.Model.Seat;
import com.xp.Model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository <MovieTicket, Long> {
    boolean existsByShowAndSeat(Show show, Seat seat);

    List<MovieTicket> findByShow(Show show);
}
