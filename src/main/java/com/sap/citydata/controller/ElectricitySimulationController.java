package com.sap.citydata.controller;

import com.sap.citydata.model.Electricity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping("/api/simulate")
public class ElectricitySimulationController {
    private final Random random = new Random();

    private static final String[] ENERGY_SOURCES = {"Solar", "Wind", "Hydro", "Coal"};
    private static final String[] DISTRICTS = {"Manhattan", "Brooklyn", "Queens", "Bronx", "Staten Island"};
    private static final int ELECTRICITY_BATCH_SIZE = 50;

    @GetMapping("/electricity")
    public Electricity simulateElectricity() {
        Electricity electricity = new Electricity();
        // Reuse constants for energy source and district selection
        electricity.setSource(ENERGY_SOURCES[random.nextInt(ENERGY_SOURCES.length)]);
        electricity.setDistrict(DISTRICTS[random.nextInt(DISTRICTS.length)]);
        electricity.setConsumption(1000 + random.nextDouble() * 4000);
        electricity.setPeak("17:00-19:00");
        electricity.setStatus("Active");
        // For simulation, no outage is set
        electricity.setOutageTs(null);
        electricity.setOutageDur(null);
        electricity.setOutageArea(null);
        electricity.setTs(new Timestamp(System.currentTimeMillis()));
        return electricity;
    }

    @GetMapping("/electricity/batch")
    public List<Electricity> simulateElectricityBatch() {
        List<Electricity> list = new ArrayList<>();
        // Use a fixed base timestamp for repeatability.
        Timestamp baseTs = Timestamp.valueOf("2025-02-01 08:00:00");

        // Generate 50 records with deterministic values.
        for (int i = 0; i < ELECTRICITY_BATCH_SIZE; i++) {
            Electricity electricity = new Electricity();
            // Cycle through energy sources and districts deterministically.
            electricity.setSource(ENERGY_SOURCES[i % ENERGY_SOURCES.length]);
            electricity.setDistrict(DISTRICTS[i % DISTRICTS.length]);
            // For consumption, use a base value of 2000 and increment by 50 each time.
            electricity.setConsumption((double) (2000 + i * 50));
            electricity.setPeak("17:00-19:00");
            electricity.setStatus("Active");
            electricity.setOutageTs(null);
            electricity.setOutageDur(null);
            electricity.setOutageArea(null);
            // Each record is timestamped one minute apart from the base time.
            electricity.setTs(new Timestamp(baseTs.getTime() + i * 60000));
            list.add(electricity);
        }
        return list;
    }
}
