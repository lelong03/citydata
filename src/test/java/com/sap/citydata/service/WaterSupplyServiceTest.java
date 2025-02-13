package com.sap.citydata.service;

import com.sap.citydata.model.WaterSupply;
import com.sap.citydata.repository.WaterSupplyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class WaterSupplyServiceTest {

    @Mock
    private WaterSupplyRepository waterSupplyRepository;

    @InjectMocks
    private WaterSupplyService waterSupplyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {
        // Create a sample WaterSupply object
        WaterSupply ws = new WaterSupply();
        ws.setSource("Reservoir");
        ws.setConsumption(1000.0);
        ws.setPh(7.0);
        ws.setTurbidity(3.0);
        ws.setStatus("Normal");
        // Assume ts is set automatically or set it here if needed

        // Stub the repository's save method
        when(waterSupplyRepository.save(ws)).thenReturn(ws);

        // Call the service method
        WaterSupply created = waterSupplyService.create(ws);

        // Assert and verify
        assertThat(created).isNotNull();
        assertThat(created.getSource()).isEqualTo("Reservoir");
        verify(waterSupplyRepository, times(1)).save(ws);
    }

    @Test
    public void testFindAll() {
        // Create sample data
        WaterSupply ws1 = new WaterSupply();
        ws1.setSource("Reservoir");
        ws1.setConsumption(1000.0);

        WaterSupply ws2 = new WaterSupply();
        ws2.setSource("River");
        ws2.setConsumption(800.0);

        List<WaterSupply> list = Arrays.asList(ws1, ws2);
        when(waterSupplyRepository.findAll()).thenReturn(list);

        // Call the service method
        List<WaterSupply> result = waterSupplyService.findAll();

        // Assert and verify
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getSource()).isEqualTo("Reservoir");
        assertThat(result.get(1).getSource()).isEqualTo("River");
        verify(waterSupplyRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        // Create a sample WaterSupply object
        WaterSupply ws = new WaterSupply();
        ws.setSource("Groundwater");
        ws.setConsumption(1200.0);

        when(waterSupplyRepository.findById(1L)).thenReturn(Optional.of(ws));

        // Call the service method
        WaterSupply result = waterSupplyService.findById(1L);

        // Assert and verify
        assertThat(result).isNotNull();
        assertThat(result.getSource()).isEqualTo("Groundwater");
        verify(waterSupplyRepository, times(1)).findById(1L);
    }
}
