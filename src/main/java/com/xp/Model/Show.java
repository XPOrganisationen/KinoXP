package com.xp.Model;

import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"theater_id, start_time"}), name = "Shows")
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long showId;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "theater_id", nullable = false)
    private Theater theater;

    private LocalDateTime startTime;

    public Show() {}

    public Show(Movie movie, Theater theater, LocalDateTime startTime) {
        this.movie = movie;
        this.theater = theater;
        this.startTime = startTime;
    }

    public Show(Long showId, Movie movie, Theater theater, LocalDateTime startTime) {
        this.showId = showId;
        this.movie = movie;
        this.theater = theater;
        this.startTime = startTime;
    }

    public Long getShowId() {
        return showId;
    }
    public void setShowId(Long showId) {}

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Theater getTheater() {
        return theater;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }

    public LocalDateTime getStartTime() {
        return  startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Show show = (Show) o;
        return Objects.equals(showId, show.showId) && Objects.equals(movie, show.movie) && Objects.equals(theater, show.theater) && Duration.between(startTime, show.startTime).abs().getSeconds() <= 5;
    }

    @Override
    public int hashCode() {
        return Objects.hash(showId, movie, theater, startTime);
    }
}
