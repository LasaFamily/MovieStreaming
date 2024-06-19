package com.movie.kit.autowired;

import com.movie.kit.service.*;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
public class AutowiredMappedService {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ShowService showService;

    @Autowired
    private PersonService personService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private MovieDetailsService movieDetailsService;

    @Autowired
    private ShowDetailsService showDetailsService;


}
