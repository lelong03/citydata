package com.sap.citydata.repository;

import com.sap.citydata.model.Electricity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ElectricityRepositoryTest {

    @Autowired
    private ElectricityRepository repository;

    @Test
    public void testSaveAndFind() {
        Electricity elec = new Electricity();
        elec.setSource("Solar");
        elec.setDistrict("Central");
        elec.setConsumption(2500.0);
        elec.setPeak("17:00-19:00");
        elec.setStatus("Active");
        elec.setTs(new Timestamp(System.currentTimeMillis()));

        repository.save(elec);
        Electricity found = repository.findById(elec.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getSource()).isEqualTo("Solar");
    }
}

