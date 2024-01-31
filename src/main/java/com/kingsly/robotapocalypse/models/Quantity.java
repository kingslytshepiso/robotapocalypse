package com.kingsly.robotapocalypse.models;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class Quantity {
    @NonNull
    private Double quantityValue;
    @NonNull
    private String quantityUnit;
}
