package com.movie.kit.jsoup;

import com.movie.kit.model.Shows;

import java.util.List;

public interface ShowJsoupService {
    List<Shows> getShows(Integer startIndex, Integer endIndex, Integer showNumber);
}
