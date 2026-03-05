package com.xp.Service;

import com.xp.Model.Movie;
import com.xp.Model.MovieTicket;
import com.xp.Model.SeatType;
import com.xp.Model.TicketType;

public class TicketService {

    public final MovieTicket movieTicket;

    public TicketService(MovieTicket movieTicket) {
        this.movieTicket = movieTicket;
    }

    public double calculateTotalPrice(TicketType ticketType, SeatType seatType, int quantity, Movie movie) {

        double pricePerTicket = ticketType.getPrice(); // priser for de diverse biletter findes i TicketType Enum :)

        pricePerTicket += seatType.getPriceAdjustment(); // vi tilføjer prisen af typen af sæde valgt med prisen for movie ticket

        if(movie.getMovieDuration() >= 170) {
            pricePerTicket += 15;
        }

        if (movie.isIs3D()) {
            pricePerTicket +=10;
        }

        double total = pricePerTicket * quantity;

        if (quantity <=5) {
            total *= 1.05; // service fee = 5%
        } else if (quantity >=10) {
            total *= 0.93; // discount = 7%
        }

        return total;
    }
}
