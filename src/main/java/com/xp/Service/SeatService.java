package com.xp.Service;

import com.xp.Model.SeatAvailability;
import com.xp.Model.Show;
import com.xp.Model.ShowSeat;
import com.xp.Repository.SeatRepository;
import com.xp.Repository.ShowRepository;
import com.xp.Repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class SeatService {

    public final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;
    private final ShowRepository showRepository;

    public SeatService(SeatRepository seatRepository, TicketRepository ticketRepository, ShowRepository showRepository) {
        this.seatRepository = seatRepository;
        this.ticketRepository = ticketRepository;
        this.showRepository = showRepository;
    }

    public List <ShowSeat> findAllSeats() {
        return seatRepository.findAll();
    }

    public ShowSeat findSeatById(Long seatId) {
        return seatRepository.findById(seatId).orElseThrow(() -> new IllegalStateException("seat " + seatId + " not found"));
    }

    public List<ShowSeat> getSeatsForShow(Show show) {
        return seatRepository.findAllByShow(show);
    }

    @Transactional
    public void changeSeatTypeIfAdmin(Long showId, Long seatId, SeatAvailability newAvailability) {
        Show show = showRepository.findById(showId).get();
        ShowSeat showSeat = findSeatById(seatId);

        if (showSeat.getSeatAvailability() == SeatAvailability.RESERVED) {
            throw new IllegalArgumentException("Cannot change seat type if type = reserved");
        }

        List<ShowSeat> showSeats = getSeatsForShow(show);

        for (ShowSeat s : showSeats) {
            if (s.getShowSeatId().equals(seatId)) {
                s.setSeatAvailability(newAvailability);
            }
        }
    }

    public ShowSeat updateSeat(ShowSeat showSeat) {
        if (!seatRepository.existsById(showSeat.getShowSeatId())) {
            throw new IllegalStateException("No seat exists with ID " + showSeat.getShowSeatId());
        }

        return seatRepository.save(showSeat);
    }

    public void deleteSeat(Long seatId) {
        if (!seatRepository.existsById(seatId)) {
            throw new IllegalStateException("No seat exists with ID " + seatId);
        }

        seatRepository.deleteById(seatId);
    }
}
