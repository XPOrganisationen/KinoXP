package com.xp;

import com.xp.Model.Seat;
import com.xp.Model.Show;
import com.xp.Model.Theater;
import com.xp.Repository.SeatRepository;
import com.xp.Repository.ShowRepository;
import com.xp.Repository.TheaterRepository;
import com.xp.Repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource(locations = "classpath:application.properties")
public class TestTicketRepository {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private TheaterRepository theaterRepository;


    private Seat seat;
    private Show show;
    private Theater theater;


    @BeforeEach
    void setUp() {
        show = new Show();
        show.setTheater(theater);
        showRepository.save(show);
    }

}
