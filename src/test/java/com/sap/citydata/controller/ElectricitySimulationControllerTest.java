package com.sap.citydata.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ElectricitySimulationController.class)
public class ElectricitySimulationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSimulateElectricity() throws Exception {
        // Test the endpoint that returns a single Electricity record
        mockMvc.perform(get("/api/simulate/electricity"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.peak", is("17:00-19:00")))
                .andExpect(jsonPath("$.status", is("Active")))
                .andExpect(jsonPath("$.outageTs").doesNotExist())
                .andExpect(jsonPath("$.outageDur").doesNotExist())
                .andExpect(jsonPath("$.outageArea").doesNotExist())
                .andExpect(jsonPath("$.ts", notNullValue()))
                // Verify that source is one of the allowed values
                .andExpect(jsonPath("$.source", anyOf(is("Solar"), is("Wind"), is("Hydro"), is("Coal"))))
                // Verify that district is one of the allowed values
                .andExpect(jsonPath("$.district", anyOf(is("Manhattan"), is("Brooklyn"), is("Queens"), is("Bronx"), is("Staten Island"))))
                // Check consumption is between 1000 and 5000 (since it's 1000 + random * 4000)
                .andExpect(jsonPath("$.consumption", greaterThanOrEqualTo(1000.0)))
                .andExpect(jsonPath("$.consumption", lessThan(5000.0)));
    }

    @Test
    public void testSimulateElectricityBatch() throws Exception {
        // Test the endpoint that returns a batch of 50 Electricity records.
        // Our controller uses a fixed base timestamp "2025-02-01 08:00:00" and increments each record by one minute.
        mockMvc.perform(get("/api/simulate/electricity/batch"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(50)))
                // Check that the first record has the expected deterministic values:
                .andExpect(jsonPath("$[0].source", is("Solar")))
                .andExpect(jsonPath("$[0].district", is("Manhattan")))
                .andExpect(jsonPath("$[0].consumption", is(2000.0)))
                // Check that its timestamp equals "2025-02-01 08:00:00" (assuming the controller returns that format)
//                .andExpect(jsonPath("$[0].ts", is("2025-02-01 08:00:00")))
                // Check that the second record increments consumption by 50 and district cycles:
                .andExpect(jsonPath("$[1].consumption", is(2050.0)))
                .andExpect(jsonPath("$[1].source", is("Wind")))
                .andExpect(jsonPath("$[1].district", is("Brooklyn")));
    }
}
