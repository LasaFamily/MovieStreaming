package com.movie.kit.interceptor;

import com.movie.kit.mapping.Movies;
import com.movie.kit.mapping.Persons;
import com.movie.kit.mapping.Shows;

import java.util.List;

public interface SearchInterceptorService {
    List<Movies> getMoviesBySearchAndPageIndex(String search, String pageIndex);
    List<Shows> getShowsBySearchAndPageIndex(String search, String pageIndex);
    List<Persons> getPersonsBySearchAndPageIndex(String search, String pageIndex);
}
