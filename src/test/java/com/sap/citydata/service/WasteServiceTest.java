package com.sap.citydata.service;

import com.sap.citydata.model.Waste;
import com.sap.citydata.repository.WasteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class WasteServiceTest {

    @Mock
    private WasteRepository wasteRepository;

    @InjectMocks
    private WasteService wasteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Clear any existing interactions (if needed)
        reset(wasteRepository);
    }

    @Test
    public void testCreate() {
        // Prepare a sample Waste instance with non-null values.
        Waste waste = new Waste();
        waste.setFreq("Daily");
        waste.setSegregation("Organic, Plastic");
        waste.setFacility("Normal");
        waste.setTotal(10.0);
        waste.setRecycle(50.0);
        waste.setReportDate(Date.valueOf(LocalDate.of(2025, 3, 1)));

        // Stub repository.save() to return the same waste.
        when(wasteRepository.save(waste)).thenReturn(waste);

        Waste created = wasteService.create(waste);

        assertThat(created).isNotNull();
        assertThat(created.getFreq()).isEqualTo("Daily");
        assertThat(created.getSegregation()).isEqualTo("Organic, Plastic");
        verify(wasteRepository, times(1)).save(waste);
    }

    @Test
    public void testUploadWasteData() throws Exception {
        // Prepare CSV content with a header and 2 rows
        String header = "freq,segregation,facility,total,recycle,report_date\n";
        String row1 = "Daily,Organic,Normal,10.0,50.0,2025-03-01\n";
        String row2 = "Daily,Plastic,AtCapacity,20.0,75.0,2025-03-02\n";
        String csvContent = header + row1 + row2;
        MultipartFile file = new MockMultipartFile("file", "waste.csv", "text/csv", csvContent.getBytes());

        // Stub repository.saveAll() to return an empty list.
        when(wasteRepository.saveAll(anyList())).thenReturn(new ArrayList<>());

        int count = wasteService.uploadWasteData(file);
        assertThat(count).isEqualTo(2);

        // Verify that repository.saveAll() was called at least once.
        verify(wasteRepository, atLeastOnce()).saveAll(anyList());
    }

    @Test
    public void testFindAll() {
        // Prepare sample data
        Waste waste1 = new Waste();
        waste1.setFreq("Daily");
        waste1.setSegregation("Organic");
        Waste waste2 = new Waste();
        waste2.setFreq("Weekly");
        List<Waste> list = Arrays.asList(waste1, waste2);
        when(wasteRepository.findAll()).thenReturn(list);

        List<Waste> result = wasteService.findAll();
        assertThat(result).hasSize(2);
        verify(wasteRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        // Prepare a sample Waste instance.
        Waste waste = new Waste();
        waste.setFreq("Daily");
        waste.setSegregation("Plastic");
        when(wasteRepository.findById(1L)).thenReturn(Optional.of(waste));

        Waste found = wasteService.findById(1L);
        assertThat(found).isNotNull();
        assertThat(found.getFreq()).isEqualTo("Daily");
        assertThat(found.getSegregation()).isEqualTo("Plastic");
        verify(wasteRepository, times(1)).findById(1L);
    }
}
