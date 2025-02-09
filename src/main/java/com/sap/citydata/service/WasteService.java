package com.sap.citydata.service;


import com.sap.citydata.model.Waste;
import com.sap.citydata.repository.WasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class WasteService {
    private final WasteRepository repository;

    @Autowired
    public WasteService(WasteRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Waste create(Waste waste) {
        return repository.save(waste);
    }

    @Transactional
    public int uploadWasteData(MultipartFile file) throws Exception {
        int count = 0;
        List<Waste> batch = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            // Assume first line is header
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                Waste waste = new Waste();
                waste.setFreq(tokens[0].trim());
                waste.setSegregation(tokens[1].trim());
                waste.setFacility(tokens[2].trim());
                waste.setTotal(Double.parseDouble(tokens[3].trim()));
                waste.setRecycle(Double.parseDouble(tokens[4].trim()));
                waste.setReportDate(Date.valueOf(tokens[5].trim()));
                batch.add(waste);
                count++;
                if (batch.size() >= 1000) {
                    repository.saveAll(batch);
                    batch.clear();
                }
            }
            if (!batch.isEmpty()) {
                repository.saveAll(batch);
            }
        }
        return count;
    }

    @Transactional(readOnly = true)
    public List<Waste> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Waste findById(Long id) {
        return repository.findById(id).orElse(null);
    }
}
