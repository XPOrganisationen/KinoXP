package com.xp;

import com.xp.Model.Cinema;
import com.xp.Repository.CinemaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@ActiveProfiles("dev")
public class CinemaRepositoryTest
{

    @Autowired
    private CinemaRepository cinemaRepository;

    @Test
    void findByCinemaNameIgnoreCaseFindsExactMatch()
    {
        Cinema cinema = cinemaRepository.findById(1L).get();
        Cinema actualCinema = cinemaRepository.findByCinemaNameIgnoreCase("Downtown Cinema").get(); // Har valgt at kalde den efter den dummy-data vi har i db'en. Vi kan lave vores egne navne.

        Assertions.assertEquals(cinema.getCinemaId(), actualCinema.getCinemaId());
        Assertions.assertEquals(cinema.getCinemaName(), actualCinema.getCinemaName());
        Assertions.assertEquals(cinema.getCinemaAddress(), actualCinema.getCinemaAddress());


    }

}
