package com.sap.citydata.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.citydata.model.Electricity;
import com.sap.citydata.service.ElectricityService;
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

@WebMvcTest(ElectricityController.class)
public class ElectricityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ElectricityService service;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testCreateElectricity() throws Exception {
        Electricity elec = new Electricity();
        elec.setId(1L);
        elec.setSource("Solar");
        elec.setDistrict("Central");
        elec.setConsumption(2500.0);
        elec.setPeak("17:00-19:00");
        elec.setStatus("Active");
        elec.setTs(new Timestamp(System.currentTimeMillis()));

        Mockito.when(service.create(Mockito.any(Electricity.class))).thenReturn(elec);

        mockMvc.perform(post("/api/electricity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(elec)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testGetAllElectricity() throws Exception {
        Electricity elec = new Electricity();
        elec.setId(1L);
        elec.setSource("Solar");
        elec.setDistrict("Central");
        elec.setConsumption(2500.0);
        elec.setPeak("17:00-19:00");
        elec.setStatus("Active");
        elec.setTs(new Timestamp(System.currentTimeMillis()));

        Mockito.when(service.findAll()).thenReturn(Collections.singletonList(elec));

        mockMvc.perform(get("/api/electricity"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }
}

