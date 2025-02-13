package com.sap.citydata.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.sql.Date;
import java.time.LocalDate;

import com.sap.citydata.dto.electricity.ElectricityConsumptionResponse;
import com.sap.citydata.dto.electricity.OutageMetricsResponse;
import com.sap.citydata.dto.electricity.PeakLoadAnalysisResponse;
import com.sap.citydata.dto.waste.RecyclingRateResponse;
import com.sap.citydata.dto.waste.WasteMetricsResponse;
import com.sap.citydata.dto.water.AvgWaterConsumptionResponse;
import com.sap.citydata.dto.water.DailyConsumptionResponse;
import com.sap.citydata.dto.water.DailyWaterLossResponse;
import com.sap.citydata.dto.water.DailyWaterQualityResponse;
import com.sap.citydata.repository.ElectricityRepository;
import com.sap.citydata.repository.WasteRepository;
import com.sap.citydata.repository.WaterSupplyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

// Assuming these DTOs and repository interfaces are imported from your project package.
@ExtendWith(MockitoExtension.class)
@ExtendWith(MockitoExtension.class)
public class MetricsServiceTest {

    @Mock
    private WaterSupplyRepository waterSupplyRepository;

    @Mock
    private ElectricityRepository electricityRepository;

    @Mock
    private WasteRepository wasteRepository;

    @InjectMocks
    private MetricsService metricsService;

    @Test
    void testGetAverageWaterConsumption() {
        // Arrange: create a dummy projection for average water consumption.
        WaterSupplyRepository.AvgConsumptionBySource dummyProjection = new WaterSupplyRepository.AvgConsumptionBySource() {
            @Override
            public String getSource() {
                return "SourceA";
            }
            @Override
            public Double getAvgConsumption() {
                return 100.0;
            }
        };

        when(waterSupplyRepository.findAverageConsumptionGroupedBySource(any(Timestamp.class)))
                .thenReturn(Collections.singletonList(dummyProjection));

        // Act
        List<AvgWaterConsumptionResponse> responses = metricsService.getAverageWaterConsumption("1week");

        // Assert
        assertEquals(1, responses.size());
        AvgWaterConsumptionResponse response = responses.get(0);
        assertEquals("SourceA", response.getSource());
        assertEquals(100.0, response.getAvgConsumption());
    }

    @Test
    void testGetDailyAverageConsumption() {
        // Arrange: create a dummy projection for daily water consumption.
        WaterSupplyRepository.DailyConsumptionProjection dummyProjection = new WaterSupplyRepository.DailyConsumptionProjection() {
            @Override
            public Date getDay() {
                return Date.valueOf(LocalDate.of(2025, 2, 12));
            }
            @Override
            public Double getAvgConsumption() {
                return 75.0;
            }
        };

        when(waterSupplyRepository.findDailyAverageConsumption(any(Timestamp.class)))
                .thenReturn(Collections.singletonList(dummyProjection));

        // Act
        List<DailyConsumptionResponse> responses = metricsService.getDailyAverageConsumption("1month");

        // Assert
        assertEquals(1, responses.size());
        DailyConsumptionResponse response = responses.get(0);
        // The service maps the java.sql.Date to its String representation.
        assertEquals("2025-02-12", response.getDay());
        assertEquals(75.0, response.getAvgConsumption());
    }

    @Test
    void testGetDailyAverageWaterQuality() {
        // Arrange: create a dummy projection for daily water quality.
        WaterSupplyRepository.DailyWaterQualityProjection dummyProjection = new WaterSupplyRepository.DailyWaterQualityProjection() {
            @Override
            public Date getDay() {
                return Date.valueOf(LocalDate.of(2025, 2, 10));
            }
            @Override
            public Double getAvgPh() {
                return 7.2;
            }
            @Override
            public Double getAvgTurbidity() {
                return 0.5;
            }
        };

        when(waterSupplyRepository.findDailyAverageWaterQuality(any(Timestamp.class)))
                .thenReturn(Collections.singletonList(dummyProjection));

        // Act
        List<DailyWaterQualityResponse> responses = metricsService.getDailyAverageWaterQuality("3month");

        // Assert
        assertEquals(1, responses.size());
        DailyWaterQualityResponse response = responses.get(0);
        assertEquals("2025-02-10", response.getDay());
        assertEquals(7.2, response.getAvgPh());
        assertEquals(0.5, response.getAvgTurbidity());
    }

    @Test
    void testGetDailyWaterLossRate() {
        // Arrange: create a dummy projection for daily water loss.
        WaterSupplyRepository.DailyWaterLossProjection dummyProjection = new WaterSupplyRepository.DailyWaterLossProjection() {
            @Override
            public Date getDay() {
                return Date.valueOf(LocalDate.of(2025, 2, 8));
            }
            @Override
            public Double getTotalConsumption() {
                return 200.0;
            }
            @Override
            public Double getLostConsumption() {
                return 20.0;
            }
            @Override
            public Double getLossRate() {
                return 10.0;
            }
        };

        when(waterSupplyRepository.findDailyWaterLossRate(any(Timestamp.class)))
                .thenReturn(Collections.singletonList(dummyProjection));

        // Act
        List<DailyWaterLossResponse> responses = metricsService.getDailyWaterLossRate("1week");

        // Assert
        assertEquals(1, responses.size());
        DailyWaterLossResponse response = responses.get(0);
        assertEquals("2025-02-08", response.getDay());
        assertEquals(200.0, response.getTotalConsumption());
        assertEquals(20.0, response.getLostConsumption());
        assertEquals(10.0, response.getLossRate());
    }

