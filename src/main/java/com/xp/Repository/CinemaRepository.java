package com.xp.Repository;

import com.xp.Model.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepository extends JpaRepository <Cinema, Long> {
}
