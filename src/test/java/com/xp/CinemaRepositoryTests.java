package com.xp;

import com.xp.Model.Cinema;
import com.xp.Repository.CinemaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
public class CinemaRepositoryTests
{
    @Autowired
    private CinemaRepository cinemaRepository;

    @Test
    void findByCinemaNameIgnoreCaseFindsExactMatch()
    {
        Cinema expectedCinema = cinemaRepository.findById(1L).get();

        Optional<Cinema> actualCinema = cinemaRepository.findByCinemaNameIgnoreCase("Downtown Cinema");

        org.junit.jupiter.api.Assertions.assertTrue(actualCinema.isPresent());
        org.junit.jupiter.api.Assertions.assertEquals(expectedCinema, actualCinema.get());
    }

    @Test
    void findByCinemaNameIgnoreCaseReturnsEmptyAndOptionalIfNoMatch()
    {
        Optional<Cinema> actualCinema = cinemaRepository.findByCinemaNameIgnoreCase("There is no cinema with the name you've searched for");
        org.junit.jupiter.api.Assertions.assertTrue(actualCinema.isEmpty());
    }

    @Test
    void findByCinemaNameContainingIgnoreCaseFindsPartialMatch()
    {
        List<Cinema> actualCinemas = cinemaRepository.findByCinemaNameContainingIgnoreCase("town");

        org.junit.jupiter.api.Assertions.assertFalse(actualCinemas.isEmpty());
        org.junit.jupiter.api.Assertions.assertTrue(actualCinemas.stream().anyMatch(cinema -> cinema.getCinemaName().equalsIgnoreCase("Downtown Cinema")));
    }

    @Test
    void findByCinemaNameContainingIgnoreCaseReturnsWhenEmptyList()
    {
        List<Cinema> actualCinemas = cinemaRepository.findByCinemaNameContainingIgnoreCase("kjsndf");

        org.junit.jupiter.api.Assertions.assertEquals(List.of(), actualCinemas);
    }

    @Test
    void findByCinemaAddressContainingIgnoreCaseFindsPartialMatch()
    {
        List<Cinema> actualCinemas = cinemaRepository.findByCinemaAddressContainingIgnoreCase("Main");

        org.junit.jupiter.api.Assertions.assertFalse(actualCinemas.isEmpty());
        org.junit.jupiter.api.Assertions.assertTrue(actualCinemas.stream().anyMatch(cinema -> cinema.getCinemaAddress().equalsIgnoreCase("123 Main st")));
    }

    @Test
    void findAllByOrderCinemaNameAscReturnsCinemasInCorrectOrderAlphabetically()
    {
        List<Cinema> actualCinemas = cinemaRepository.findAll();

        List<String> actualNames = actualCinemas.stream()
                .filter(cinema -> cinema.getCinemaName() != null) // 6 null cinemas from FILIP, only when running tests together!!!!!
                .map(Cinema::getCinemaName)
                .toList();

        List<String> sortedNames = actualNames
                .stream()
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .toList();

        org.junit.jupiter.api.Assertions.assertEquals(sortedNames, actualNames);
    }

    @Test
    void findAllWithTheatersReturnsCinemasIncludingTheaters()
    {
        List<Cinema> actualCinemas = cinemaRepository.findAllWithTheaters();

        org.junit.jupiter.api.Assertions.assertFalse(actualCinemas.isEmpty());

        Cinema cinemaWithTheaters = actualCinemas.stream()
                .filter(cinema -> cinema.getCinemaId().equals(1L))
                .findFirst()
                .orElseThrow();

        org.junit.jupiter.api.Assertions.assertNotNull(cinemaWithTheaters);
    }
}