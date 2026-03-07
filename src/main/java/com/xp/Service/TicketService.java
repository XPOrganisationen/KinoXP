package com.xp.Service;

import com.xp.Model.Movie;
import com.xp.Model.SeatType;
import com.xp.Model.TicketType;
import com.xp.Repository.TicketRepository;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    public final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public double calculateTotalPrice(TicketType ticketType, SeatType seatType, int quantity, Movie movie) {

        double pricePerTicket = ticketType.getPrice(); // priser for de diverse biletter findes i TicketType Enum :)

        pricePerTicket += seatType.getPriceAdjustment(); // vi tilføjer prisen af typen af sæde valgt med prisen for movie ticket

        if(movie.getMovieDuration() >= 170) { // hvis film er længere en 170 minutter tilføjes der ekstra gebyr på biletten
            pricePerTicket += 15;
        }

        if (movie.is_3d()) { // samme som før, en 3D film koster lidt ekstra
            pricePerTicket +=10;
        }

        double total = pricePerTicket * quantity; // pris for 1 billet ganget (*) nummeret af biletter valgt

        if (quantity <=5) { // hvis 5 eller færre biletter er købt
            total *= 1.05; // Registration fee: + 5% af total price
        } else if (quantity >=10) { // hvis 10 eller flere biletter er købt
            total *= 0.93; // discount: - 7% af total price
        }

        return total; // returnerer det endelige beløb
    }
}
