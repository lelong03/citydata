package com.sap.citydata.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sap.citydata.model.Waste;
import com.sap.citydata.service.WasteService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WasteController.class)
public class WasteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WasteService service;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testUploadWasteFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "waste.csv", "text/csv",
                "freq,segregation,facility,total,recycle,report_date\nDaily,Organic,Normal,15.0,65.0,2025-02-09".getBytes());

        Mockito.when(service.uploadWasteData(Mockito.any())).thenReturn(1);

        mockMvc.perform(multipart("/api/waste/upload").file(file))
                .andExpect(status().isOk())
                .andExpect(content().string("File processed successfully, records: 1"));
    }

    @Test
    public void testGetAllWaste() throws Exception {
        Waste waste = new Waste();
        waste.setId(1L);
        waste.setFreq("Daily");
        waste.setSegregation("Organic, Plastic");
        waste.setFacility("Normal");
        waste.setTotal(15.0);
        waste.setRecycle(65.0);
        waste.setReportDate(Date.valueOf("2025-02-09"));

        Mockito.when(service.findAll()).thenReturn(Collections.singletonList(waste));

        mockMvc.perform(get("/api/waste"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].freq").value("Daily"))
        ;
    }
}

