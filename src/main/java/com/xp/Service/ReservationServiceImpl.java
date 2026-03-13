package com.xp.Service;

import com.xp.Exceptions.EntityDoesNotExistException;
import com.xp.Model.Movie;
import com.xp.Model.MovieTicket;
import com.xp.Model.Reservation;
import com.xp.Model.Show;
import com.xp.Repository.ReservationRepository;
import com.xp.Repository.ShowRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final ShowRepository showRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, ShowRepository showRepository) {
        this.reservationRepository = reservationRepository;
        this.showRepository = showRepository;
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId).orElseThrow(() -> new EntityDoesNotExistException("No reservation exists with ID " + reservationId));
    }

    @Override
    public Reservation addReservation(Reservation reservation) {
        reservation.setTotalPrice(calculateTotalPrice(reservation));
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation updateReservation(Reservation reservation) {
        if (!reservationRepository.existsById(reservation.getReservationId())) {
            throw new EntityDoesNotExistException("No reservation exists with ID " + reservation.getReservationId());
        }

        return this.reservationRepository.save(reservation);
    }

    @Override
    public void deleteReservation(Long reservationId) {
        if (!reservationRepository.existsById(reservationId)) {
            throw new EntityDoesNotExistException("No reservation exists with ID " + reservationId);
        }

        reservationRepository.deleteById(reservationId);
    }

    @Override
    public Double calculateTotalPrice(Reservation reservation) {
        List<MovieTicket> movieTickets = reservation.getMovieTickets();
        if (movieTickets.isEmpty()) {
            return 0.0;
        }

        double totalPrice = 0.0;
        double basePriceMultiplier = movieTickets.size() <= 5 ? 1.05 : movieTickets.size() >= 10 ? 0.93 : 1.0;

        for (MovieTicket movieTicket : movieTickets) {
            totalPrice += movieTicket.getPrice();
        }

        return totalPrice * basePriceMultiplier;
    }
}
