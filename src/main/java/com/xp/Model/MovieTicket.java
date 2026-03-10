package com.xp.Model;

import jakarta.persistence.*;

@Entity
@Table(name="movie_tickets")
public class MovieTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_ticket_id")
    private Long movieTicketId;

    private Double price;

    @ManyToOne
    @JoinColumn(name = "show_id", nullable = false)
    private Show show;

    @OneToOne
    @JoinColumn(name = "show_seat_id", nullable = false)
    private ShowSeat showSeat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    public MovieTicket() {}

    public MovieTicket(Double price, Show show, ShowSeat showSeat) {
        this.price = price;
        this.show = show;
        this.showSeat = showSeat;
    }

    public Long getMovieTicketId() {
        return movieTicketId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ShowSeat getSeat() {
        return showSeat;
    }

    public void setSeat(ShowSeat showSeat) {
        this.showSeat = showSeat;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
