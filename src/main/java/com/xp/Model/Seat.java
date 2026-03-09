package com.xp.Model;

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
    private SeatAvailability seatAvailability;

    @Version
    private Long version;

    public Seat() {}

    public Seat(Theater theater, Integer rowNumber, Integer seatNumber, SeatAvailability seatAvailability) {
        this.theater = theater;
        this.rowNumber = rowNumber;
        this.seatNumber = seatNumber;
        this.seatAvailability = seatAvailability;
    }

    public SeatAvailability getSeatAvailability() {
        return seatAvailability;
    }

    public void setSeatAvailability(SeatAvailability seatAvailability) {
        this.seatAvailability = seatAvailability;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return Objects.equals(seatId, seat.seatId) && Objects.equals(theater, seat.theater) && Objects.equals(rowNumber, seat.rowNumber) && Objects.equals(seatNumber, seat.seatNumber) && seatAvailability == seat.seatAvailability && Objects.equals(version, seat.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seatId, theater, rowNumber, seatNumber, seatAvailability, version);
    }
}
