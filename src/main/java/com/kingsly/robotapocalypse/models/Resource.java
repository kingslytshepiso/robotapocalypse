package com.kingsly.robotapocalypse.models;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Resource {
    @Id
    @GeneratedValue
    private UUID id;
    @NonNull
    private String name;
    @NonNull
    private String type;
    @Embedded
    @NonNull
    private Quantity quantity;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "survivor_id")
    private Survivor survivor;
}
