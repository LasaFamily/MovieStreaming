package com.movie.kit.service;


import com.movie.kit.domain.Movies;
import com.movie.kit.domain.Persons;
import com.movie.kit.domain.Shows;

import java.util.List;

public interface SearchService {
    List<Movies> getMoviesBySearchAndPageIndex(String search, String pageIndex);
    List<Shows> getShowsBySearchAndPageIndex(String search, String pageIndex);
    List<Persons> getPersonsBySearchAndPageIndex(String search, String pageIndex);
}
