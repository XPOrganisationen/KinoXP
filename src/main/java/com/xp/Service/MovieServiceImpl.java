package com.xp.Service;

import com.xp.Exceptions.EntityDoesNotExistException;
import com.xp.Model.DTOs.MovieFilter;
import com.xp.Model.Movie;
import com.xp.Repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    @Override
    public Movie findById(Long movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new EntityDoesNotExistException("No movie found with ID " + movieId));
    }

    @Override
    public List<Movie> findAllByTitle(String title) {
        return movieRepository.findAllByMovieTitleContainingIgnoreCase(title);
    }

    @Override
    public Movie updateMovie(Movie movie) {
        if (!movieRepository.existsById(movie.getMovieId())) {
            throw new EntityDoesNotExistException("No movie found with ID " + movie.getMovieId());
        }

        return movieRepository.save(movie);
    }

    @Override
    public Movie addMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public void deleteMovie(Long movieId) {
        if (!movieRepository.existsById(movieId)) {
            throw new EntityDoesNotExistException("No movie found with ID " + movieId);
        }

        movieRepository.deleteById(movieId);
    }

    @Override
    public List<Movie> findAllOrderByPopularityDesc() {
        return movieRepository.findAllOrderByPopularityDesc();
    }

    @Override
    public List<Movie> findAllOrderBySeatsAvailableShownSoon() {
        return movieRepository.findAllOrderByAvailableSeatsShownSoon();
    }

    @Override // Spaghetti warning
    public List<Movie> findAllByFilter(MovieFilter movieFilter) {
        boolean hasAgeLimit = movieFilter.ageLimit() != null;
        boolean hasCategories = movieFilter.categories() != null && !movieFilter.categories().isEmpty();
        boolean hasTitle = movieFilter.title() != null && !movieFilter.title().isEmpty();

        if (!hasAgeLimit && !hasCategories && !hasTitle) {
            return findAll();
        }

        if (hasAgeLimit && hasCategories && hasTitle) {
            return findAllByTitle(movieFilter.title())
                    .stream()
                    .filter(movie -> movie.getAgeLimit() <= movieFilter.ageLimit()
                                            && movieFilter.categories().contains(movie.getMovieCategory()))
                    .toList();
        }

        if (hasAgeLimit && hasCategories) {
            return findAllByAgeLimitLessThanEqual(movieFilter.ageLimit())
                    .stream()
                    .filter(movie -> movieFilter.categories().contains(movie.getMovieCategory()))
                    .toList();
        }

        if (hasAgeLimit && hasTitle) {
            return findAllByTitle(movieFilter.title())
                    .stream()
                    .filter(movie -> movie.getAgeLimit() <= movieFilter.ageLimit())
                    .toList();
        }

        if (!hasAgeLimit && hasCategories && hasTitle) {
            return findAllByTitle(movieFilter.title())
                    .stream()
                    .filter(movie -> movieFilter.categories().contains(movie.getMovieCategory()))
                    .toList();
        }

        if (!hasAgeLimit && hasCategories) {
            return findAllMatchingCategoryList(movieFilter.categories());
        }

        if (hasAgeLimit) {
            return findAllByAgeLimitLessThanEqual(movieFilter.ageLimit());
        }

        return findAllByTitle(movieFilter.title());
    }

    private List<Movie> findAllMatchingCategoryList(List<String> categoryList) {
        return movieRepository.findAllMatchingCategoryList(categoryList);
    }

    private List<Movie> findAllByAgeLimitLessThanEqual(Integer ageLimit) {
        return movieRepository.findAllByAgeLimitLessThanEqual(ageLimit);
    }
}
