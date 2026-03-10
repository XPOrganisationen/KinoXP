package com.xp.Model;

import com.xp.Model.DTOs.Seat;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"show_seat_id", "seat_id", "show_id"}), name = "show_Seats")
public class ShowSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long showSeatId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "seat_id") // "row" is a reserved keyword in MySQL
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;

    @Enumerated(EnumType.STRING)
    private SeatAvailability seatAvailability;

    public ShowSeat() {}

    public ShowSeat(Seat seat, Show show, SeatAvailability seatAvailability) {
        this.seat = seat;
        this.show = show;
        this.seatAvailability = seatAvailability;
    }

    public SeatAvailability getSeatAvailability() {
        return seatAvailability;
    }

    public void setSeatAvailability(SeatAvailability seatAvailability) {
        this.seatAvailability = seatAvailability;
    }

    public Long getShowSeatId() {
        return showSeatId;
    }

    public void setShowSeatId(Long showSeatId) {
        this.showSeatId = showSeatId;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ShowSeat showSeat = (ShowSeat) o;
        return Objects.equals(showSeatId, showSeat.showSeatId) && Objects.equals(seat, showSeat.seat) && Objects.equals(show, showSeat.show) && seatAvailability == showSeat.seatAvailability;
    }

    @Override
    public int hashCode() {
        return Objects.hash(showSeatId, seat, show, seatAvailability);
    }
}
