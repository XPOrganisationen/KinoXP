package com.xp.Service;

import com.xp.Model.DTOs.MovieFilter;
import com.xp.Model.Movie;

import java.util.List;

public interface MovieService {
    public List<Movie> findAll();
    public Movie findById(Long movieId);
    public List<Movie> findAllByTitle(String title);
    public Movie updateMovie(Movie movie);
    public Movie addMovie(Movie movie);
    public void deleteMovie(Long movieId);
    public List<Movie> findAllOrderByPopularityDesc();
    public List<Movie> findAllOrderBySeatsAvailableShownSoon();
    public List<Movie> findAllByFilter(MovieFilter movieFilter);
    public List<String> findAllCategories();
}
