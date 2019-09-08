package com.vortexalex.tutorials.springboot.data.jdbc;

import com.vortexalex.tutorials.springboot.data.jdbc.persistence.Vehicle;
import com.vortexalex.tutorials.springboot.data.jdbc.persistence.VehicleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@Slf4j
public class SpringWebApplication implements CommandLineRunner {

	// this is auto-configured
	@Autowired
	private VehicleRepository vehicleRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringWebApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		vehicleRepository.findAll().forEach(v -> log.info("id =" + v.getId() + ", license plate = " + v.getLicensePlate()));
	}

}
