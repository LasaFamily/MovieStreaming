package com.movie.kit.service;

import com.movie.kit.autowired.AutowiredService;
import com.movie.kit.constants.ApiConstants;
import com.movie.kit.domain.Countries;
import com.movie.kit.domain.Genres;
import com.movie.kit.domain.Movies;
import com.movie.kit.domain.Shows;
import com.movie.kit.util.MoviesUtils;
import com.movie.kit.util.ShowUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl extends AutowiredService implements CountryService {

    @Autowired
    private GenreService genreService;

    @Override
    public List<Countries> getAllCountries() {
        return getCountryInterceptorService().getAllCountries().stream()
                .map(country -> new Countries(country.getIso_3166_1(),
                        country.getEnglish_name())).collect(Collectors.toList());
    }

    @Override
    public List<Movies> getCountryMoviesByCountryCodeAndPageIndex(String countryCode, String pageIndex) {
        return MoviesUtils.getMovies(genreService.getAllGenericsByType(ApiConstants.MOVIE)
                        .stream().collect(Collectors.toMap(Genres::getGenericId, genres -> genres)),
                getCountryInterceptorService().getCountryMoviesByCountryCodeAndPageIndex(countryCode, pageIndex));
    }

    @Override
    public List<Shows> getCountryShowsByCountryCodeAndPageIndex(String countryCode, String pageIndex) {
        return ShowUtils.getShows(genreService.getAllGenericsByType(ApiConstants.TV)
                        .stream().collect(Collectors.toMap(Genres::getGenericId, genres -> genres)),
                getCountryInterceptorService().getCountryShowsByCountryCodeAndPageIndex(countryCode, pageIndex));
    }
}
