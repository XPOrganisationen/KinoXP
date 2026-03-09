package com.xp.Model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Theaters")
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long theaterId;
    private String theaterName;
    private Integer numberOfRows;
    private Integer seatsPerRow;

    @ManyToOne
    @JoinColumn(name = "cinema_id", nullable = false)
    private Cinema cinema;

    public Theater() {}

    public Theater(String theaterName, Integer numberOfRows, Integer seatsPerRow) {
        this.theaterName = theaterName;
        this.numberOfRows = numberOfRows;
        this.seatsPerRow = seatsPerRow;
    }

    public Long getTheaterId() {
        return this.theaterId;
    }

    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public Integer getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(Integer numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public Integer getSeatsPerRow() {
        return seatsPerRow;
    }

    public void setSeatsPerRow(Integer seatsPerRow) {
        this.seatsPerRow = seatsPerRow;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Theater theater = (Theater) o;
        return Objects.equals(theaterId, theater.theaterId) && Objects.equals(theaterName, theater.theaterName) && Objects.equals(numberOfRows, theater.numberOfRows) && Objects.equals(seatsPerRow, theater.seatsPerRow) && Objects.equals(cinema, theater.cinema);
    }

    @Override
    public int hashCode() {
        return Objects.hash(theaterId, theaterName, numberOfRows, seatsPerRow, cinema);
    }
}
