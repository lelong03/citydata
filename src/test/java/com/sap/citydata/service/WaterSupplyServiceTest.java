package com.sap.citydata.service;

import com.sap.citydata.model.WaterSupply;
import com.sap.citydata.repository.WaterSupplyRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WaterSupplyServiceTest {

    @Test
    public void testCreate() {
        WaterSupplyRepository repository = Mockito.mock(WaterSupplyRepository.class);
        WaterSupplyService service = new WaterSupplyService(repository);
        WaterSupply ws = new WaterSupply();
        ws.setSource("Reservoir");
        ws.setConsumption(1200.0);
        ws.setPh(7.0);
        ws.setTurbidity(3.0);
        ws.setStatus("Normal");
        ws.setTs(new Timestamp(System.currentTimeMillis()));

        when(repository.save(ws)).thenReturn(ws);
        WaterSupply result = service.create(ws);
        assertNotNull(result);
        verify(repository, times(1)).save(ws);
    }
}
