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

	/*@Autowired
	@Qualifier("targetJdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	*/

	public static void main(String[] args) {
		SpringApplication.run(SpringWebApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		vehicleRepository.findAll().forEach(v -> log.info("id =" + v.getId() + ", license plate = " + v.getLicensePlate()));
		//saveBatch(list);

	}

	/*
	private void saveBatch(List<Vehicle> list) {
		try {
			log.info("saving batch of " + list.size() + "elements");
			jdbcTemplate.batchUpdate(
					"INSERT INTO vehicle (id, license_plate) values (?, ?)",
					createBatchArgs(list)
			);
		} catch (Exception e) {
			log.error("Error saving batch", e);
		}
	}
	*/

	private List createBatchArgs(List<Vehicle> list) {
		List<Object> batch = new ArrayList<>();
		list.forEach( el -> {
			Object[] values = new Object[] {
					el.getId(),
					el.getLicensePlate()
					};
			batch.add(values);
		});
		return batch;
	}
}
