package com.xp;

import com.xp.Model.ShowSeat;
import com.xp.Repository.ShowSeatRepository;
import com.xp.Repository.ShowRepository;
import com.xp.Service.SeatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TestShowSeatService {

    private ShowSeatRepository showSeatRepository;
    private SeatService seatService;


    @BeforeEach
    void setUp() {
        showSeatRepository = mock(ShowSeatRepository.class);
        ShowRepository showRepository = mock(ShowRepository.class);
        seatService = new SeatService(showSeatRepository, showRepository);
    }

    /*@Test
    void reserveSeat_success() {
        Seat seat = new Seat();
        seat.setSeatAvailability(SeatAvailability.VACANT);

        when(seatRepository.findById(1L)).thenReturn(Optional.of(seat));

        seatService.reserveSeat(1L);

        assertEquals(SeatAvailability.RESERVED, seat.getSeatAvailability());
        verify(seatRepository).save(seat);
    }

     */

    /*@Test
    void reserveSeat_alreadyReserved_throwsException() {
        Seat seat = new Seat();
        seat.setSeatAvailability(SeatAvailability.RESERVED);

        when(seatRepository.findById(1L)).thenReturn(Optional.of(seat));

        assertThrows(IllegalStateException.class, () -> {
            seatService.reserveSeat(1L);
        });
    }

     */

    /*@Test
    void reserveSeat_handicapSeat_throwsException() {

        Seat seat = new Seat();
        seat.setSeatAvailability(SeatAvailability.HANDICAP);

        when(seatRepository.findById(1L)).thenReturn(Optional.of(seat));

        assertThrows(RuntimeException.class, () -> {
            seatService.reserveSeat(1L);
        });
    }

     */

    @Test
    void findAllSeats_returnsSeats() {

        List<ShowSeat> showSeats = List.of(new ShowSeat(), new ShowSeat());

        when(showSeatRepository.findAll()).thenReturn(showSeats);

        List<ShowSeat> result = seatService.findAllSeats();

        assertEquals(2, result.size());
        verify(showSeatRepository).findAll();
    }

    @Test
    void findSeatById_returnsSeat() {

        ShowSeat showSeat = new ShowSeat();

        when(showSeatRepository.findById(1L)).thenReturn(Optional.of(showSeat));

        ShowSeat result = seatService.findSeatById(1L);

        assertEquals(showSeat, result);
        verify(showSeatRepository).findById(1L);
    }

    /*@Test
    void reserveSeat_outOfService_throwsException() {

        Seat seat = new Seat();
        seat.setSeatAvailability(SeatAvailability.OUT_OF_SERVICE);

        when(seatRepository.findById(1L)).thenReturn(Optional.of(seat));

        assertThrows(IllegalStateException.class, () -> {
            seatService.reserveSeat(1L);
        });
    }

     */


}
