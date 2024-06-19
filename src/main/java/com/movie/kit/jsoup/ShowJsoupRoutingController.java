package com.movie.kit.jsoup;

import com.movie.kit.model.Movies;
import com.movie.kit.model.Shows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class ShowJsoupRoutingController  {

    @Autowired
    private ShowJsoupService showJsoupService;

    @GetMapping("/jsoup/shows")
    public ResponseEntity<List<Shows>> jsoupShows() {
        return new ResponseEntity<>(showJsoupService.getShows(1, 421, 32), HttpStatus.OK);
    }

    @GetMapping("/jsoup/shows/index/{startIndex}/{endIndex}/{showNumber}")
    public ResponseEntity<List<Shows>> jsoupShows(@PathVariable Integer startIndex,@PathVariable Integer endIndex, @PathVariable Integer showNumber) {
        return new ResponseEntity<>(showJsoupService.getShows(startIndex, endIndex, showNumber), HttpStatus.OK);
    }
}
