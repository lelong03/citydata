package com.sap.citydata.controller;

import com.sap.citydata.model.Electricity;
import com.sap.citydata.service.ElectricityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/electricity")
public class ElectricityController {
    private final ElectricityService service;

    @Autowired
    public ElectricityController(ElectricityService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Electricity> create(@RequestBody Electricity electricity) {
        Electricity saved = service.create(electricity);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Electricity>> getAll() {
        List<Electricity> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Electricity> getById(@PathVariable Long id) {
        Electricity elec = service.findById(id);
        return elec != null ? ResponseEntity.ok(elec) : ResponseEntity.notFound().build();
    }
}

