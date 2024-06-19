package com.movie.kit.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CastCrew {
    private String personId;
    private String personName;
    private String personImage;
    private String personJob;
}
