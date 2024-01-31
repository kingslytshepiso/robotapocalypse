package com.kingsly.robotapocalypse.models;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Embeddable
public class LocationCoordinates {
    @NonNull
    private Float latitude;
    @NonNull
    private Float longitude;
}
