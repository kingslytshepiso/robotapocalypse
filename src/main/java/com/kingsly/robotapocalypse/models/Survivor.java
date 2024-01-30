package com.kingsly.robotapocalypse.models;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import java.util.Set;

@Data
@Entity
public class Survivor {
    @Id
    private long id;
    private String name;
    private String surname;
    private int age;
    private Gender gender;
    @Embedded
    private LocationCoordinates lastLocation;
    private int infectionReports;
    private boolean isInfected;
    @OneToMany(mappedBy = "survivor")
    private Set<Resource> resourses;
}
