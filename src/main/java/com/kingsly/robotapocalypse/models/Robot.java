package com.kingsly.robotapocalypse.models;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Robot {
    @NonNull
    private String modelNumber;
    @NonNull
    private String serialNumber;
    @NonNull
    private LocalDateTime manufaturedDate;
    @NonNull
    private String category;
}
