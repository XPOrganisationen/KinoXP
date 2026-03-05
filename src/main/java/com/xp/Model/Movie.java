package com.xp.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;
    private String movieTitle;
    private String movieDescription;
    private Double movieDuration;
    private String movieCategory;
    private Integer ageLimit;
    private boolean is3D;

    public Movie() {}

    public Movie(String movieTitle, String movieDescription, Double movieDuration, String movieCategory, Integer ageLimit, boolean is3D) {
        this.movieTitle = movieTitle;
        this.movieDescription = movieDescription;
        this.movieDuration = movieDuration;
        this.movieCategory = movieCategory;
        this.ageLimit = ageLimit;
        this.is3D = is3D;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Integer getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(Integer ageLimit) {
        this.ageLimit = ageLimit;
    }

    public boolean isIs3D() {
        return is3D;
    }

    public void setIs3D(boolean is3D) {
        this.is3D = is3D;
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
        return movieDuration;
    }

    public void setMovieDuration(Double movieDuration) {
        this.movieDuration = movieDuration;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }
}
