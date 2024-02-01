package com.kingsly.robotapocalypse.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Robot {

    @NonNull
    private String model;
    @NonNull
    private String serialNumber;
    @NonNull
    private String manufaturedDate;
    @NonNull
    private String category;
}
