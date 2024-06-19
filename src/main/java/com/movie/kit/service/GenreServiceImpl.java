package com.movie.kit.service;

import com.movie.kit.autowired.AutowiredService;
import com.movie.kit.constants.ApiConstants;
import com.movie.kit.domain.Genres;
import com.movie.kit.domain.Movies;
import com.movie.kit.domain.Shows;
import com.movie.kit.util.MoviesUtils;
import com.movie.kit.util.ShowUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreServiceImpl extends AutowiredService implements GenreService {

    @Override
    public List<Genres> getAllGenericsByType(String genericType) {
        List<Genres> dbGenres = getGenericInterceptorService().getAllGenericsByType(genericType).stream().map(genres ->
                new Genres(genres.getId(), genres.getName())).collect(Collectors.toList());
        if (genericType.equalsIgnoreCase(ApiConstants.TV)) {
            dbGenres.add(new Genres(10749, ApiConstants.ROMANCE));
            dbGenres.add(new Genres(10402, ApiConstants.MUSIC));
            dbGenres.add(new Genres(36, ApiConstants.HISTORY));
        }
        return dbGenres;
    }

    @Override
    public List<Movies> getMoviesByGenreIdAndPageIndex(String genreId, String pageIndex) {
        return MoviesUtils.getMovies(getAllGenericsByType(ApiConstants.MOVIE)
                        .stream().collect(Collectors.toMap(Genres::getGenericId, genres -> genres)),
                getGenericInterceptorService().getMoviesByGenreIdAndPageIndex(genreId, pageIndex));
    }

    @Override
    public List<Shows> getShowsByGenreIdAndPageIndex(String genreId, String pageIndex) {
        return ShowUtils.getShows(getAllGenericsByType(ApiConstants.TV)
                        .stream().collect(Collectors.toMap(Genres::getGenericId, genres -> genres)),
                getGenericInterceptorService().getShowsByGenreIdAndPageIndex(genreId, pageIndex));
    }
}
