package com.xp.Controller;

import com.xp.Model.DTOs.MovieFilter;
import com.xp.Model.Movie;
import com.xp.Service.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/movies/")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> getAllMovies() {
        return this.movieService.findAll();
    }

    @GetMapping("{movieId}")
    public Movie getMovieById(@PathVariable Long movieId) {
        return this.movieService.findById(movieId);
    }

    @GetMapping("by-title/{movieTitle}")
    public List<Movie> getMovieByTitle(@PathVariable String movieTitle) {
        return this.movieService.findAllByTitle(movieTitle);
    }

    @GetMapping("by-popularity")
    public List<Movie> getPopularMovies() {
        return this.movieService.findAllOrderByPopularityDesc();
    }

    @GetMapping("by-available-seats-shown-soon")
    public List<Movie> getMoviesWithAvailableSeatsShownSoon() {
        return this.movieService.findAllOrderBySeatsAvailableShownSoon();
    }

    @PostMapping("by-filter")
    public List<Movie> getMoviesByFilter(@RequestBody MovieFilter movieFilter) {
        return this.movieService.findAllByFilter(movieFilter);
    }

    @GetMapping("categories")
    public List<String> getCategories() {
        return this.movieService.findAllCategories();
    }

    @PostMapping
    public Movie addMovie(@RequestBody Movie movie) {
        return this.movieService.addMovie(movie);
    }

    @PutMapping
    public Movie updateMovie(@RequestBody Movie movie) {
        return this.movieService.updateMovie(movie);
    }

    @DeleteMapping("{movieId}")
    public void deleteMovie(@PathVariable Long movieId) {
        this.movieService.deleteMovie(movieId);
    }
}
