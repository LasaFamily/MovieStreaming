package com.movie.kit.autowired;

import com.movie.kit.interceptor.RestService;
import com.movie.kit.repository.MovieRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Getter
public class AutowiredAndValueService {

    @Value("${movie.base.url}")
    private String baseUrl;

    @Value("${movie.bearer.token}")
    private String bearerToken;

    @Autowired
    private RestService restService;

    @Autowired
    private MovieRepository movieRepository;

}
