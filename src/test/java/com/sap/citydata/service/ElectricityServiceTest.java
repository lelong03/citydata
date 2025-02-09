package com.sap.citydata.service;

import com.sap.citydata.model.Electricity;
import com.sap.citydata.repository.ElectricityRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ElectricityServiceTest {

    @Test
    public void testCreate() {
        ElectricityRepository repository = Mockito.mock(ElectricityRepository.class);
        ElectricityService service = new ElectricityService(repository);
        Electricity elec = new Electricity();
        elec.setSource("Solar");
        elec.setDistrict("Central");
        elec.setConsumption(2500.0);
        elec.setPeak("17:00-19:00");
        elec.setStatus("Active");
        elec.setTs(new Timestamp(System.currentTimeMillis()));

        when(repository.save(elec)).thenReturn(elec);
        Electricity result = service.create(elec);
        assertNotNull(result);
        verify(repository, times(1)).save(elec);
    }
}
