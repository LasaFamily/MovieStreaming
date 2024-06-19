package com.movie.kit.util;

import com.movie.kit.domain.Persons;

import java.util.List;
import java.util.stream.Collectors;

public class PersonUtils {
    public static List<Persons> getPersons(List<com.movie.kit.mapping.Persons> persons) {
        return persons.stream().map(PersonUtils::getPerson).collect(Collectors.toList());
    }

    public static Persons getPerson(com.movie.kit.mapping.Persons person) {
        return Persons.builder()
                .personId(person.getId())
                .personPoster(person.getProfile_path())
                .personName(person.getName())
                .personPopularity(person.getPopularity())
                .personDateOfBirth(CommonUtil.formatReleaseDate(person.getBirthday()))
                .build();
    }
}
