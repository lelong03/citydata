package com.sap.citydata.service;

import com.sap.citydata.model.Waste;
import com.sap.citydata.repository.WasteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WasteServiceTest {

    @Test
    public void testCreate() {
        WasteRepository repository = Mockito.mock(WasteRepository.class);
        WasteService service = new WasteService(repository);
        Waste waste = new Waste();
        waste.setFreq("Daily");
        waste.setSegregation("Organic, Plastic");
        waste.setFacility("Normal");
        waste.setTotal(15.0);
        waste.setRecycle(65.0);
        waste.setReportDate(Date.valueOf("2025-02-09"));

        when(repository.save(waste)).thenReturn(waste);
        Waste result = service.create(waste);
        assertNotNull(result);
        verify(repository, times(1)).save(waste);
    }
}
