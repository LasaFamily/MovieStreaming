package com.movie.kit.controller;

import com.movie.kit.api.RestClient;
import com.movie.kit.constants.MovieUrls;
import com.movie.kit.domain.Shows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class PersonShowsController  extends RestClient {

    @Value("${movie.host.url}")
    private String hostUrl;

    @GetMapping(value = "/person/shows/{personId}")
    public String personShows(@PathVariable String personId, Model model) {
        Map<String, List<Shows>> shows = get(hostUrl + MovieUrls.PERSON_SHOWS_END_POINT + "?personId=" +
                personId, new ParameterizedTypeReference<>() {});
        int showsCount = getTotalCount(shows).size() / 20;
        model.addAttribute("showsCount", showsCount + 1);
        model.addAttribute("pageIndex", 1);
        model.addAttribute("personId", personId);
        model.addAttribute("title", "person Shows");
        model.addAttribute("viewName", "Person Shows");
        model.addAttribute("userSearch", "shows");
        return "persons/shows";
    }


    @GetMapping(value = "/person/shows/append")
    public String personShowsAppend(@RequestParam String personId, @RequestParam Integer pageIndex, Model model) {
        Map<String, List<Shows>> showMap = get(hostUrl + MovieUrls.PERSON_SHOWS_END_POINT + "?personId=" +
                personId, new ParameterizedTypeReference<>() {});
        List<Shows> allShows =  getTotalCount(showMap);
        int startIndex = pageIndex * 20 - 20;
        int endIndex = pageIndex * 20;

        List<Shows> shows = IntStream.range(startIndex, Math.min(allShows.size(), endIndex))
                .mapToObj(allShows::get)
                .collect(Collectors.toList());
        model.addAttribute("shows", shows);
        return pageIndex % 2 == 0 ? "shows/evenAppendShows" : "shows/oddAppendShows";
    }

    private List<Shows> getTotalCount(Map<String, List<Shows>> shows) {
        return shows.values().stream().flatMap(Collection::stream).distinct().collect(Collectors.toList());
    }
}