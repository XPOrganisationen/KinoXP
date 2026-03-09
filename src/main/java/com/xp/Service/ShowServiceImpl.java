package com.xp.Service;

import com.xp.Exceptions.EntityDoesNotExistException;
import com.xp.Model.Cinema;
import com.xp.Model.DTOs.CinemaGroup;
import com.xp.Model.DTOs.TheaterGroup;
import com.xp.Model.Show;
import com.xp.Model.Theater;
import com.xp.Repository.ShowRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ShowServiceImpl implements ShowService {
    private final ShowRepository showRepository;

    public ShowServiceImpl(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @Override
    public List<Show> findAll() {
        return showRepository.findAll();
    }

    @Override
    public Show findById(Long showId) {
        return showRepository
                .findById(showId)
                .orElseThrow(() -> new EntityDoesNotExistException("No show exists with id:" + showId));
    }

    @Override
    public List<Show> findAllByMovieId(Long movieId) {
        return this.showRepository.findAllByMovieId(movieId);
    }

    @Override
    public Show addShow(Show show) {
        return this.showRepository.save(show);
    }

    @Override
    public Show updateShow(Show show) {
        if (!this.showRepository.existsById(show.getShowId())) {
            throw new EntityDoesNotExistException("No show exists with id:" + show.getShowId());
        }

        return this.showRepository.save(show);
    }

    @Override
    public void deleteShow(Long showId) {
        if (!this.showRepository.existsById(showId)) {
            throw new EntityDoesNotExistException("No show exists with id:" + showId);
        }

        this.showRepository.deleteById(showId);
    }

    @Override
    public Map<Cinema, List<Show>> getAllShowsGroupByCinemaForMovie(Long movieId) {
        return findAllByMovieId(movieId)
                .stream()
                .filter(s -> s.getStartTime().isAfter(LocalDateTime.now()))
                .collect(
                        Collectors
                        .groupingBy(s -> s.getTheater().getCinema())
                );
    }

    @Override
    public List<CinemaGroup> getAllCinemaGroupsForMovie(Long movieId) {
        return getAllShowsGroupByCinemaForMovie(movieId)
                .entrySet()
                .stream()
                .map(entry -> {
                    Cinema cinema = entry.getKey();
                    List<Show> cinemaShows = entry.getValue();

                    Map<Theater, List<Show>> showsByTheater = cinemaShows
                            .stream()
                            .collect(Collectors.groupingBy(Show::getTheater));

                    List<TheaterGroup> theaterGroups = showsByTheater
                            .entrySet()
                            .stream()
                            .map(theaterEntry -> {
                                Theater theater = theaterEntry.getKey();
                                List<Show> theaterShows = theaterEntry.getValue();
                                return new TheaterGroup(theater.getTheaterId(),
                                        theater.getTheaterName(),
                                        theaterShows.stream().sorted(Comparator.comparing(Show::getStartTime))
                                                .toList());
                            }).toList();

                    return new CinemaGroup(cinema.getCinemaId(), cinema.getCinemaName(), theaterGroups);
                }).toList();
    }
}
