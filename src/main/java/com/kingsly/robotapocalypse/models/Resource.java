package com.kingsly.robotapocalypse.models;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Resource {
    private String name;
    @Embedded
    private Quantity quantity;
    @Id
    @ManyToOne
    @JoinColumn(name = "survivor_id", nullable = false)
    private Survivor survivor;
}
