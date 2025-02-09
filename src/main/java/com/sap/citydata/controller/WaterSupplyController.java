package com.sap.citydata.controller;

import com.sap.citydata.model.WaterSupply;
import com.sap.citydata.service.WaterSupplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/water")
public class WaterSupplyController {
    private final WaterSupplyService service;

    @Autowired
    public WaterSupplyController(WaterSupplyService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<WaterSupply> create(@RequestBody WaterSupply waterSupply) {
        WaterSupply saved = service.create(waterSupply);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<WaterSupply>> getAll() {
        List<WaterSupply> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WaterSupply> getById(@PathVariable Long id) {
        WaterSupply ws = service.findById(id);
        return ws != null ? ResponseEntity.ok(ws) : ResponseEntity.notFound().build();
    }

}

