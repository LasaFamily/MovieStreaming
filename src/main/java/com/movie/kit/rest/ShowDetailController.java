package com.movie.kit.rest;

import com.movie.kit.autowired.AutowiredMappedService;
import com.movie.kit.domain.Episodes;
import com.movie.kit.domain.ShowDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/api")
public class ShowDetailController extends AutowiredMappedService {

    @GetMapping(value = "/show/details")
    public ShowDetails showDetails(@RequestParam String showId) {
        return getShowDetailsService().getShowDetailsByShowId(showId);
    }

    @GetMapping(value = "/show/season/episodes")
    public List<Episodes> showSeasonEpisodes(@RequestParam String showId, @RequestParam Integer seasonNumber) {
        return getShowDetailsService().getEpisodesByShowIdAndSeasonId(showId, seasonNumber);
    }

    @GetMapping(value = "/show/season/episode/server")
    public String showSeasonEpisodesServerUrl(@RequestParam String showId, @RequestParam String tmdbId, @RequestParam Integer seasonNumber, @RequestParam String episodeNumber, @RequestParam Integer serverId) {
        String serverUrl = "";
        switch (serverId) {
            case 0:
                List<String> servers = getShowDetailsService().getShowStreamingId(showId, String.valueOf(seasonNumber), episodeNumber);
                if (servers.size() > 0)
                    serverUrl = servers.get(0);
                else
                    serverUrl = "https://vidsrc.to/embed/tv/" + tmdbId + "/" + seasonNumber + "/" + episodeNumber + "/";
                break;
            case 1:
                List<String> serverInfo = getShowDetailsService().getShowStreamingId(showId, String.valueOf(seasonNumber), episodeNumber);
                if (serverInfo.size() > 1)
                    serverUrl = serverInfo.get(1);
                else if (serverInfo.size() == 1)
                    serverUrl = serverInfo.get(0);
                else
                    serverUrl = "https://vidsrc.to/embed/tv/" + tmdbId + "/" + seasonNumber + "/" + episodeNumber + "/";
                break;
            case 2:
                serverUrl = "https://vidsrc.to/embed/tv/" + tmdbId + "/" + seasonNumber + "/" + episodeNumber + "/";
                break;
            case 3:
                serverUrl = "https://embed.smashystream.com/playere.php?tmdb=" + showId + "&season=" + seasonNumber + "&episode=" + episodeNumber;
                break;
            case 4:
                serverUrl = "https://multiembed.mov/?video_id=" + tmdbId + "&s=" + seasonNumber + "&e=" + episodeNumber;
                break;
            case 5:
                serverUrl = "https://flixhq.click/wp-content/plugins/fmovie-core/player/player.php?video_id=" + tmdbId + "&s=" + seasonNumber + "&e=" + episodeNumber;
                break;
            case 6:
                serverUrl = "https://moviesapi.club/tv/" + showId + "-" + seasonNumber + "-" + episodeNumber;
                break;
            case 7:
                serverUrl = "https://www.primewire.tf/embed/tv?imdb=" + tmdbId + "&season=" + seasonNumber + "&episode=" + episodeNumber;
                break;
            case 8:
                serverUrl = "https://123movieswatch.pw/se_player.php?video_id=" + tmdbId + "&s=" + seasonNumber + "&e=" + episodeNumber;
                break;
            default:
                break;
        }

        return serverUrl;
    }

}