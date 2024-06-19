package com.movie.kit.jsoup;

import com.movie.kit.model.Movies;
import com.movie.kit.repository.MovieRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MovieJsoupServiceImpl implements MovieJsoupService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public List<Movies> getMovies(Integer startIndex, Integer endIndex, Integer movieNumber) {
        List<Movies> movies = new ArrayList<>();
        for(int i = startIndex; i<=endIndex; i++) {
            try {
                Document doc = Jsoup.connect("https://himovies.to/movie?page=" + i)
                        .userAgent("Mozilla/5.0").timeout(10000).validateTLSCertificates(false).get();
                Element body = doc.body();
                List<Movies> dbMovies = getMovies(body, movieNumber);
                movies.addAll(dbMovies);
            } catch (Exception e) {
                System.out.println("Error Occurs : " + i + " : " + e.getMessage());
            }
        }
        return movies;
    }

    @Override
    public List<Movies> getLookMovies(Integer pageNumber, Integer movieNumber) {
        List<Movies> movies = new ArrayList<>();
        for(int i = 1; i<=pageNumber; i++) {
            try {
                Document doc = Jsoup.connect("https://lookmovie.plus/watch-movies/page/" + i + "/")
                        .userAgent("Mozilla/5.0").timeout(10000).validateTLSCertificates(false).get();
                Element body = doc.body();
                List<Movies> dbMovies = getLookMovies(body, movieNumber);
                movies.addAll(dbMovies);
            } catch (Exception e) {
                System.out.println("Error Occurs : " + i + " : " + e.getMessage());
            }
        }
        return movies;
    }

    private List<Movies> getLookMovies(Element body, Integer movieNumber) {
        List<Movies> savedMovies = new ArrayList<>();
        Elements elements = body.getElementsByClass("TPostMv");
        int count = 1;
        for (Element element : elements) {
            if(count <= movieNumber) {
                String movieId = element.attr("id").split("-")[1];
                String movieName = element.getElementsByClass("TPMvCn").get(0).getElementsByClass("Title").text();
                String movieYear = element.getElementsByClass("Info").get(0).getElementsByClass("Date").text();
                List<Movies> dbMovie = movieRepository.findByMovieNameAndMovieReleaseDate(movieName, movieYear);
                if(dbMovie.size() == 1) {
                    Movies movie = dbMovie.get(0);
                    movie.setLookMovieId(movieId);
                    Movies dbMovies = movieRepository.save(movie);
                } else {
                    Movies movie = new Movies();
                    movie.setMovieName(movieName);
                    movie.setMovieReleaseDate(movieYear);
                    movie.setLookMovieId(movieId);
                    Movies dbMovies = movieRepository.save(movie);
                    savedMovies.add(dbMovies);
                }
            }
            count++;
        }
        return savedMovies;
    }

    private List<Movies> getMovies(Element body, Integer movieNumber) {
        List<Movies> savedMovies = new ArrayList<>();
        Elements elements = body.getElementsByClass("flw-item");
        int count = 1;
        for (Element element : elements) {
            if(count <= movieNumber) {
                Movies movie = new Movies();
                movie.setMovieName(element.getElementsByClass("film-name").first().text());
                movie.setMovieReleaseDate(element.getElementsByClass("fdi-item").first().text());
                Element innerElement = element.getElementsByClass("film-poster").first();
                String[] movieId = innerElement.select("a").attr("href").split("/movie/");
                if(movieId.length > 1) {
                    String[] spiltMovieId = movieId[1].split("-");
                    if(spiltMovieId.length > 0) {
                        String streamingId = spiltMovieId[spiltMovieId.length - 1];
                        movie.setMovieStreamingId(streamingId);
                    }
                }
                List<Movies> dbMovie = movieRepository.findByMovieNameAndMovieReleaseDate(element.getElementsByClass("film-name").first().text(),
                        element.getElementsByClass("fdi-item").first().text());
                if(dbMovie.size() == 1) {
                    movie.setId(dbMovie.get(0).getId());
                }
                Movies dbMovies = movieRepository.save(movie);
                savedMovies.add(dbMovies);
                count++;
            }

        }
        return savedMovies;
    }
}
