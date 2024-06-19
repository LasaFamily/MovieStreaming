package com.movie.kit.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Countries {
    private String countryCode;
    private String countryName;
    private Integer countryIndex;
    private String countryNumber;

    public Countries(String countryCode, String countryName) {
        this.countryCode = countryCode;
        this.countryName = countryName;
    }
}
