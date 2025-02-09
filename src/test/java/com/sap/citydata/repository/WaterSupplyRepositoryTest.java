package com.sap.citydata.repository;

import com.sap.citydata.model.WaterSupply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class WaterSupplyRepositoryTest {

    @Autowired
    private WaterSupplyRepository repository;

    @Test
    public void testSaveAndFind() {
        WaterSupply ws = new WaterSupply();
        ws.setSource("Reservoir");
        ws.setConsumption(1200.0);
        ws.setPh(7.0);
        ws.setTurbidity(3.0);
        ws.setStatus("Normal");
        ws.setTs(new Timestamp(System.currentTimeMillis()));

        repository.save(ws);
        WaterSupply found = repository.findById(ws.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getSource()).isEqualTo("Reservoir");
    }
}

