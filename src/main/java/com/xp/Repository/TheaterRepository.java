package com.xp.Repository;

import com.xp.Model.DTOs.Seat;
import com.xp.Model.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TheaterRepository extends JpaRepository <Theater, Long> {
}
