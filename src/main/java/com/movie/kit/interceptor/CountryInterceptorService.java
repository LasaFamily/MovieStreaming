package com.movie.kit.interceptor;

import com.movie.kit.mapping.Countries;
import com.movie.kit.mapping.Movies;
import com.movie.kit.mapping.Shows;

import java.util.List;

public interface CountryInterceptorService {
    List<Countries> getAllCountries();
    List<Movies> getCountryMoviesByCountryCodeAndPageIndex(String countryCode, String pageIndex);
    List<Shows> getCountryShowsByCountryCodeAndPageIndex(String countryCode, String pageIndex);
}
