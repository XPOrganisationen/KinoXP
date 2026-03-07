package com.xp.Model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;
    private String movieTitle;
    private String movieDescription;
    private Double movieDurationMinutes;
    private String movieCategory;
    private Integer ageLimit;
    private boolean is_3d;

    public Movie() {}

    public Movie(String movieTitle, String movieDescription, Double movieDurationMinutes, String movieCategory, Integer ageLimit, boolean is_3d) {
        this.movieTitle = movieTitle;
        this.movieDescription = movieDescription;
        this.movieDurationMinutes = movieDurationMinutes;
        this.movieCategory = movieCategory;
        this.ageLimit = ageLimit;
        this.is_3d = is_3d;
    }

    public Movie(Long movieId, String movieTitle, String movieDescription, Double movieDurationMinutes, String movieCategory, Integer ageLimit, boolean is_3d) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.movieDescription = movieDescription;
        this.movieDurationMinutes = movieDurationMinutes;
        this.movieCategory = movieCategory;
        this.ageLimit = ageLimit;
        this.is_3d = is_3d;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {}

    public Integer getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(Integer ageLimit) {
        this.ageLimit = ageLimit;
    }

    public boolean is_3d() {
        return is_3d;
    }

    public void setIs_3d(boolean is3D) {
        this.is_3d = is3D;
    }

    public String getMovieCategory() {
        return movieCategory;
    }

    public void setMovieCategory(String movieCategory) {
        this.movieCategory = movieCategory;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public Double getMovieDuration() {
        return movieDurationMinutes;
    }

    public void setMovieDuration(Double movieDurationMinutes) {
        this.movieDurationMinutes = movieDurationMinutes;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return is_3d == movie.is_3d && Objects.equals(movieId, movie.movieId) && Objects.equals(movieTitle, movie.movieTitle) && Objects.equals(movieDescription, movie.movieDescription) && Objects.equals(movieDurationMinutes, movie.movieDurationMinutes) && Objects.equals(movieCategory, movie.movieCategory) && Objects.equals(ageLimit, movie.ageLimit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieId, movieTitle, movieDescription, movieDurationMinutes, movieCategory, ageLimit, is_3d);
    }
}
