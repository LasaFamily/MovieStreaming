package com.movie.kit.controller;

import com.movie.kit.api.RestClient;
import com.movie.kit.constants.MovieUrls;
import com.movie.kit.domain.Episodes;
import com.movie.kit.domain.ShowDetails;
import com.movie.kit.domain.Shows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
public class ShowDetailsController extends RestClient {

    @Value("${movie.host.url}")
    private String hostUrl;

    @GetMapping(value = "/show/details/{showId}")
    public String showDetails(@PathVariable String showId, Model model) {
        ShowDetails show = get(hostUrl + MovieUrls.SHOW_DETAILS_END_POINT + "?showId=" + showId, new ParameterizedTypeReference<>() {});
        List<Shows> shows = get(hostUrl + MovieUrls.SHOW_RECOMMENDATIONS_END_POINT + "?showId=" + showId + "&pageIndex=" + 1, new ParameterizedTypeReference<>() {});
        model.addAttribute("showId", showId);
        model.addAttribute("tmdbId", show.getShowImdbId());
        model.addAttribute("show", show);
        model.addAttribute("shows", shows);
        model.addAttribute("seasonNumber", show.getSeasons().get(0).getSeasonNumber());
        model.addAttribute("episodeNumber", 1);
        model.addAttribute("title", "shows");
        model.addAttribute("userSearch", "shows");
        List<String> servers = Arrays.asList("CloudUp","MegaUpload", "vidsrc","smashystream","multiembed","2embed","moviesapi","primewire", "traze");
        model.addAttribute("servers", servers);
        return "shows/showDetails";
    }

    @GetMapping(value = "/show/season/episodes")
    public String seasonEpisodes(@RequestParam String showId, @RequestParam String seasonNumber, Model model) {
        List<Episodes> episodes = get(hostUrl + MovieUrls.SHOW_SEASON_EPISODES_END_POINT + "?showId=" + showId + "&seasonNumber=" + seasonNumber, new ParameterizedTypeReference<>() {
        });
        model.addAttribute("showId", showId);
        model.addAttribute("seasonNumber", seasonNumber);
        model.addAttribute("episodes", episodes);
        return "shows/showSeasonEpisodes";
    }

    @GetMapping(value = "/show/season/episode/server")
    public @ResponseBody String seasonEpisodes(@RequestParam String showId, @RequestParam String tmdbId, @RequestParam String seasonNumber,
                                               @RequestParam String episodeNumber, @RequestParam Integer serverId) {
        String serverUrl = get(hostUrl + MovieUrls.SHOW_SEASON_EPISODE_SERVER_END_POINT + "?showId=" + showId + "&tmdbId=" + tmdbId +
                "&seasonNumber=" + seasonNumber + "&episodeNumber=" + episodeNumber + "&serverId=" + serverId ,
                new ParameterizedTypeReference<>() {});
        return serverUrl;
    }
}

