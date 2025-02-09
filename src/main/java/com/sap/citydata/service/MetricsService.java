package com.sap.citydata.service;

import com.sap.citydata.dto.electricity.ElectricityConsumptionResponse;
import com.sap.citydata.dto.water.AvgWaterConsumptionResponse;
import com.sap.citydata.dto.water.DailyConsumptionResponse;
import com.sap.citydata.dto.water.DailyWaterLossResponse;
import com.sap.citydata.dto.water.DailyWaterQualityResponse;
import com.sap.citydata.repository.ElectricityRepository;
import com.sap.citydata.repository.WaterSupplyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MetricsService {
    private final WaterSupplyRepository waterSupplyRepository;
    private final ElectricityRepository electricityRepository;

    @Autowired
    public MetricsService(WaterSupplyRepository waterSupplyRepository, ElectricityRepository electricityRepository) {
        this.waterSupplyRepository = waterSupplyRepository;
        this.electricityRepository = electricityRepository;
    }

    private LocalDateTime getStartTime(String range) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startTime;
        if ("1week".equalsIgnoreCase(range)) {
            startTime = now.minusWeeks(1);
        } else if ("1month".equalsIgnoreCase(range)) {
            startTime = now.minusMonths(1);
        } else if ("3month".equalsIgnoreCase(range)) {
            startTime = now.minusMonths(3);
        } else { // default is 1 day
            startTime = now.minusDays(1);
        }
        return startTime;
    }

    @Transactional(readOnly = true)
    public List<AvgWaterConsumptionResponse> getAverageWaterConsumption(String range) {
        LocalDateTime startTime = getStartTime(range);
        Timestamp ts = Timestamp.valueOf(startTime);
        List<WaterSupplyRepository.AvgConsumptionBySource> results = waterSupplyRepository.findAverageConsumptionGroupedBySource(ts);
        return results.stream()
                .map(r -> new AvgWaterConsumptionResponse(r.getSource(), r.getAvgConsumption()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DailyConsumptionResponse> getDailyAverageConsumption(String range) {
        LocalDateTime startTime = getStartTime(range);
        Timestamp ts = Timestamp.valueOf(startTime);
        List<WaterSupplyRepository.DailyConsumptionProjection> projections = waterSupplyRepository.findDailyAverageConsumption(ts);
        return projections.stream()
                .map(p -> new DailyConsumptionResponse(p.getDay().toString(), p.getAvgConsumption()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DailyWaterQualityResponse> getDailyAverageWaterQuality(String range) {
        LocalDateTime startTime = getStartTime(range);
        Timestamp ts = Timestamp.valueOf(startTime);
        List<WaterSupplyRepository.DailyWaterQualityProjection> projections = waterSupplyRepository.findDailyAverageWaterQuality(ts);
        return projections.stream()
                .map(p -> new DailyWaterQualityResponse(p.getDay().toString(), p.getAvgPh(), p.getAvgTurbidity()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DailyWaterLossResponse> getDailyWaterLossRate(String range) {
        LocalDateTime startTime = getStartTime(range);
        Timestamp ts = Timestamp.valueOf(startTime);
        List<WaterSupplyRepository.DailyWaterLossProjection> projections = waterSupplyRepository.findDailyWaterLossRate(ts);
        return projections.stream()
                .map(p -> new DailyWaterLossResponse(
                        p.getDay().toString(),
                        p.getTotalConsumption(),
                        p.getLostConsumption(),
                        p.getLossRate()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ElectricityConsumptionResponse> getAverageElectricityConsumption(String startDateStr, String endDateStr) {
        // Parse startDate string
        LocalDate startDate = LocalDate.parse(startDateStr); // expects format yyyy-MM-dd
        LocalDateTime startDateTime = startDate.atStartOfDay();
        Timestamp tsStart = Timestamp.valueOf(startDateTime);

        // Parse endDate string, default to today if missing or empty
        LocalDate endDate;
        if (endDateStr == null || endDateStr.trim().isEmpty()) {
            endDate = LocalDate.now();
        } else {
            endDate = LocalDate.parse(endDateStr);
        }
        // For the end date, we set the time to the end of the day if desired.
        // Here we simply use the start-of-day (inclusive). Adjust as needed.
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
        Timestamp tsEnd = Timestamp.valueOf(endDateTime);

        List<ElectricityRepository.ElectricityConsumptionProjection> projections = electricityRepository.findAvgConsumptionByDistrict(tsStart, tsEnd);
        return projections.stream()
                .map(p -> new ElectricityConsumptionResponse(p.getDistrict(), p.getAvgConsumption()))
                .collect(Collectors.toList());
    }

}

