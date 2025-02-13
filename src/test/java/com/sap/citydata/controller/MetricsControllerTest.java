package com.sap.citydata.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MetricsController.class)
public class MetricsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MetricsService metricsService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAvgConsumptionBySource() throws Exception {
        // Dummy response for AvgWaterConsumption
        AvgWaterConsumptionResponse resp1 = new AvgWaterConsumptionResponse("Reservoir", 1100.0);
        AvgWaterConsumptionResponse resp2 = new AvgWaterConsumptionResponse("River", 900.0);
        List<AvgWaterConsumptionResponse> list = Arrays.asList(resp1, resp2);
        when(metricsService.getAverageWaterConsumption(anyString())).thenReturn(list);

        mockMvc.perform(get("/api/metrics/avg_consumption_by_source")
                        .param("range", "1day")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].source", is("Reservoir")))
                .andExpect(jsonPath("$[0].avgConsumption", is(1100.0)))
                .andExpect(jsonPath("$[1].source", is("River")));
    }

    @Test
    public void testGetDailyAvgConsumption() throws Exception {
        DailyConsumptionResponse dcr1 = new DailyConsumptionResponse("2025-03-01", 1000.0);
        DailyConsumptionResponse dcr2 = new DailyConsumptionResponse("2025-03-02", 1100.0);
        List<DailyConsumptionResponse> list = Arrays.asList(dcr1, dcr2);
        when(metricsService.getDailyAverageConsumption(anyString())).thenReturn(list);

        mockMvc.perform(get("/api/metrics/avg_consumption_by_day")
                        .param("range", "1day")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].day", is("2025-03-01")))
                .andExpect(jsonPath("$[0].avgConsumption", is(1000.0)));
    }

    @Test
    public void testGetDailyWaterQuality() throws Exception {
        DailyWaterQualityResponse quality1 = new DailyWaterQualityResponse("2025-03-01", 7.0, 3.0);
        DailyWaterQualityResponse quality2 = new DailyWaterQualityResponse("2025-03-02", 7.1, 3.2);
        List<DailyWaterQualityResponse> list = Arrays.asList(quality1, quality2);
        when(metricsService.getDailyAverageWaterQuality(anyString())).thenReturn(list);

        mockMvc.perform(get("/api/metrics/daily_water_quality")
                        .param("range", "1day")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].day", is("2025-03-01")))
                .andExpect(jsonPath("$[0].avgPh", is(7.0)))
                .andExpect(jsonPath("$[0].avgTurbidity", is(3.0)));
    }

    @Test
    public void testGetDailyWaterLossRate() throws Exception {
        DailyWaterLossResponse loss1 = new DailyWaterLossResponse("2025-03-01", 3000.0, 1000.0, 33.33);
        DailyWaterLossResponse loss2 = new DailyWaterLossResponse("2025-03-02", 3500.0, 700.0, 20.0);
        List<DailyWaterLossResponse> list = Arrays.asList(loss1, loss2);
        when(metricsService.getDailyWaterLossRate(anyString())).thenReturn(list);

        mockMvc.perform(get("/api/metrics/daily_water_loss_rate")
                        .param("range", "1week")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].day", is("2025-03-01")))
                .andExpect(jsonPath("$[0].totalConsumption", is(3000.0)))
                .andExpect(jsonPath("$[0].lostConsumption", is(1000.0)))
                .andExpect(jsonPath("$[0].lossRate", is(33.33)));
    }

    @Test
    public void testGetAvgElectricityConsumption() throws Exception {
        ElectricityConsumptionResponse elecResp1 = new ElectricityConsumptionResponse("Manhattan", 3200.0);
        ElectricityConsumptionResponse elecResp2 = new ElectricityConsumptionResponse("Brooklyn", 2500.0);
        List<ElectricityConsumptionResponse> list = Arrays.asList(elecResp1, elecResp2);
        when(metricsService.getAverageElectricityConsumption(eq("2025-03-01"), anyString())).thenReturn(list);

        mockMvc.perform(get("/api/metrics/avg_electricity_consumption")
                        .param("startDate", "2025-03-01")
                        .param("endDate", "2025-03-02")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].district", is("Manhattan")))
                .andExpect(jsonPath("$[0].avgConsumption", is(3200.0)));
    }

    @Test
    public void testGetPeakLoadAnalysis() throws Exception {
        PeakLoadAnalysisResponse peakResp1 = new PeakLoadAnalysisResponse("16:00-18:00", 3500.0, 4000.0, 10500.0);
        PeakLoadAnalysisResponse peakResp2 = new PeakLoadAnalysisResponse("17:00-19:00", 3200.0, 3200.0, 3200.0);
        List<PeakLoadAnalysisResponse> list = Arrays.asList(peakResp1, peakResp2);
        when(metricsService.getPeakLoadAnalysis(anyString())).thenReturn(list);

        mockMvc.perform(get("/api/metrics/peak_load_analysis")
                        .param("range", "1week")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].peak", is("16:00-18:00")))
                .andExpect(jsonPath("$[0].totalConsumption", is(10500.0)));
    }

    @Test
    public void testGetOutageMetrics() throws Exception {
        OutageMetricsResponse outageResp1 = new OutageMetricsResponse("2025-03-01", 2L, 25.0, 50.0);
        OutageMetricsResponse outageResp2 = new OutageMetricsResponse("2025-03-02", 1L, 15.0, 15.0);
        List<OutageMetricsResponse> list = Arrays.asList(outageResp1, outageResp2);
        when(metricsService.getOutageMetrics(anyString())).thenReturn(list);

        mockMvc.perform(get("/api/metrics/outage_metrics")
                        .param("range", "1week")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].outageDate", is("2025-03-01")))
                .andExpect(jsonPath("$[0].outageFrequency", is(2)))
                .andExpect(jsonPath("$[0].avgOutageDuration", is(25.0)))
                .andExpect(jsonPath("$[0].totalOutageDuration", is(50.0)));
    }

    @Test
    public void testGetRecyclingRate() throws Exception {
        RecyclingRateResponse recyclingRate = new RecyclingRateResponse(66.67);
        // Assuming nonRecycled = 33.33 computed in the DTO constructor
        when(metricsService.getRecyclingRate(anyString())).thenReturn(recyclingRate);

        mockMvc.perform(get("/api/metrics/recycling_rate")
                        .param("range", "1week")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recycledRate", is(66.67)))
                .andExpect(jsonPath("$.nonRecycledRate", is(33.33)));
    }

    @Test
    public void testGetWasteMetrics() throws Exception {
        WasteMetricsResponse wasteResp1 = new WasteMetricsResponse("2025-03-01", 30.0, 20.0);
        WasteMetricsResponse wasteResp2 = new WasteMetricsResponse("2025-03-02", 15.0, 9.0);
        List<WasteMetricsResponse> list = Arrays.asList(wasteResp1, wasteResp2);
        when(metricsService.getWasteMetrics(anyString())).thenReturn(list);

        mockMvc.perform(get("/api/metrics/waste_metrics")
                        .param("range", "1week")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].reportDate", is("2025-03-01")))
                .andExpect(jsonPath("$[0].totalWasteCollected", is(30.0)))
                .andExpect(jsonPath("$[0].totalWasteRecycle", is(20.0)));
    }
}
