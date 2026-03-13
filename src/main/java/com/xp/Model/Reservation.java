package com.xp.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @ManyToOne
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "reservation_id")
    private List<MovieTicket> movieTickets;

    private double totalPrice;

    public Reservation() {}

    public Reservation(Show show, List<MovieTicket> movieTickets) {
        this.show = show;
        this.movieTickets = movieTickets;
    }

    public Reservation(Show show, List<MovieTicket> movieTickets, double totalPrice) {
        this.show = show;
        this.movieTickets = movieTickets;
        this.totalPrice = totalPrice;
    }

    public List<MovieTicket> getMovieTickets() {
        return movieTickets;
    }

    public void setMovieTickets(List<MovieTicket> movieTickets) {
        this.movieTickets = movieTickets;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
