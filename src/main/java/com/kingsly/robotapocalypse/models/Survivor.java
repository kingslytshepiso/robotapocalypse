package com.kingsly.robotapocalypse.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@NoArgsConstructor
// @RequiredArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Survivor {
    @Id
    @NonNull
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String surname;
    @NonNull
    private Integer age;
    @NonNull
    private Gender gender;
    @Embedded
    @NonNull
    private LocationCoordinates lastLocation;
    @NonNull
    private Integer infectionReports;
    @NonNull
    private Boolean isInfected;
    @OneToMany(mappedBy = "survivor", cascade = CascadeType.ALL)
    private List<Resource> resourses;

    // public Survivor(Long id, String name, String surname, Integer age, Gender
    // gender, LocationCoordinates lastLocation,
    // Integer infectionReports, Boolean isInfected){
    // this.id = id;
    // this.name = name;
    // //Other fields
    // }
}
