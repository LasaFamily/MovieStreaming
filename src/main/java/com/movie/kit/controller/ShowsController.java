package com.movie.kit.controller;

import com.movie.kit.api.RestClient;
import com.movie.kit.constants.MovieUrls;
import com.movie.kit.domain.Genres;
import com.movie.kit.domain.Shows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ShowsController extends RestClient {

    @Value("${movie.host.url}")
    private String hostUrl;

    @GetMapping(value = "/shows")
    public String homeShows(Model model) {
        List<Shows> popularShows = get(hostUrl + MovieUrls.SHOWS_END_POINT + "?showType=popular" + "&pageIndex=" + 1, new ParameterizedTypeReference<>() {});
        List<Shows> topRatedShows = get(hostUrl + MovieUrls.SHOWS_END_POINT + "?showType=top_rated" + "&pageIndex=" + 1, new ParameterizedTypeReference<>() {});
        List<Shows> topWeeklyShows = get(hostUrl + MovieUrls.SHOWS_END_POINT + "?showType=week" + "&pageIndex=" + 1, new ParameterizedTypeReference<>() {});
        List<Shows> nowPlayingShows = get(hostUrl + MovieUrls.SHOWS_END_POINT + "?showType=airing_today" + "&pageIndex=" + 1, new ParameterizedTypeReference<>() {});
        List<Shows> upcomingShows = get(hostUrl + MovieUrls.SHOWS_END_POINT + "?showType=on_the_air" + "&pageIndex=" + 1, new ParameterizedTypeReference<>() {});
        List<Genres> showGenres = get(hostUrl + MovieUrls.GENRES_END_POINT + "?genreType=tv", new ParameterizedTypeReference<>() {});

        Collections.shuffle(popularShows);
        Collections.shuffle(topRatedShows);
        Collections.shuffle(topWeeklyShows);
        Collections.shuffle(nowPlayingShows);
        Collections.shuffle(upcomingShows);

        model.addAttribute("popularShows", popularShows);
        model.addAttribute("topRatedShows", topRatedShows);
        model.addAttribute("topWeeklyShows", getTopWeeklyShows(topWeeklyShows));
        model.addAttribute("nowPlayingShows", nowPlayingShows);
        model.addAttribute("upcomingShows", upcomingShows);
        model.addAttribute("showGenres", showGenres);
        model.addAttribute("title", "shows");
        model.addAttribute("userSearch", "shows");
        return "shows/home";
    }

    @GetMapping(value = "/shows/{showType}")
    public String shows(@PathVariable String showType, Model model) {
        model.addAttribute("showType", showType);
        model.addAttribute("pageIndex", 1);
        model.addAttribute("title", "shows");
        model.addAttribute("viewName", getViewName(showType));
        model.addAttribute("userSearch", "shows");
        return "shows/shows";
    }

    @GetMapping(value = "/shows/append")
    public String showsAppend(@RequestParam String showType, @RequestParam Integer pageIndex, Model model) {
        List<Shows> shows = get(hostUrl + MovieUrls.SHOWS_END_POINT + "?showType=" + showType + "&pageIndex=" + pageIndex, new ParameterizedTypeReference<>() {});
        model.addAttribute("showType", showType);
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("shows", shows);
        return pageIndex % 2 == 0 ? "shows/evenAppendShows" : "shows/oddAppendShows";
    }

    private String getViewName(String movieType) {
        return movieType.equalsIgnoreCase("popular") ? "Popular Shows" :
                movieType.equalsIgnoreCase("top_rated") ? "Top Rated Shows" :
                        movieType.equalsIgnoreCase("airing_today") ? "Airing Today Shows" :
                                movieType.equalsIgnoreCase("on_the_air") ? "On The Air Shows" : "Shows";
    }

    private Map<Integer, List<Shows>> getTopWeeklyShows(List<Shows> topWeeklyShows) {
        final Integer[] index = {1};
        Map<Integer, List<Shows>> shows = topWeeklyShows.stream().peek(show -> {
            if(index[0] <=5) {
                show.setShowIndex(1);
            } else if(index[0] <=10) {
                show.setShowIndex(2);
            } else if(index[0] <=15) {
                show.setShowIndex(3);
            } else if(index[0] <=20) {
                show.setShowIndex(4);
            }
            show.setShowNumber(index[0] < 10 ? ("0" + index[0]) : index[0].toString());
            index[0]++;
        }).collect(Collectors.groupingBy(Shows::getShowIndex, Collectors.toList()));
        shows.remove(4);
        return shows;
    }
}
