package com.movie.kit.autowired;

import com.movie.kit.interceptor.*;
import com.movie.kit.repository.ShowRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
public class AutowiredService {

    @Autowired
    private MovieInterceptorService movieInterceptorService;

    @Autowired
    private ShowInterceptorService showInterceptorService;

    @Autowired
    private PersonInterceptorService personInterceptorService;

    @Autowired
    private SearchInterceptorService searchInterceptorService;

    @Autowired
    private GenericInterceptorService genericInterceptorService;

    @Autowired
    private CountryInterceptorService countryInterceptorService;

    @Autowired
    private MovieDetailsInterceptorService movieDetailsInterceptorService;

    @Autowired
    private ShowDetailsInterceptorService showDetailsInterceptorService;

    @Autowired
    private ShowRepository showRepository;
}
