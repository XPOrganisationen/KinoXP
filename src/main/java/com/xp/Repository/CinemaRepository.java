package com.xp.Repository;

import com.xp.Model.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CinemaRepository extends JpaRepository <Cinema, Long>
{
    Optional<Cinema> findByCinemaNameIgnoreCase(String cinemaName);

    List<Cinema> findByCinemaNameContainingIgnoreCase(String cinemaName);

    List<Cinema> findByCinemaAddressContainingIgnoreCase(String cinemaAddress);

    @Query("""
        SELECT DISTINCT c
        FROM Cinema c
        LEFT JOIN Theater t ON t.cinema = c
        ORDER BY c.cinemaName ASC
    """)
    List<Cinema> findAllWithTheaters();
}
