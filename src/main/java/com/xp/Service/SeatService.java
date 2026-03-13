package com.xp.Service;

import com.xp.Model.SeatAvailability;
import com.xp.Model.Show;
import com.xp.Model.ShowSeat;
import com.xp.Repository.ShowSeatRepository;
import com.xp.Repository.ShowRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService {

    public final ShowSeatRepository showSeatRepository;
    private final ShowRepository showRepository;

    public SeatService(ShowSeatRepository showSeatRepository, ShowRepository showRepository) {
        this.showSeatRepository = showSeatRepository;
        this.showRepository = showRepository;
    }

    public List <ShowSeat> findAllSeats() {
        return showSeatRepository.findAll();
    }

    public ShowSeat findSeatById(Long seatId) {
        return showSeatRepository.findById(seatId).orElseThrow(() -> new IllegalStateException("seat " + seatId + " not found"));
    }

    public List<ShowSeat> getSeatsForShow(Show show) {
        return showSeatRepository.findAllByShow(show);
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
        if (!showSeatRepository.existsById(showSeat.getShowSeatId())) {
            throw new IllegalStateException("No seat exists with ID " + showSeat.getShowSeatId());
        }

        return showSeatRepository.save(showSeat);
    }

    public void deleteSeat(Long seatId) {
        if (!showSeatRepository.existsById(seatId)) {
            throw new IllegalStateException("No seat exists with ID " + seatId);
        }

        showSeatRepository.deleteById(seatId);
    }
}
