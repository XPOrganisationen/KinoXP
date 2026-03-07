package com.xp.Model;

import jakarta.persistence.*;

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
    private Integer seatNumber;

    @Enumerated(EnumType.STRING)
    private SeatAvailability seatAvailability;

    public Seat() {}

    public Seat(Long theaterId, Integer rowNumber, Integer seatNumber, SeatAvailability seatAvailability) {

    }

    public SeatAvailability getSeatAvailability() {
        return seatAvailability;
    }

    public void setSeatAvailability(SeatAvailability seatAvailability) {
        this.seatAvailability = seatAvailability;
    }
}
