package com.xp.Controller;

import com.xp.Model.DTOs.CinemaGroup;
import com.xp.Model.Show;
import com.xp.Service.ShowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/shows/")
public class ShowController {
    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @GetMapping
    public List<Show> getAllShows() {
        return showService.findAll();
    }

    @GetMapping("{showId}")
    public Show getShowById(@PathVariable Long showId) {
        return showService.findById(showId);
    }

    @GetMapping("by-movie-id")
    public List<Show> getShowsByMovieId(@RequestParam Long movieId) {
        return showService.findAllByMovieId(movieId);
    }

    @GetMapping("/grouped-by-cinema/{movieId}")
    public List<CinemaGroup> getAllCinemaGroups(@PathVariable Long movieId) {
        return showService.getAllCinemaGroupsForMovie(movieId);
    }

    @PostMapping
    public ResponseEntity<Show> createShow(@RequestBody Show show) {
        return ResponseEntity.created(URI.create("/api/shows/" + show.getShowId())).body(showService.addShow(show));
    }

    @PutMapping
    public ResponseEntity<Show> updateShow(@RequestBody Show show) {
        return ResponseEntity.ok(showService.updateShow(show));
    }

    @PostMapping("delete/{showId}")
    public ResponseEntity<Void> deleteShow(@PathVariable Long showId) {
        showService.deleteShow(showId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
