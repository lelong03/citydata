package com.sap.citydata.repository;

import com.sap.citydata.model.Electricity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

@DataJpaTest
public class ElectricityRepositoryTest {

    @Autowired
    private ElectricityRepository electricityRepository;

    @BeforeEach
    public void setUp() {
        // Clean the repository before each test
        electricityRepository.deleteAll();
    }

    @Test
    public void testFindAvgConsumptionByDistrict() {
        LocalDateTime now = LocalDateTime.now();

        // Create sample Electricity instances with all fields populated.
        Electricity e1 = new Electricity();
        e1.setSource("Solar");
        e1.setDistrict("Manhattan");
        e1.setConsumption(3200.0);
        e1.setPeak("17:00-19:00");
        e1.setStatus("Active");
        // Provide dummy outage data even if not used here
        e1.setOutageTs(Timestamp.valueOf(now.minusDays(1).withHour(12)));
        e1.setOutageDur(15);
        e1.setOutageArea("Manhattan");
        e1.setTs(Timestamp.valueOf(now.minusHours(3)));
        electricityRepository.save(e1);

        Electricity e2 = new Electricity();
        e2.setSource("Wind");
        e2.setDistrict("Brooklyn");
        e2.setConsumption(2500.0);
        e2.setPeak("18:00-20:00");
        e2.setStatus("Active");
        e2.setOutageTs(Timestamp.valueOf(now.minusDays(1).withHour(12).plusMinutes(5)));
        e2.setOutageDur(20);
        e2.setOutageArea("Brooklyn");
        e2.setTs(Timestamp.valueOf(now.minusHours(2)));
        electricityRepository.save(e2);

        Electricity e3 = new Electricity();
        e3.setSource("Coal");
        e3.setDistrict("Queens");
        e3.setConsumption(4000.0);
        e3.setPeak("16:00-18:00");
        e3.setStatus("Overloaded");
        e3.setOutageTs(Timestamp.valueOf(now.minusDays(1).withHour(12).plusMinutes(10)));
        e3.setOutageDur(30);
        e3.setOutageArea("Queens");
        e3.setTs(Timestamp.valueOf(now.minusHours(1)));
        electricityRepository.save(e3);

        // Query for all records (using a wide window)
        Timestamp start = Timestamp.valueOf(now.minusDays(2));
        Timestamp end = Timestamp.valueOf(now.plusDays(1));
        List<ElectricityRepository.ElectricityConsumptionProjection> projections =
                electricityRepository.findAvgConsumptionByDistrict(start, end);

        // Expect one result per district (3 groups)
        assertThat(projections).hasSize(3);
        for (ElectricityRepository.ElectricityConsumptionProjection projection : projections) {
            switch (projection.getDistrict()) {
                case "Manhattan":
                    assertThat(projection.getAvgConsumption()).isCloseTo(3200.0, within(0.01));
                    break;
                case "Brooklyn":
                    assertThat(projection.getAvgConsumption()).isCloseTo(2500.0, within(0.01));
                    break;
                case "Queens":
                    assertThat(projection.getAvgConsumption()).isCloseTo(4000.0, within(0.01));
                    break;
                default:
                    throw new AssertionError("Unexpected district: " + projection.getDistrict());
            }
        }
    }

