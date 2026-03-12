package com.xp.Repository;

import com.xp.Model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository <Reservation, Long> {
}
