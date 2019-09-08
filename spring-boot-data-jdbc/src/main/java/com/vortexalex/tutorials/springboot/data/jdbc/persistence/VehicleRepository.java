package com.vortexalex.tutorials.springboot.data.jdbc.persistence;

import org.springframework.data.repository.Repository;

import java.util.stream.Stream;

public interface VehicleRepository extends Repository<Vehicle, Long> {

    Stream<Vehicle> findAll();
}
