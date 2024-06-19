package com.movie.kit.domain;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Trailers {
    private String trailerName;
    private String trailerKey;
    private String trailerType;
    private Date trailerDate;
    private String trailerImage;
    public Trailers(String trailerName, String trailerKey, String trailerType) {
        this.trailerName = trailerName;
        this.trailerKey = trailerKey;
        this.trailerType = trailerType;
    }

    public Trailers(String trailerName, String trailerKey, String trailerType, Date trailerDate) {
        this.trailerName = trailerName;
        this.trailerKey = trailerKey;
        this.trailerType = trailerType;
        this.trailerDate = trailerDate;
    }
}
