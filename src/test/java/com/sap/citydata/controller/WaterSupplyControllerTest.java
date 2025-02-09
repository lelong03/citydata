package com.sap.citydata.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.citydata.model.WaterSupply;
import com.sap.citydata.service.WaterSupplyService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WaterSupplyController.class)
public class WaterSupplyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WaterSupplyService service;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testCreateWaterSupply() throws Exception {
        WaterSupply ws = new WaterSupply();
        ws.setId(1L);
        ws.setSource("Reservoir");
        ws.setConsumption(1200.0);
        ws.setPh(7.0);
        ws.setTurbidity(3.0);
        ws.setStatus("Normal");
        ws.setTs(new Timestamp(System.currentTimeMillis()));

        Mockito.when(service.create(Mockito.any(WaterSupply.class))).thenReturn(ws);

        mockMvc.perform(post("/api/water")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ws)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testGetAllWaterSupply() throws Exception {
        WaterSupply ws = new WaterSupply();
        ws.setId(1L);
        ws.setSource("Reservoir");
        ws.setConsumption(1200.0);
        ws.setPh(7.0);
        ws.setTurbidity(3.0);
        ws.setStatus("Normal");
        ws.setTs(new Timestamp(System.currentTimeMillis()));

        Mockito.when(service.findAll()).thenReturn(Collections.singletonList(ws));

        mockMvc.perform(get("/api/water"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }
}
