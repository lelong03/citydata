package com.sap.citydata.service;

import com.sap.citydata.model.WaterSupply;
import com.sap.citydata.repository.WaterSupplyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class WaterSupplyService {
    private final WaterSupplyRepository repository;

    @Autowired
    public WaterSupplyService(WaterSupplyRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public WaterSupply create(WaterSupply waterSupply) {
        return repository.save(waterSupply);
    }

    @Transactional(readOnly = true)
    public List<WaterSupply> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public WaterSupply findById(Long id) {
        return repository.findById(id).orElse(null);
    }
}

