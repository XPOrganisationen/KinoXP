package com.xp.Controller;

import com.xp.Model.Theater;
import com.xp.Service.TheaterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/theaters")
public class TheaterController {
    private final TheaterService theaterService;

    public TheaterController(TheaterService theaterService) {
        this.theaterService = theaterService;
    }

    @GetMapping
    public List<Theater> findAllTheaters() {
        return theaterService.findAllTheaters();
    }

    @GetMapping("/{theaterId}")
    public Theater findTheaterById(@PathVariable Long theaterId) {
        return theaterService.findTheaterById(theaterId);
    }

    @PostMapping
    public ResponseEntity<Theater> createTheater(@RequestBody Theater theater) {
        return ResponseEntity
                .created(URI.create("/api/theaters/" + theater.getTheaterId()))
                .body(theaterService.createTheater(theater));
    }

    @PutMapping
    public Theater updateTheater(@RequestBody Theater theater) {
        return theaterService.updateTheater(theater);
    }

    @DeleteMapping("/{theaterId}")
    public ResponseEntity<Void> deleteTheater(@PathVariable Long theaterId) {
        theaterService.deleteTheaterById(theaterId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
