package com.xp.model;

import jakarta.persistence.*;

@Entity
@Table(name="movie_tickets")
public class MovieTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieTicketId;

    private Double price;

    @ManyToOne
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    @OneToOne
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    public MovieTicket() {}

    public MovieTicket(Double price, Show show, Seat seat) {
        this.price = price;
        this.show = show;
        this.seat = seat;
    }

    public Integer getMovieTicketId() {
        return movieTicketId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }
}
