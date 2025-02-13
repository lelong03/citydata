package com.sap.citydata.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.citydata.model.Waste;
import com.sap.citydata.service.WasteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WasteController.class)
public class WasteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WasteService wasteService;

    @Autowired
    private ObjectMapper objectMapper; // For JSON conversion

    @Test
    public void testUploadFile_invalid() throws Exception {
        // Test with an empty file (should return bad request)
        MockMultipartFile emptyFile = new MockMultipartFile("file", "empty.csv", "text/csv", new byte[0]);

        mockMvc.perform(multipart("/api/waste/upload").file(emptyFile))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Invalid request: file parameter is missing or not a multipart request.")));
    }

    @Test
    public void testUploadFile_success() throws Exception {
        // Prepare a CSV file with header and two rows
        String csvContent = "freq,segregation,facility,total,recycle,report_date\n"
                + "Daily,Organic,Normal,10.0,50.0,2025-03-01\n"
                + "Daily,Plastic,AtCapacity,20.0,75.0,2025-03-02\n";
        MockMultipartFile file = new MockMultipartFile("file", "waste.csv", "text/csv", csvContent.getBytes());

        // Stub service.uploadWasteData() to return 2 records processed
        when(wasteService.uploadWasteData(any())).thenReturn(2);

        mockMvc.perform(multipart("/api/waste/upload").file(file))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("File processed successfully, records: 2")));
    }

    @Test
    public void testGetAll() throws Exception {
        // Prepare sample Waste objects.
        Waste waste1 = new Waste();
        waste1.setId(1L);
        waste1.setFreq("Daily");
        waste1.setSegregation("Organic");
        waste1.setFacility("Normal");
        waste1.setTotal(10.0);
        waste1.setRecycle(50.0);
        waste1.setReportDate(Date.valueOf(LocalDate.of(2025, 3, 1)));

        Waste waste2 = new Waste();
        waste2.setId(2L);
        waste2.setFreq("Daily");
        waste2.setSegregation("Plastic");
        waste2.setFacility("AtCapacity");
        waste2.setTotal(20.0);
        waste2.setRecycle(75.0);
        waste2.setReportDate(Date.valueOf(LocalDate.of(2025, 3, 2)));

        List<Waste> wastes = Arrays.asList(waste1, waste2);
        when(wasteService.findAll()).thenReturn(wastes);

        mockMvc.perform(get("/api/waste"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].freq", is("Daily")))
                .andExpect(jsonPath("$[0].segregation", is("Organic")))
                .andExpect(jsonPath("$[1].segregation", is("Plastic")));
    }

    @Test
    public void testGetById_found() throws Exception {
        Waste waste = new Waste();
        waste.setId(1L);
        waste.setFreq("Daily");
        waste.setSegregation("Plastic");
        waste.setFacility("AtCapacity");
        waste.setTotal(20.0);
        waste.setRecycle(75.0);
        waste.setReportDate(Date.valueOf(LocalDate.of(2025, 3, 2)));

        when(wasteService.findById(1L)).thenReturn(waste);

        mockMvc.perform(get("/api/waste/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.freq", is("Daily")))
                .andExpect(jsonPath("$.segregation", is("Plastic")));
    }

    @Test
    public void testGetById_notFound() throws Exception {
        when(wasteService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/waste/1"))
                .andExpect(status().isNotFound());
    }
}
