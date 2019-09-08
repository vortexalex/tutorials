package com.vortexalex.tutorials.springboot.data.jdbc.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
public class Vehicle {

    @Id
    private Long id;

    private String licensePlate;
}
