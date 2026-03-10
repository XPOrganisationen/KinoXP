package com.xp;

import com.xp.Model.Cinema;
import com.xp.Model.Movie;
import com.xp.Model.Show;
import com.xp.Model.Theater;
import com.xp.Repository.ShowRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class ShowRepositoryTests {
    @Autowired
    private ShowRepository showRepository;

    @Test
    void findAllByMovieIdFindsAllMoviesWithGivenId() {
        List<Show> expectedShows = List.of(
            new Show(
                    4L,
                new Movie(
                    3L,
                      "Horror Night",
                      "Spine-chilling horror",
                      100.0,
                      "Horror",
                      18,
                      false
                ),
                new Theater(
                        2L,
                        new Cinema(
                               1L,
                                "Downtown Cinema",
                                "123 Main St"
                        ),
                    "Screen 2",
                        3,
                        4
                ),
                LocalDateTime.now().plusMinutes(30)
            )
        );

        List<Show> actualShows = showRepository.findAllByMovieId(3L);
        org.junit.jupiter.api.Assertions.assertEquals(expectedShows, actualShows);
    }
}
