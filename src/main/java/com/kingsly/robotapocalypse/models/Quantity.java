package com.kingsly.robotapocalypse.models;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class Quantity {
    private double quantityValue;
    private String quantityUnit;
}
