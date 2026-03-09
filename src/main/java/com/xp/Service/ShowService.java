package com.xp.Service;

import com.xp.Model.Cinema;
import com.xp.Model.DTOs.CinemaGroup;
import com.xp.Model.Show;

import java.util.List;
import java.util.Map;

public interface ShowService {
    public List<Show> findAll();
    public Show findById(Long showId);
    public List<Show> findAllByMovieId(Long movieId);
    public Show addShow(Show show);
    public Show updateShow(Show show);
    public void deleteShow(Long showId);
    public Map<Cinema, List<Show>> getAllShowsGroupByCinemaForMovie(Long movieId);
    public List<CinemaGroup> getAllCinemaGroupsForMovie(Long movieId);
}
