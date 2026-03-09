package com.xp.Model;

import com.xp.Model.Cinema;
import jakarta.persistence.*;

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
}
