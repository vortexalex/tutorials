package com.vortexalex.tutorials.spring.web.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class Vehicle {

    @NonNull
    private String licensePlate;

    @NonNull
    private Double weight;
}
