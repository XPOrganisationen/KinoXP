package com.xp.Model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservationId;

    @ManyToOne
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MovieTicket> movieTickets;

    public Reservation() {}

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

    public Integer getReservationId() {
        return reservationId;
    }
}
