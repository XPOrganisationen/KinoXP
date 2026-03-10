package com.xp.Service;

import com.xp.Model.Cinema;
import com.xp.Repository.CinemaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CinemaService
{
    private final CinemaRepository cinemaRepository;

    public CinemaService(CinemaRepository cinemaRepository)
    {
        this.cinemaRepository = cinemaRepository;
    }

    public List<Cinema> getAllCinemas()
    {
        return cinemaRepository.findAllByOrderByCinemaNameAsc();
    }

    public List<Cinema> getAllCinemasWithTheaters()
    {
        return cinemaRepository.findAllWithTheaters();
    }

    public Cinema getCinemaById(Long id)
    {
        return cinemaRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Cinema not found with id"));
    }

    public Cinema createCinema(Cinema cinema)
    {
        return cinemaRepository.save(cinema);
    }

    public Cinema updateCinema(Long id, Cinema updatedCinema)
    {
        Cinema existingCinema = cinemaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cinema not found with id: " + id));
        existingCinema.setCinemaName(updatedCinema.getCinemaName());
        existingCinema.setCinemaAddress(updatedCinema.getCinemaAddress());

        return cinemaRepository.save(existingCinema);
    }

    public void deleteCinema(Long id)
    {
        if (!cinemaRepository.existsById(id))
        {
            throw new RuntimeException("Cinema not found with id: " + id);
        }
        cinemaRepository.deleteById(id);
    }

    public List<Cinema> searchByName(String name)
    {
        return cinemaRepository.findByCinemaNameContainingIgnoreCase(name);
    }


}
