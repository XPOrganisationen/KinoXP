package com.xp.model;

import jakarta.persistence.*;


@Entity
@Table(
        name="seats",
        uniqueConstraints = @UniqueConstraint(columnNames = {"theater", "rownumber", "seatnumber"}))
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="seat_id")
    private Long seatId;

    @ManyToOne
    @JoinColumn(name = "theater_id", nullable = false)
    private Theater theater;

    @Column(name="ro_number")
    private Integer rowNumber;

    @Column(name="seat_number")
    private Integer seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(name="seat_type")
    private SeatType seatType;

    public Seat() {}

    public Seat(Long theaterId, Integer rowNumber, Integer seatNumber, SeatType seatType) {

    }
}