    @Test
    void testGetAverageElectricityConsumption() {
        // Arrange: create a dummy projection for electricity consumption.
        ElectricityRepository.ElectricityConsumptionProjection dummyProjection = new ElectricityRepository.ElectricityConsumptionProjection() {
            @Override
            public String getDistrict() {
                return "District1";
            }
            @Override
            public Double getAvgConsumption() {
                return 150.0;
            }
        };

        when(electricityRepository.findAvgConsumptionByDistrict(any(Timestamp.class), any(Timestamp.class)))
                .thenReturn(Collections.singletonList(dummyProjection));

        // Act
        List<ElectricityConsumptionResponse> responses =
                metricsService.getAverageElectricityConsumption("2025-01-01", "2025-01-31");

        // Assert
        assertEquals(1, responses.size());
        ElectricityConsumptionResponse response = responses.get(0);
        assertEquals("District1", response.getDistrict());
        assertEquals(150.0, response.getAvgConsumption());
    }

    @Test
    void testGetPeakLoadAnalysis() {
        // Arrange: create a dummy projection for peak load analysis.
        ElectricityRepository.PeakLoadProjection dummyProjection = new ElectricityRepository.PeakLoadProjection() {
            @Override
            public String getPeak() {
                return "17:00-19:00";
            }
            @Override
            public Double getAvgConsumption() {
                return 250.0;
            }
            @Override
            public Double getMaxConsumption() {
                return 350.0;
            }
            @Override
            public Double getTotalConsumption() {
                return 1000.0;
            }
        };

        when(electricityRepository.findPeakLoadAnalysis(any(Timestamp.class)))
                .thenReturn(Collections.singletonList(dummyProjection));

        // Act
        List<PeakLoadAnalysisResponse> responses = metricsService.getPeakLoadAnalysis("1day");

        // Assert
        assertEquals(1, responses.size());
        PeakLoadAnalysisResponse response = responses.get(0);
        assertEquals("17:00-19:00", response.getPeak());
        assertEquals(250.0, response.getAvgConsumption());
        assertEquals(350.0, response.getMaxConsumption());
        assertEquals(1000.0, response.getTotalConsumption());
    }

    @Test
    void testGetOutageMetrics() {
        // Arrange: create a dummy projection for outage metrics.
        ElectricityRepository.OutageMetricsProjection dummyProjection = new ElectricityRepository.OutageMetricsProjection() {
            @Override
            public Date getOutageDate() {
                return Date.valueOf(LocalDate.of(2025, 2, 5));
            }
            @Override
            public Long getOutageFrequency() {
                return 3L;
            }
            @Override
            public Double getAvgOutageDuration() {
                return 1.5;
            }
            @Override
            public Double getTotalOutageDuration() {
                return 4.5;
            }
        };

        when(electricityRepository.findOutageMetrics(any(Timestamp.class)))
                .thenReturn(Collections.singletonList(dummyProjection));

        // Act
        List<OutageMetricsResponse> responses = metricsService.getOutageMetrics("1week");

        // Assert
        assertEquals(1, responses.size());
        OutageMetricsResponse response = responses.get(0);
        assertEquals("2025-02-05", response.getOutageDate());
        assertEquals(3, response.getOutageFrequency());
        assertEquals(1.5, response.getAvgOutageDuration());
        assertEquals(4.5, response.getTotalOutageDuration());
    }

    @Test
    void testGetRecyclingRate() {
        // Arrange: stub the recycling rate call.
        Double dummyRate = 0.65;
        when(wasteRepository.findOverallRecyclingRate(any(Date.class), any(Date.class)))
                .thenReturn(dummyRate);

        // Act
        RecyclingRateResponse response = metricsService.getRecyclingRate("1month");

        // Assert
        assertEquals(dummyRate, response.getRecycledRate());
    }

    @Test
    void testGetWasteMetrics() {
        // Arrange: create a dummy projection for waste metrics.
        WasteRepository.WasteMetricsProjection dummyProjection = new WasteRepository.WasteMetricsProjection() {
            @Override
            public Date getReportDate() {
                return Date.valueOf(LocalDate.of(2025, 2, 3));
            }
            @Override
            public Double getTotalWasteCollected() {
                return 500.0;
            }
            @Override
            public Double getTotalWasteRecycle() {
                return 300.0;
            }
        };

        when(wasteRepository.findWasteMetrics(any(Date.class), any(Date.class)))
                .thenReturn(Collections.singletonList(dummyProjection));

        // Act
        List<WasteMetricsResponse> responses = metricsService.getWasteMetrics("1week");

        // Assert
        assertEquals(1, responses.size());
        WasteMetricsResponse response = responses.get(0);
        assertEquals("2025-02-03", response.getReportDate());
        assertEquals(500.0, response.getTotalWasteCollected());
        assertEquals(300.0, response.getTotalWasteRecycle());
    }
}