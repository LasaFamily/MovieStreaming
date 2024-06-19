package com.movie.kit.interceptor;

import com.movie.kit.mapping.Movies;
import com.movie.kit.mapping.Persons;
import com.movie.kit.mapping.Shows;

import java.util.List;
import java.util.Map;

public interface PersonInterceptorService {
    List<Persons> getPersonsByPageIndex(String pageIndex);
    Map<String, List<Movies>> getPersonMoviesByPersonId(String personId);
    Map<String, List<Shows>> getPersonShowsByPersonId(String personId);
    Persons getPersonDetailsByPersonId(String personId);
}
