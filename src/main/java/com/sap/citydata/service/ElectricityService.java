package com.sap.citydata.service;

import com.sap.citydata.model.Electricity;
import com.sap.citydata.repository.ElectricityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class ElectricityService {
    private final ElectricityRepository repository;

    @Autowired
    public ElectricityService(ElectricityRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Electricity create(Electricity electricity) {
        return repository.save(electricity);
    }

    @Transactional(readOnly = true)
    public List<Electricity> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Electricity findById(Long id) {
        return repository.findById(id).orElse(null);
    }
}

