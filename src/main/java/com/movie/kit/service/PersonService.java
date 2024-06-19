package com.movie.kit.service;

import com.movie.kit.domain.Movies;
import com.movie.kit.domain.Persons;
import com.movie.kit.domain.Shows;

import java.util.List;
import java.util.Map;

public interface PersonService {
    List<Persons> getPersonsByPageIndex(String pageIndex);
    Map<String, List<Movies>> getPersonMoviesByPersonId(String personId);
    Map<String, List<Shows>> getPersonShowsByPersonId(String personId);
    Persons getPersonDetailsByPersonId(String personId);
}
