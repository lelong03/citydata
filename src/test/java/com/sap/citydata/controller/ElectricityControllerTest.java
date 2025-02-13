package com.sap.citydata.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.citydata.model.Electricity;
import com.sap.citydata.service.ElectricityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ElectricityController.class)
public class ElectricityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ElectricityService electricityService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateElectricity() throws Exception {
        // Prepare a sample Electricity object.
        Electricity electricity = new Electricity();
        electricity.setSource("Solar");
        electricity.setDistrict("Manhattan");
        electricity.setConsumption(3200.0);
        electricity.setPeak("17:00-19:00");
        electricity.setStatus("Active");
        electricity.setOutageTs(Timestamp.valueOf(LocalDateTime.now().minusHours(2)));
        electricity.setOutageDur(15);
        electricity.setOutageArea("Manhattan");
        electricity.setTs(Timestamp.valueOf(LocalDateTime.now()));

        // Stub the service's create method.
        when(electricityService.create(any(Electricity.class))).thenReturn(electricity);

        mockMvc.perform(post("/api/electricity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(electricity)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.source", is("Solar")))
                .andExpect(jsonPath("$.district", is("Manhattan")))
                .andExpect(jsonPath("$.consumption", is(3200.0)))
                .andExpect(jsonPath("$.peak", is("17:00-19:00")))
                .andExpect(jsonPath("$.status", is("Active")));
    }

    @Test
    public void testGetAllElectricity() throws Exception {
        // Prepare sample Electricity objects.
        Electricity e1 = new Electricity();
        e1.setSource("Solar");
        e1.setDistrict("Manhattan");
        e1.setConsumption(3200.0);
        e1.setPeak("17:00-19:00");
        e1.setStatus("Active");
        e1.setOutageTs(Timestamp.valueOf(LocalDateTime.now().minusHours(2)));
        e1.setOutageDur(15);
        e1.setOutageArea("Manhattan");
        e1.setTs(Timestamp.valueOf(LocalDateTime.now()));

        Electricity e2 = new Electricity();
        e2.setSource("Wind");
        e2.setDistrict("Brooklyn");
        e2.setConsumption(2500.0);
        e2.setPeak("18:00-20:00");
        e2.setStatus("Active");
        e2.setOutageTs(Timestamp.valueOf(LocalDateTime.now().minusHours(3)));
        e2.setOutageDur(20);
        e2.setOutageArea("Brooklyn");
        e2.setTs(Timestamp.valueOf(LocalDateTime.now()));

        List<Electricity> list = Arrays.asList(e1, e2);
        when(electricityService.findAll()).thenReturn(list);

        mockMvc.perform(get("/api/electricity"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].district", is("Manhattan")))
                .andExpect(jsonPath("$[1].district", is("Brooklyn")));
    }

    @Test
    public void testGetElectricityById_Found() throws Exception {
        Electricity e = new Electricity();
        e.setSource("Coal");
        e.setDistrict("Queens");
        e.setConsumption(4000.0);
        e.setPeak("16:00-18:00");
        e.setStatus("Overloaded");
        e.setOutageTs(Timestamp.valueOf(LocalDateTime.now().minusHours(1)));
        e.setOutageDur(30);
        e.setOutageArea("Queens");
        e.setTs(Timestamp.valueOf(LocalDateTime.now()));

        when(electricityService.findById(1L)).thenReturn(e);

        mockMvc.perform(get("/api/electricity/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.source", is("Coal")))
                .andExpect(jsonPath("$.district", is("Queens")))
                .andExpect(jsonPath("$.consumption", is(4000.0)))
                .andExpect(jsonPath("$.peak", is("16:00-18:00")))
                .andExpect(jsonPath("$.status", is("Overloaded")));
    }

    @Test
    public void testGetElectricityById_NotFound() throws Exception {
        when(electricityService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/electricity/1"))
                .andExpect(status().isNotFound());
    }
}
