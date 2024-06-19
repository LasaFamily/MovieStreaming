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
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl extends AutowiredService implements PersonService {

    @Autowired
    private GenreService genreService;

    @Override
    public List<Persons> getPersonsByPageIndex(String pageIndex) {
        return PersonUtils.getPersons(getPersonInterceptorService().getPersonsByPageIndex(pageIndex));
    }

    @Override
    public Map<String, List<Movies>> getPersonMoviesByPersonId(String personId) {
        return getMoviesMap(
                genreService.getAllGenericsByType(ApiConstants.MOVIE)
                        .stream().collect(Collectors.toMap(Genres::getGenericId, genres -> genres)),
                getPersonInterceptorService().getPersonMoviesByPersonId(personId));
    }

    @Override
    public Map<String, List<Shows>> getPersonShowsByPersonId(String personId) {
        return getShowsMap(
                genreService.getAllGenericsByType(ApiConstants.TV)
                        .stream().collect(Collectors.toMap(Genres::getGenericId, genres -> genres)),
                getPersonInterceptorService().getPersonShowsByPersonId(personId));
    }

    @Override
    public Persons getPersonDetailsByPersonId(String personId) {
        return PersonUtils.getPerson(getPersonInterceptorService().getPersonDetailsByPersonId(personId));
    }

    private static Map<String, List<Movies>> getMoviesMap(Map<Integer, Genres> genresMap, Map<String, List<com.movie.kit.mapping.Movies>> movies) {
       return movies.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
                moviesInfo -> MoviesUtils.getMovies(genresMap, moviesInfo.getValue())));
    }

    private static Map<String, List<Shows>> getShowsMap(Map<Integer, Genres> genresMap, Map<String, List<com.movie.kit.mapping.Shows>> shows) {
        return shows.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
                showsInfo -> ShowUtils.getShows(genresMap, showsInfo.getValue())));
    }
}
