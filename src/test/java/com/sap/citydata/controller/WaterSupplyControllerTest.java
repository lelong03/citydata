package com.sap.citydata.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.citydata.model.WaterSupply;
import com.sap.citydata.service.WaterSupplyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WaterSupplyController.class)
public class WaterSupplyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WaterSupplyService waterSupplyService;

    @Autowired
    private ObjectMapper objectMapper; // For converting objects to JSON

    @Test
    public void testCreateWaterSupply() throws Exception {
        // Prepare a sample WaterSupply object.
        WaterSupply ws = new WaterSupply();
        ws.setSource("Reservoir");
        ws.setConsumption(1000.0);
        ws.setPh(7.0);
        ws.setTurbidity(3.0);
        ws.setStatus("Normal");
        ws.setTs(Timestamp.valueOf(LocalDateTime.now()));

        // Stub the service's create method.
        when(waterSupplyService.create(any(WaterSupply.class))).thenReturn(ws);

        mockMvc.perform(post("/api/water")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ws)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.source", is("Reservoir")))
                .andExpect(jsonPath("$.consumption", is(1000.0)))
                .andExpect(jsonPath("$.ph", is(7.0)))
                .andExpect(jsonPath("$.turbidity", is(3.0)))
                .andExpect(jsonPath("$.status", is("Normal")));
    }

    @Test
    public void testGetAllWaterSupply() throws Exception {
        // Create sample WaterSupply objects.
        WaterSupply ws1 = new WaterSupply();
        ws1.setSource("Reservoir");
        ws1.setConsumption(1000.0);
        ws1.setPh(7.0);
        ws1.setTurbidity(3.0);
        ws1.setStatus("Normal");
        ws1.setTs(Timestamp.valueOf(LocalDateTime.now()));

        WaterSupply ws2 = new WaterSupply();
        ws2.setSource("River");
        ws2.setConsumption(800.0);
        ws2.setPh(6.8);
        ws2.setTurbidity(3.5);
        ws2.setStatus("Leak");
        ws2.setTs(Timestamp.valueOf(LocalDateTime.now()));

        List<WaterSupply> list = Arrays.asList(ws1, ws2);
        when(waterSupplyService.findAll()).thenReturn(list);

        mockMvc.perform(get("/api/water"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].source", is("Reservoir")))
                .andExpect(jsonPath("$[1].source", is("River")));
    }

    @Test
    public void testGetWaterSupplyByIdFound() throws Exception {
        // Prepare a sample WaterSupply object.
        WaterSupply ws = new WaterSupply();
        ws.setSource("Groundwater");
        ws.setConsumption(1200.0);
        ws.setPh(7.2);
        ws.setTurbidity(2.8);
        ws.setStatus("Normal");
        ws.setTs(Timestamp.valueOf(LocalDateTime.now()));

        when(waterSupplyService.findById(1L)).thenReturn(ws);

        mockMvc.perform(get("/api/water/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.source", is("Groundwater")))
                .andExpect(jsonPath("$.consumption", is(1200.0)));
    }

    @Test
    public void testGetWaterSupplyByIdNotFound() throws Exception {
        when(waterSupplyService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/water/1"))
                .andExpect(status().isNotFound());
    }
}
