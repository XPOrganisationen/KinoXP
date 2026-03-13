package com.xp.Service;

import com.xp.Model.Reservation;

import java.util.List;

public interface ReservationService {
    public List<Reservation> getAllReservations();
    public Reservation getReservationById(Long reservationId);
    public Reservation addReservation(Reservation reservation);
    public Reservation updateReservation(Reservation reservation);
    public void deleteReservation(Long reservationId);
    public Double calculateTotalPrice(Reservation reservation);
}
