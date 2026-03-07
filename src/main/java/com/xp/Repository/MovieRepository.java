package com.xp.Repository;

import com.xp.Model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository <Movie, Long> {
    List<Movie> findAllByMovieTitleContainingIgnoreCase(String movieTitle);

    @Query(value = """
            SELECT m.*
            FROM movies m
            LEFT JOIN (
            	SELECT m.movie_id, COUNT(mt.movie_ticket_id) AS tickets_sold
            	FROM movies m
            	LEFT JOIN shows s ON s.movie_id = m.movie_id
            	LEFT JOIN reservations r ON r.show_id = s.show_id
            	LEFT JOIN movie_tickets mt ON mt.reservation_id = r.reservation_id
            	GROUP BY m.movie_id
            ) t ON t.movie_id = m.movie_id
            ORDER BY COALESCE(t.tickets_sold, 0) DESC;
            """, nativeQuery = true) // Whew.
    List<Movie> findAllOrderByPopularityDesc(); // Popularity = Tickets sold

    @Query(value = """
           SELECT m.*
           FROM movies m
           JOIN (
                SELECT s.movie_id, MIN(s.start_time) AS next_start_time
                FROM shows s
                JOIN theaters th ON th.theater_id = s.theater_id
                LEFT JOIN (
                    SELECT mt.show_id, COUNT(*) AS sold_tickets
                    FROM movie_tickets mt
                    GROUP BY mt.show_id
                ) sold ON sold.show_id = s.show_id
                WHERE s.start_time > NOW()
                AND (COALESCE(th.number_of_rows,0) * COALESCE(th.seats_per_row,0)) > COALESCE(sold.sold_tickets,0)
                GROUP BY s.movie_id
           ) avail ON avail.movie_id = m.movie_id
           ORDER BY TIMESTAMPDIFF(MINUTE, NOW(), avail.next_start_time) ASC;
           """, nativeQuery = true)
    List<Movie> findAllOrderByAvailableSeatsShownSoon();

    @Query(value = """
            SELECT m.*
            FROM movies m
            WHERE m.movie_category IN (:categories)
            GROUP BY m.movie_id;
            """, nativeQuery = true)
    List<Movie> findAllMatchingCategoryList(@Param("categories") List<String> categories);
    // Finds all movies which fit one of the categories passed by the user.

    List<Movie> findAllByAgeLimitLessThanEqual(Integer ageLimit);
}
