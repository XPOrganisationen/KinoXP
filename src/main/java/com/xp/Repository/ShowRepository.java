package com.xp.Repository;

import com.xp.Model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {
    @Query("""
            SELECT s
            FROM Show s
            WHERE s.movie.movieId = :movieId
            """)
    public List<Show> findAllByMovieId(Long movieId);
}
