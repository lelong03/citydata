package com.sap.citydata.service;

import com.sap.citydata.model.Electricity;
import com.sap.citydata.repository.ElectricityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ElectricityServiceTest {

    @Mock
    private ElectricityRepository electricityRepository;

    @InjectMocks
    private ElectricityService electricityService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() {
        // Prepare a sample Electricity instance
        Electricity electricity = new Electricity();
        electricity.setSource("Solar");
        electricity.setDistrict("Manhattan");
        electricity.setConsumption(3200.0);
        electricity.setPeak("17:00-19:00");
        electricity.setStatus("Active");
        electricity.setOutageTs(Timestamp.valueOf(LocalDateTime.now().minusHours(4)));
        electricity.setOutageDur(20);
        electricity.setOutageArea("Manhattan");
        electricity.setTs(Timestamp.valueOf(LocalDateTime.now().minusHours(3)));

        when(electricityRepository.save(electricity)).thenReturn(electricity);

        Electricity created = electricityService.create(electricity);
        assertThat(created).isNotNull();
        assertThat(created.getDistrict()).isEqualTo("Manhattan");

        verify(electricityRepository, times(1)).save(electricity);
    }

    @Test
    public void testFindAll() {
        // Prepare sample data
        Electricity e1 = new Electricity();
        e1.setSource("Solar");
        e1.setDistrict("Manhattan");
        e1.setConsumption(3200.0);
        e1.setPeak("17:00-19:00");
        e1.setStatus("Active");
        e1.setOutageTs(Timestamp.valueOf(LocalDateTime.now().minusHours(4)));
        e1.setOutageDur(20);
        e1.setOutageArea("Manhattan");
        e1.setTs(Timestamp.valueOf(LocalDateTime.now().minusHours(3)));

        Electricity e2 = new Electricity();
        e2.setSource("Wind");
        e2.setDistrict("Brooklyn");
        e2.setConsumption(2500.0);
        e2.setPeak("18:00-20:00");
        e2.setStatus("Active");
        e2.setOutageTs(Timestamp.valueOf(LocalDateTime.now().minusHours(3)));
        e2.setOutageDur(15);
        e2.setOutageArea("Brooklyn");
        e2.setTs(Timestamp.valueOf(LocalDateTime.now().minusHours(2)));

        List<Electricity> list = Arrays.asList(e1, e2);
        when(electricityRepository.findAll()).thenReturn(list);

        List<Electricity> result = electricityService.findAll();
        assertThat(result).hasSize(2);
        verify(electricityRepository, times(1)).findAll();
    }

    @Test
    public void testFindById() {
        Electricity e1 = new Electricity();
        e1.setSource("Coal");
        e1.setDistrict("Queens");
        e1.setConsumption(4000.0);
        e1.setPeak("16:00-18:00");
        e1.setStatus("Overloaded");
        e1.setOutageTs(Timestamp.valueOf(LocalDateTime.now().minusHours(2)));
        e1.setOutageDur(30);
        e1.setOutageArea("Queens");
        e1.setTs(Timestamp.valueOf(LocalDateTime.now().minusHours(1)));

        when(electricityRepository.findById(1L)).thenReturn(Optional.of(e1));

        Electricity result = electricityService.findById(1L);
        assertThat(result).isNotNull();
        assertThat(result.getSource()).isEqualTo("Coal");
        verify(electricityRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateAll() {
        Electricity e1 = new Electricity();
        e1.setSource("Solar");
        e1.setDistrict("Manhattan");
        e1.setConsumption(3200.0);
        e1.setPeak("17:00-19:00");
        e1.setStatus("Active");
        e1.setOutageTs(Timestamp.valueOf(LocalDateTime.now().minusHours(4)));
        e1.setOutageDur(20);
        e1.setOutageArea("Manhattan");
        e1.setTs(Timestamp.valueOf(LocalDateTime.now().minusHours(3)));

        Electricity e2 = new Electricity();
        e2.setSource("Wind");
        e2.setDistrict("Brooklyn");
        e2.setConsumption(2500.0);
        e2.setPeak("18:00-20:00");
        e2.setStatus("Active");
        e2.setOutageTs(Timestamp.valueOf(LocalDateTime.now().minusHours(3)));
        e2.setOutageDur(15);
        e2.setOutageArea("Brooklyn");
        e2.setTs(Timestamp.valueOf(LocalDateTime.now().minusHours(2)));

        List<Electricity> list = Arrays.asList(e1, e2);
        when(electricityRepository.saveAll(list)).thenReturn(list);

        List<Electricity> createdList = electricityService.createAll(list);
        assertThat(createdList).hasSize(2);
        verify(electricityRepository, times(1)).saveAll(list);
    }
}
