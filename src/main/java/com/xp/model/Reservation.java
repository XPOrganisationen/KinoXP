package com.xp.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservationId;

    @ManyToOne
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    @OneToMany
    @JoinColumn(name = "reservation_id", nullable = false)
    private List<MovieTicket> movieTickets;
}
