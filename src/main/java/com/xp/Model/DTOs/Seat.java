package com.xp.Model.DTOs;

import com.xp.Model.SeatAvailability;
import com.xp.Model.SeatType;
import com.xp.Model.Theater;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"theater_id", "ro_number", "seat_number"}), name = "Seats")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    @ManyToOne
    @JoinColumn(name = "theater_id", nullable = false)
    private Theater theater;

    @Column(name = "ro_number") // "row" is a reserved keyword in MySQL
    private Integer rowNumber;

    @Column(name = "seat_number")
    private Integer seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    public Seat() {}

    public Seat(Theater theater, Integer rowNumber, Integer seatNumber, SeatType seatType) {
        this.theater = theater;
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public Theater getTheater() {
        return theater;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }

    public Integer getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return Objects.equals(seatId, seat.seatId) && Objects.equals(theater, seat.theater) && Objects.equals(rowNumber, seat.rowNumber) && Objects.equals(seatNumber, seat.seatNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seatId, theater, rowNumber, seatNumber);
    }
}
