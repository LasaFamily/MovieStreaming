package com.movie.kit.rest;

import com.movie.kit.autowired.AutowiredMappedService;
import com.movie.kit.domain.Shows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class ShowController extends AutowiredMappedService {

    @GetMapping(value = "/shows")
    public List<Shows> shows(@RequestParam String showType, @RequestParam String pageIndex) {
        return getShowService().getShowsByShowTypeAndPageIndex(showType, pageIndex);
    }

    @GetMapping(value = "/show/recommendations")
    public List<Shows> showRecommendations(@RequestParam String showId, @RequestParam String pageIndex) {
        return getShowService().getRecommendationShowByShowIdAndPageIndex(showId, pageIndex);
    }
}