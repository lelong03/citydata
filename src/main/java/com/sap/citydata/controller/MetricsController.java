package com.sap.citydata.controller;

import com.sap.citydata.dto.electricity.ElectricityConsumptionResponse;
import com.sap.citydata.dto.electricity.OutageMetricsResponse;
import com.sap.citydata.dto.electricity.PeakLoadAnalysisResponse;
import com.sap.citydata.dto.waste.RecyclingRateResponse;
import com.sap.citydata.dto.waste.WasteMetricsResponse;
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

    // Endpoint for Peak Load Analysis
    @GetMapping("/peak_load_analysis")
    public ResponseEntity<List<PeakLoadAnalysisResponse>> getPeakLoadAnalysis(
            @RequestParam(value = "range", required = false, defaultValue = "1week") String range) {
        List<PeakLoadAnalysisResponse> responses = metricsService.getPeakLoadAnalysis(range);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/outage_metrics")
    public ResponseEntity<List<OutageMetricsResponse>> getOutageMetrics(
            @RequestParam(value = "range", required = false, defaultValue = "1week") String range) {
        List<OutageMetricsResponse> responses = metricsService.getOutageMetrics(range);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/recycling_rate")
    public ResponseEntity<RecyclingRateResponse> getRecyclingRate(
            @RequestParam(value = "range", required = false, defaultValue = "1week") String range) {
        RecyclingRateResponse response = metricsService.getRecyclingRate(range);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/waste_metrics")
    public ResponseEntity<List<WasteMetricsResponse>> getWasteMetrics(
            @RequestParam(value = "range", required = false, defaultValue = "1week") String range) {
        List<WasteMetricsResponse> responses = metricsService.getWasteMetrics(range);
        return ResponseEntity.ok(responses);
    }

}
