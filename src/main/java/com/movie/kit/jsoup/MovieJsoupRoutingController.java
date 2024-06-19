package com.movie.kit.jsoup;

import com.movie.kit.model.Movies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class MovieJsoupRoutingController {
    @Autowired
    private MovieJsoupService movieJsoupService;

    @GetMapping("/jsoup/moviessss")
    public ResponseEntity<List<Movies>> jsoupMovies() {
        return new ResponseEntity<>(movieJsoupService.getMovies(1, 123,32), HttpStatus.OK);
    }

    @GetMapping("/jsoup/movies/index/{startIndex}/{endIndex}/{MovieNumber}")
    public ResponseEntity<List<Movies>> jsoupMovies(@PathVariable Integer startIndex, @PathVariable Integer endIndex, @PathVariable Integer MovieNumber) {
        return new ResponseEntity<>(movieJsoupService.getMovies(startIndex,endIndex, MovieNumber), HttpStatus.OK);
    }

    @GetMapping("/jsoup/movies/lookmovies/{pageNumber}/{MovieNumber}")
    public ResponseEntity<List<Movies>> jsoupLookMovies(@PathVariable Integer pageNumber, @PathVariable Integer MovieNumber) {
        return new ResponseEntity<>(movieJsoupService.getLookMovies(pageNumber, MovieNumber), HttpStatus.OK);
    }
}
