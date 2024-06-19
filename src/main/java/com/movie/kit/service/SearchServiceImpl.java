package com.movie.kit.service;

import com.movie.kit.autowired.AutowiredService;
import com.movie.kit.constants.ApiConstants;
import com.movie.kit.domain.Genres;
import com.movie.kit.domain.Movies;
import com.movie.kit.domain.Persons;
import com.movie.kit.domain.Shows;
import com.movie.kit.util.MoviesUtils;
import com.movie.kit.util.PersonUtils;
import com.movie.kit.util.ShowUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl extends AutowiredService implements SearchService {

    @Autowired
    private GenreService genreService;


    @Override
    public List<Movies> getMoviesBySearchAndPageIndex(String search, String pageIndex) {
        return MoviesUtils.getMovies(genreService.getAllGenericsByType(ApiConstants.MOVIE)
                        .stream().collect(Collectors.toMap(Genres::getGenericId, genres -> genres)),
                getSearchInterceptorService().getMoviesBySearchAndPageIndex(search, pageIndex));
    }

    @Override
    public List<Shows> getShowsBySearchAndPageIndex(String search, String pageIndex) {
        return ShowUtils.getShows(genreService.getAllGenericsByType(ApiConstants.TV)
                        .stream().collect(Collectors.toMap(Genres::getGenericId, genres -> genres)),
                getSearchInterceptorService().getShowsBySearchAndPageIndex(search, pageIndex));
    }

    @Override
    public List<Persons> getPersonsBySearchAndPageIndex(String search, String pageIndex) {
        return PersonUtils.getPersons(getSearchInterceptorService().getPersonsBySearchAndPageIndex(search, pageIndex));
    }
}
