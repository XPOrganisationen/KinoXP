package com.xp;

import com.xp.Model.Movie;
import com.xp.Repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.stream.Collectors;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
public class MovieRepositoryTests {
    @Autowired
    private MovieRepository movieRepository;

    @Test
    void findAllByMovieTitleIgnoreCaseFindsTitleByEnding() {
        Movie spaceAdventure = movieRepository.findById(1L).get();
        List<Movie> movies = movieRepository.findAllByMovieTitleContainingIgnoreCase("Adventure");
        org.junit.jupiter.api.Assertions.assertEquals(spaceAdventure, movies.getFirst());
        org.junit.jupiter.api.Assertions.assertEquals(1, movies.size());
    }

    @Test
    void findAllByMovieTitleIgnoreCaseFindsTitleByBeginning() {
        Movie spaceAdventure = movieRepository.findById(1L).get();
        List<Movie> movies = movieRepository.findAllByMovieTitleContainingIgnoreCase("Space");
        org.junit.jupiter.api.Assertions.assertEquals(spaceAdventure, movies.getFirst());
        org.junit.jupiter.api.Assertions.assertEquals(1, movies.size());
    }

    @Test
    void findAllByMovieTitleIgnoreCaseFindsTitleWithNonMatchingCasing() {
        Movie spaceAdventure = movieRepository.findById(1L).get();
        List<Movie> movies = movieRepository.findAllByMovieTitleContainingIgnoreCase("space");
        org.junit.jupiter.api.Assertions.assertEquals(spaceAdventure, movies.getFirst());
        org.junit.jupiter.api.Assertions.assertEquals(1, movies.size());
    }

    @Test
    void findAllByMovieTitleIgnoreCaseFindsPartialMatch() {
        List<Movie> moviesWithTheInTitle = List.of(
            new Movie(
                    1L,
                    "Space Adventure",
                    "Sci-fi epic",
                    130.0,
                    "Science Fiction",
                    12,
                    true
            ),
            new Movie(
                    4L,
                    "Documentary: Nature",
                    "Wildlife documentary",
                    60.0,
                    "Documentary",
                    0,
                    false
            )
        );

        List<Movie> actualMovies = movieRepository.findAllByMovieTitleContainingIgnoreCase("ture"); // Adven*, Na*
        org.junit.jupiter.api.Assertions.assertTrue(actualMovies.equals(moviesWithTheInTitle));
        org.junit.jupiter.api.Assertions.assertEquals(2, actualMovies.size());
    }

    @Test
    void findAllByMovieTitleIgnoreCaseReturnsEmptyListOnNoMatches() {
        org.junit.jupiter.api.Assertions.assertEquals(List.of(), movieRepository.findAllByMovieTitleContainingIgnoreCase("abcde"));
    }

    @Test
    void findAllByAgeLimitLEQReturnsOnlyMoviesWithAgeRatingBelowOrEqualToAgeLimit() {
        List<Movie> moviesWithAgeRatingsLEQ15 = List.of(
            new Movie(
                    2L,
                    "Romantic Comedy",
                    "Light romance",
                    95.0,
                    "Romantic Comedy",
                    10,
                    false
            ),
            new Movie(
                    4L,
                    "Documentary: Nature",
                    "Wildlife documentary",
                    60.0,
                    "Documentary",
                    0,
                    false
            ),
            new Movie(
                    5L,
                    "Animated Fun",
                    "Family animation",
                    80.0,
                    "Animation",
                    0,
                    true
            )
        );

        var actual = movieRepository.findAllByAgeLimitLessThanEqual(10);
        org.junit.jupiter.api.Assertions.assertTrue(moviesWithAgeRatingsLEQ15.containsAll(actual));
        org.junit.jupiter.api.Assertions.assertEquals(3, actual.size());
    }

    @Test // Order does not fit dev-database so hard to verify
    public void findAllOrderByPopularityDescReturnsInMostTicketsSoldFirstOrder() {
        List<Long> movieIdsInDesiredOrder = List.of(3L, 1L, 2L, 4L, 5L);
        org.junit.jupiter.api.Assertions.assertEquals(movieIdsInDesiredOrder,
                movieRepository
                        .findAllOrderByPopularityDesc()
                        .stream()
                        .map(Movie::getMovieId)
                        .collect(Collectors.toList())
        );
    }

    @Test // See lines 52-64 in data.sql to confirm order.
          // Also note the fact that no duplicates (movie with ID 1) are included.
    public void findAllOrderByAvailableSeatsShownSoonReturnsListOfMoviesWithTicketsAvailableInOrderOfClosestShowingTime() {
        List<Long> movieIdsInDesiredOrder = List.of(5L, 3L, 1L, 4L, 2L);
        org.junit.jupiter.api.Assertions.assertEquals(movieIdsInDesiredOrder,
                movieRepository
                        .findAllOrderByAvailableSeatsShownSoon()
                        .stream()
                        .map(Movie::getMovieId)
                        .collect(Collectors.toList())
        );
    }

    @Test
    public void findAllMatchingCategoryListReturnsEmptyListOnNoCategories() {
        org.junit.jupiter.api.Assertions.assertEquals(List.of(), movieRepository.findAllMatchingCategoryList(List.of()));
    }

    @Test
    public void findAllMatchingCategoryListReturnsMoviesWithMatchingCategoryOnOneCategory() {
        List<Movie> expectedMovies = List.of(
            new Movie(
                    3L,
                    "Horror Night",
                    "Spine-chilling horror",
                    100.0,
                    "Horror",
                    18,
                    false
            )
        );
        org.junit.jupiter.api.Assertions.assertEquals(expectedMovies, movieRepository.findAllMatchingCategoryList(List.of("Horror")));
    }

    @Test
    public void findAllMatchingCategoryListReturnsMoviesWithMatchingCategoryOnMultipleCategories() {
        List<Movie> expectedMovies = List.of(
                new Movie(
                        1L,
                        "Space Adventure",
                        "Sci-fi epic",
                        130.0,
                        "Science Fiction",
                        12,
                        true
                ),
                new Movie(
                        3L,
                        "Horror Night",
                        "Spine-chilling horror",
                        100.0,
                        "Horror",
                        18,
                        false
                )
        );

        List<Movie> actual = movieRepository.findAllMatchingCategoryList(List.of("Horror", "Science Fiction"));
        org.junit.jupiter.api.Assertions.assertEquals(expectedMovies, actual);
    }

    @Test
    public void findAllCategoriesReturnsAllCategoriesFromTestDb() {
        List<String> expectedCategories =  List.of(
          "Science Fiction",
          "Romantic Comedy",
          "Horror",
          "Documentary",
          "Animation"
        );

        org.junit.jupiter.api.Assertions.assertTrue(movieRepository.findAllCategories().containsAll(expectedCategories));
    }
}
