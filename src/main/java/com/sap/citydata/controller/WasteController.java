package com.sap.citydata.controller;

import com.sap.citydata.model.Waste;
import com.sap.citydata.service.WasteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/waste")
public class WasteController {
    private final WasteService service;

    @Autowired
    public WasteController(WasteService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        // Validate that the request is a multipart request and that the file is not null or empty
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid request: file parameter is missing or not a multipart request.");
        }
        try {
            int count = service.uploadWasteData(file);
            return ResponseEntity.ok("File processed successfully, records: " + count);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing file: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Waste>> getAll() {
        List<Waste> list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Waste> getById(@PathVariable Long id) {
        Waste waste = service.findById(id);
        return waste != null ? ResponseEntity.ok(waste) : ResponseEntity.notFound().build();
    }
}

