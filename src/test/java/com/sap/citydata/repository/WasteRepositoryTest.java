package com.sap.citydata.repository;

import com.sap.citydata.model.Waste;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class WasteRepositoryTest {

    @Autowired
    private WasteRepository repository;

    @Test
    public void testSaveAndFind() {
        Waste waste = new Waste();
        waste.setFreq("Daily");
        waste.setSegregation("Organic, Plastic");
        waste.setFacility("Normal");
        waste.setTotal(15.0);
        waste.setRecycle(65.0);
        waste.setReportDate(Date.valueOf("2025-02-09"));

        repository.save(waste);
        Waste found = repository.findById(waste.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getFreq()).isEqualTo("Daily");
    }
}
