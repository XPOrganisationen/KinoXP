package com.xp;

import com.xp.Model.Seat;
import com.xp.Model.SeatAvailability;
import com.xp.Model.Theater;
import com.xp.Repository.SeatRepository;
import com.xp.Service.SeatService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TestSeatService {

    private SeatRepository seatRepository;
    private SeatService seatService;


    @BeforeEach
    void setUp() {
        seatRepository = mock(SeatRepository.class);
        seatService = new SeatService(seatRepository);
    }

    @Test
    void reserveSeat_success() {
        Seat seat = new Seat();
        seat.setSeatAvailability(SeatAvailability.VACANT);

        when(seatRepository.findById(1L)).thenReturn(Optional.of(seat));

        seatService.reserveSeat(1L);

        assertEquals(SeatAvailability.RESERVED, seat.getSeatAvailability());
        verify(seatRepository).save(seat);
    }

    @Test
    void reserveSeat_alreadyReserved_throwsException() {
        Seat seat = new Seat();
        seat.setSeatAvailability(SeatAvailability.RESERVED);

        when(seatRepository.findById(1L)).thenReturn(Optional.of(seat));

        assertThrows(IllegalStateException.class, () -> {
            seatService.reserveSeat(1L);
        });
    }

    @Test
    void reserveSeat_handicapSeat_throwsException() {

        Seat seat = new Seat();
        seat.setSeatAvailability(SeatAvailability.HANDICAP);

        when(seatRepository.findById(1L)).thenReturn(Optional.of(seat));

        assertThrows(RuntimeException.class, () -> {
            seatService.reserveSeat(1L);
        });
    }

    @Test
    void findAllSeats_returnsSeats() {

        List<Seat> seats = List.of(new Seat(), new Seat());

        when(seatRepository.findAll()).thenReturn(seats);

        List<Seat> result = seatService.findAllSeats();

        assertEquals(2, result.size());
        verify(seatRepository).findAll();
    }

    @Test
    void findSeatById_returnsSeat() {

        Seat seat = new Seat();

        when(seatRepository.findById(1L)).thenReturn(Optional.of(seat));

        Seat result = seatService.findSeatById(1L);

        assertEquals(seat, result);
        verify(seatRepository).findById(1L);
    }

    @Test
    void reserveSeat_outOfService_throwsException() {

        Seat seat = new Seat();
        seat.setSeatAvailability(SeatAvailability.OUT_OF_SERVICE);

        when(seatRepository.findById(1L)).thenReturn(Optional.of(seat));

        assertThrows(IllegalStateException.class, () -> {
            seatService.reserveSeat(1L);
        });
    }


}