    @Test
    public void testFindPeakLoadAnalysis() {
        LocalDateTime now = LocalDateTime.now();

        // Insert three records with different peak periods and non-null outage fields.
        Electricity e1 = new Electricity();
        e1.setSource("Solar");
        e1.setDistrict("Manhattan");
        e1.setConsumption(3200.0);
        e1.setPeak("17:00-19:00");
        e1.setStatus("Active");
        e1.setOutageTs(Timestamp.valueOf(now.minusHours(4)));  // non-null outage timestamp
        e1.setOutageDur(15);
        e1.setOutageArea("Manhattan");
        e1.setTs(Timestamp.valueOf(now.minusHours(3)));
        electricityRepository.save(e1);

        Electricity e2 = new Electricity();
        e2.setSource("Wind");
        e2.setDistrict("Brooklyn");
        e2.setConsumption(2500.0);
        e2.setPeak("18:00-20:00");
        e2.setStatus("Active");
        e2.setOutageTs(Timestamp.valueOf(now.minusHours(3)));  // non-null
        e2.setOutageDur(20);
        e2.setOutageArea("Brooklyn");
        e2.setTs(Timestamp.valueOf(now.minusHours(2)));
        electricityRepository.save(e2);

        Electricity e3 = new Electricity();
        e3.setSource("Coal");
        e3.setDistrict("Queens");
        e3.setConsumption(4000.0);
        e3.setPeak("16:00-18:00");
        e3.setStatus("Overloaded");
        e3.setOutageTs(Timestamp.valueOf(now.minusHours(2)));  // non-null
        e3.setOutageDur(30);
        e3.setOutageArea("Queens");
        e3.setTs(Timestamp.valueOf(now.minusHours(1)));
        electricityRepository.save(e3);

        Timestamp startTime = Timestamp.valueOf(now.minusDays(2));
        List<ElectricityRepository.PeakLoadProjection> projections =
                electricityRepository.findPeakLoadAnalysis(startTime);

        // Expect three groups (one per distinct peak period)
        assertThat(projections).hasSize(3);
        // The query orders by totalConsumption DESC, so the first projection should be for "16:00-18:00"
        ElectricityRepository.PeakLoadProjection first = projections.get(0);
        assertThat(first.getPeak()).isEqualTo("16:00-18:00");
        assertThat(first.getTotalConsumption()).isCloseTo(4000.0, within(0.01));
    }

    @Test
    public void testFindOutageMetrics() {
        LocalDateTime now = LocalDateTime.now();

        // Insert records for two days with full outage data.
        // Day 1:
        Electricity e1 = new Electricity();
        e1.setSource("Solar");
        e1.setDistrict("Manhattan");
        e1.setConsumption(3200.0);
        e1.setPeak("17:00-19:00");
        e1.setStatus("Active");
        e1.setOutageTs(Timestamp.valueOf(now.minusDays(1).withHour(12)));
        e1.setOutageDur(30);
        e1.setOutageArea("Manhattan");
        e1.setTs(Timestamp.valueOf(now.minusDays(1).withHour(8)));
        electricityRepository.save(e1);

        Electricity e2 = new Electricity();
        e2.setSource("Wind");
        e2.setDistrict("Brooklyn");
        e2.setConsumption(2500.0);
        e2.setPeak("18:00-20:00");
        e2.setStatus("Active");
        e2.setOutageTs(Timestamp.valueOf(now.minusDays(1).withHour(13)));
        e2.setOutageDur(20);
        e2.setOutageArea("Brooklyn");
        e2.setTs(Timestamp.valueOf(now.minusDays(1).withHour(8).plusMinutes(30)));
        electricityRepository.save(e2);

        // Day 2:
        Electricity e3 = new Electricity();
        e3.setSource("Coal");
        e3.setDistrict("Queens");
        e3.setConsumption(4000.0);
        e3.setPeak("16:00-18:00");
        e3.setStatus("Overloaded");
        e3.setOutageTs(Timestamp.valueOf(now.withHour(11)));
        e3.setOutageDur(15);
        e3.setOutageArea("Queens");
        e3.setTs(Timestamp.valueOf(now.withHour(9)));
        electricityRepository.save(e3);

        Timestamp startTime = Timestamp.valueOf(now.minusDays(2));
        List<ElectricityRepository.OutageMetricsProjection> projections =
                electricityRepository.findOutageMetrics(startTime);

        // Expect two groups (one for each day with outage data)
        assertThat(projections).hasSize(2);

        // Verify group for Day 1 (should have two outage records)
        ElectricityRepository.OutageMetricsProjection day1 = projections.get(0);
        assertThat(day1.getOutageDate()).isEqualTo(java.sql.Date.valueOf(now.minusDays(1).toLocalDate()));
        // Total duration for day1 = 30 + 20 = 50, average = 25, frequency = 2
        assertThat(day1.getOutageFrequency()).isEqualTo(2L);
        assertThat(day1.getTotalOutageDuration()).isCloseTo(50.0, within(0.01));
        assertThat(day1.getAvgOutageDuration()).isCloseTo(25.0, within(0.01));
    }
}
