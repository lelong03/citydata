package com.sap.citydata.controller;

import com.sap.citydata.dto.electricity.ElectricityConsumptionResponse;
import com.sap.citydata.dto.water.AvgWaterConsumptionResponse;
import com.sap.citydata.dto.water.DailyConsumptionResponse;
import com.sap.citydata.dto.water.DailyWaterLossResponse;
import com.sap.citydata.dto.water.DailyWaterQualityResponse;
import com.sap.citydata.service.MetricsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/metrics")
public class MetricsController {
    private final MetricsService metricsService;

    @Autowired
    public MetricsController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @GetMapping("/avg_consumption_by_source")
    public ResponseEntity<List<AvgWaterConsumptionResponse>> getAvgWaterConsumption(
            @RequestParam(value = "range", required = false, defaultValue = "1day") String range) {
        List<AvgWaterConsumptionResponse> responses = metricsService.getAverageWaterConsumption(range);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/avg_consumption_by_day")
    public ResponseEntity<List<DailyConsumptionResponse>> getDailyAvgConsumption(
            @RequestParam(value = "range", required = false, defaultValue = "1day") String range) {
        List<DailyConsumptionResponse> responses = metricsService.getDailyAverageConsumption(range);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/daily_water_quality")
    public ResponseEntity<List<DailyWaterQualityResponse>> getDailyWaterQuality(
            @RequestParam(value = "range", required = false, defaultValue = "1day") String range) {
        List<DailyWaterQualityResponse> responses = metricsService.getDailyAverageWaterQuality(range);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/daily_water_loss_rate")
    public ResponseEntity<List<DailyWaterLossResponse>> getDailyWaterLossRate(
            @RequestParam(value = "range", required = false, defaultValue = "1week") String range) {
        List<DailyWaterLossResponse> responses = metricsService.getDailyWaterLossRate(range);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/avg_electricity_consumption")
    public ResponseEntity<List<ElectricityConsumptionResponse>> getAvgElectricityConsumption(
            @RequestParam("startDate") String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        List<ElectricityConsumptionResponse> responses = metricsService.getAverageElectricityConsumption(startDate, endDate);
        return ResponseEntity.ok(responses);
    }
}
