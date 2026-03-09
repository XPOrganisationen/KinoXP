package com.xp.Controller;


import com.xp.Model.Cinema;
import com.xp.service.CinemaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cinemas")
@CrossOrigin
public class CinemaController
{
    private final CinemaService cinemaService;

    public CinemaController(CinemaService cinemaService)
    {
        this.cinemaService  = cinemaService;
    }

    @GetMapping
    public ResponseEntity<List<Cinema>> geAllCinemas()
    {
        return ResponseEntity.ok(cinemaService.getAllCinemas());
    }

    @GetMapping("/with-theaters")
    public ResponseEntity<List<Cinema>> getAllCinemasWithTheaters()
    {
        return ResponseEntity.ok(cinemaService.getAllCinemasWithTheaters());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cinema> getCinemaById(@PathVariable Long id)
    {
        return ResponseEntity.ok(cinemaService.getCinemaById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Cinema>> searchCinemas(@RequestParam String name)
    {
        return ResponseEntity.ok(cinemaService.searchByName(name));
    }

    @PostMapping
    public ResponseEntity<Cinema> createCinema(@RequestBody Cinema cinema)
    {
        Cinema createdCinema = cinemaService.createCinema(cinema);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCinema);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cinema> updateCinema(@PathVariable Long id, @RequestBody Cinema cinema)
    {
        return ResponseEntity.ok(cinemaService.updateCinema(id, cinema));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCinema(@PathVariable Long id)
    {
        cinemaService.deleteCinema(id);
        return ResponseEntity.noContent().build();
    }



}
