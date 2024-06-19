package com.movie.kit.test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PersonTest {
    public static void main(String[] args) {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("101", "Lokesh", "10", "JAVA"));
        personList.add(new Person("102", "Aliya", "20", "JAVA"));
        personList.add(new Person("102", "Aliya", "10", "JAVA"));
        personList.add(new Person("103", "Kumar", "40", "JAVA"));
        personList.add(new Person("104", "Ayaan", "50", "JAVA"));
        personList.add(new Person("105", "Saira", "50", "JAVA"));

        List<Person> list = personList.stream().sorted(Comparator.comparing(Person::getPersonName)
                .thenComparing(Person::getPersonImage)).collect(Collectors.toList());
        System.out.println(list);




//        List<Person> people = personList.stream().distinct().collect(Collectors.toList());
//        System.out.println(people.size());

    }
}
