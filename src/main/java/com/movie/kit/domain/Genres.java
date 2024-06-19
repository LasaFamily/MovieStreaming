package com.movie.kit.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Genres {
    private Integer genericId;
    private String genericName;
}
