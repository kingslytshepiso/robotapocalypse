package com.kingsly.robotapocalypse.models;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class LocationCoordinates {
    private float latitude;
    private float longitude;
}
