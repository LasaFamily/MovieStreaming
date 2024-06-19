package com.movie.kit.test;

import lombok.*;

import java.util.Objects;

public class Person {
    private String personId;
    private String personName;
    private String personImage;
    private String personJob;

    public Person(String personId, String personName, String personImage, String personJob) {
        this.personId = personId;
        this.personName = personName;
        this.personImage = personImage;
        this.personJob = personJob;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonImage() {
        return personImage;
    }

    public void setPersonImage(String personImage) {
        this.personImage = personImage;
    }

    public String getPersonJob() {
        return personJob;
    }

    public void setPersonJob(String personJob) {
        this.personJob = personJob;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(personId, person.personId) && Objects.equals(personName, person.personName) && Objects.equals(personImage, person.personImage) && Objects.equals(personJob, person.personJob);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, personName, personImage, personJob);
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId='" + personId + '\'' +
                ", personName='" + personName + '\'' +
                ", personImage='" + personImage + '\'' +
                ", personJob='" + personJob + '\'' +
                '}';
    }
}
