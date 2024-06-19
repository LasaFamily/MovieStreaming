package com.movie.kit.service;

import com.movie.kit.domain.Countries;
import com.movie.kit.domain.Movies;
import com.movie.kit.domain.Shows;

import java.util.List;

public interface CountryService {
    List<Countries> getAllCountries();

    List<Movies> getCountryMoviesByCountryCodeAndPageIndex(String countryCode, String pageIndex);

    List<Shows> getCountryShowsByCountryCodeAndPageIndex(String countryCode, String pageIndex);
}
