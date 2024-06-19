package com.movie.kit.repository;

import com.movie.kit.model.Shows;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Shows, Long> {
    List<Shows> findByShowNameAndSeasonNumberAndEpisodeNumber(String showName, String seasonNumber, String episodeNumber);
}
