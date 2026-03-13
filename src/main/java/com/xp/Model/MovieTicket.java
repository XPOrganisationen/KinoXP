package com.xp.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name="movie_tickets")
public class MovieTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_ticket_id")
    @JsonProperty("movieTicketId")
    private Long movieTicketId;

    @JsonProperty("price")
    private Double price;

    @OneToOne
    @JoinColumn(name = "show_seat_id", nullable = false)
    @JsonProperty("showSeat")
    private ShowSeat showSeat;

    @Enumerated(EnumType.STRING)
    @JsonProperty("ticketType")
    private TicketType ticketType;

    public MovieTicket() {}

    public MovieTicket(ShowSeat showSeat, TicketType ticketType) {
        this.showSeat = showSeat;
        this.ticketType = ticketType;
    }

    public MovieTicket(Double price, ShowSeat showSeat, TicketType ticketType) {
        this.price = price;
        this.showSeat = showSeat;
        this.ticketType = ticketType;
    }

    public Long getMovieTicketId() {
        return movieTicketId;
    }

    public void setMovieTicketId(Long movieTicketId) {
        this.movieTicketId = movieTicketId;
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

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }
}
