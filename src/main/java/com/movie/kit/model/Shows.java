package com.movie.kit.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "shows")
public class Shows {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id")
    private Long id;

    @Column(name = "show_name")
    private String showName;

    @Column(name = "season_number")
    private String seasonNumber;

    @Column(name = "episode_number")
    private String episodeNumber;

    @Column(name = "season_id")
    private String seasonId;

    @Column(name = "episode_id")
    private String episodeId;

    @Column(name = "show_streaming_id")
    private String showStreamingId;

    @Column(name = "show_server_ids")
    private String showServerIds;

    @Column(name = "show_imdb_id")
    private String showImdbId;

}
