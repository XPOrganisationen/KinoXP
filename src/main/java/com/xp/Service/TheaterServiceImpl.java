package com.xp.Service;

import com.xp.Exceptions.EntityDoesNotExistException;
import com.xp.Model.Theater;
import com.xp.Repository.TheaterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheaterServiceImpl implements TheaterService {
    private final TheaterRepository theaterRepository;

    public TheaterServiceImpl(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;
    }

    @Override
    public List<Theater> findAllTheaters() {
        return theaterRepository.findAll();
    }

    @Override
    public Theater findTheaterById(Long id) {
        return theaterRepository.findById(id).orElseThrow(() -> new EntityDoesNotExistException("No theater exists with ID " + id));
    }

    @Override
    public Theater createTheater(Theater theater) {
        return theaterRepository.save(theater);
    }

    @Override
    public Theater updateTheater(Theater theater) {
        if (!theaterRepository.existsById(theater.getTheaterId())) {
            throw new EntityDoesNotExistException("No theater exists with ID " + theater.getTheaterId());
        }

        return theaterRepository.save(theater);
    }

    @Override
    public void deleteTheaterById(Long id) {
        if (!theaterRepository.existsById(id)) {
            throw new EntityDoesNotExistException("No theater exists with ID " + id);
        }
    }
}
