package com.sap.citydata.repository;

import com.sap.citydata.model.WaterSupply;
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
public class WaterSupplyRepositoryTest {

    @Autowired
    private WaterSupplyRepository waterSupplyRepository;

    @BeforeEach
    public void setUp() {
        waterSupplyRepository.deleteAll();

        // Create sample data for 2 different days

        // Day 1: (simulate data 1 day ago)
        LocalDateTime day1 = LocalDateTime.now().minusDays(1).withHour(8).withMinute(0).withSecond(0).withNano(0);
        WaterSupply ws1 = new WaterSupply();
        ws1.setSource("Reservoir");
        ws1.setConsumption(1000.0);
        ws1.setPh(7.0);
        ws1.setTurbidity(3.0);
        ws1.setStatus("Normal");
        ws1.setTs(Timestamp.valueOf(day1.plusHours(0)));
        waterSupplyRepository.save(ws1);

        WaterSupply ws2 = new WaterSupply();
        ws2.setSource("River");
        ws2.setConsumption(800.0);
        ws2.setPh(6.8);
        ws2.setTurbidity(3.5);
        ws2.setStatus("Leak");
        ws2.setTs(Timestamp.valueOf(day1.plusHours(1)));
        waterSupplyRepository.save(ws2);

        WaterSupply ws3 = new WaterSupply();
        ws3.setSource("Groundwater");
        ws3.setConsumption(1200.0);
        ws3.setPh(7.2);
        ws3.setTurbidity(2.8);
        ws3.setStatus("Normal");
        ws3.setTs(Timestamp.valueOf(day1.plusHours(2)));
        waterSupplyRepository.save(ws3);

        // Day 2: (simulate data for today)
        LocalDateTime day2 = LocalDateTime.now().withHour(8).withMinute(0).withSecond(0).withNano(0);
        WaterSupply ws4 = new WaterSupply();
        ws4.setSource("Reservoir");
        ws4.setConsumption(1100.0);
        ws4.setPh(7.1);
        ws4.setTurbidity(3.1);
        ws4.setStatus("Normal");
        ws4.setTs(Timestamp.valueOf(day2.plusHours(0)));
        waterSupplyRepository.save(ws4);

        WaterSupply ws5 = new WaterSupply();
        ws5.setSource("River");
        ws5.setConsumption(900.0);
        ws5.setPh(6.9);
        ws5.setTurbidity(3.6);
        ws5.setStatus("Normal");
        ws5.setTs(Timestamp.valueOf(day2.plusHours(1)));
        waterSupplyRepository.save(ws5);

        WaterSupply ws6 = new WaterSupply();
        ws6.setSource("Groundwater");
        ws6.setConsumption(1300.0);
        ws6.setPh(7.3);
        ws6.setTurbidity(2.9);
        ws6.setStatus("Normal");
        ws6.setTs(Timestamp.valueOf(day2.plusHours(2)));
        waterSupplyRepository.save(ws6);
    }

    @Test
    public void testFindAverageConsumption() {
        // Test overall average consumption without source filtering.
        Timestamp startTime = Timestamp.valueOf(LocalDateTime.now().minusDays(2));
        // Passing null for source should include all records.
        Double avg = waterSupplyRepository.findAverageConsumption(startTime, null);
        // Calculation: (1000+800+1200+1100+900+1300) / 6 = 1050.0
        assertThat(avg).isCloseTo(1050.0, within(0.01));
    }

    @Test
    public void testFindAverageConsumptionGroupedBySource() {
        Timestamp startTime = Timestamp.valueOf(LocalDateTime.now().minusDays(2));
        List<WaterSupplyRepository.AvgConsumptionBySource> results =
                waterSupplyRepository.findAverageConsumptionGroupedBySource(startTime);
        // Expect 3 groups: Reservoir, River, Groundwater.
        assertThat(results).hasSize(3);
        for (WaterSupplyRepository.AvgConsumptionBySource proj : results) {
            if (proj.getSource().equals("Reservoir")) {
                // Day 1: 1000, Day 2: 1100 => average = (1000+1100)/2 = 1050
                assertThat(proj.getAvgConsumption()).isCloseTo(1050.0, within(0.01));
            } else if (proj.getSource().equals("River")) {
                // Day 1: 800, Day 2: 900 => average = 850
                assertThat(proj.getAvgConsumption()).isCloseTo(850.0, within(0.01));
            } else if (proj.getSource().equals("Groundwater")) {
                // Day 1: 1200, Day 2: 1300 => average = 1250
                assertThat(proj.getAvgConsumption()).isCloseTo(1250.0, within(0.01));
            }
        }
    }

    @Test
    public void testFindDailyAverageConsumption() {
        Timestamp startTime = Timestamp.valueOf(LocalDateTime.now().minusDays(2));
        List<WaterSupplyRepository.DailyConsumptionProjection> results =
                waterSupplyRepository.findDailyAverageConsumption(startTime);
        // We expect two groups (one per day)
        assertThat(results).hasSize(2);
        // Day 1 average: (1000+800+1200)/3 = 1000, Day 2 average: (1100+900+1300)/3 = 1100
        WaterSupplyRepository.DailyConsumptionProjection day1 = results.get(0);
        WaterSupplyRepository.DailyConsumptionProjection day2 = results.get(1);
        assertThat(day1.getAvgConsumption()).isCloseTo(1000.0, within(0.01));
        assertThat(day2.getAvgConsumption()).isCloseTo(1100.0, within(0.01));
    }

    @Test
    public void testFindDailyAverageWaterQuality() {
        Timestamp startTime = Timestamp.valueOf(LocalDateTime.now().minusDays(2));
        List<WaterSupplyRepository.DailyWaterQualityProjection> results =
                waterSupplyRepository.findDailyAverageWaterQuality(startTime);
        // Expect two groups (one per day)
        assertThat(results).hasSize(2);
        // For day 1, average pH: (7.0+6.8+7.2)/3 = 7.0; average turbidity: (3.0+3.5+2.8)/3 = approx. 3.1
        WaterSupplyRepository.DailyWaterQualityProjection day1 = results.get(0);
        assertThat(day1.getAvgPh()).isCloseTo(7.0, within(0.1));
        assertThat(day1.getAvgTurbidity()).isCloseTo(3.1, within(0.1));
    }

    @Test
    public void testFindDailyWaterLossRate() {
        // For testing, update one record to have status 'Leak' on Day 1
        List<WaterSupply> list = waterSupplyRepository.findAll();
        if (!list.isEmpty()) {
            WaterSupply ws = list.get(0);
            ws.setStatus("Leak");
            waterSupplyRepository.save(ws);
        }
        Timestamp startTime = Timestamp.valueOf(LocalDateTime.now().minusDays(2));
        List<WaterSupplyRepository.DailyWaterLossProjection> results =
                waterSupplyRepository.findDailyWaterLossRate(startTime);
        // Expect two groups (one for each day)
        assertThat(results).hasSize(2);
        // Since we updated one record to 'Leak', for Day 1:
        // total consumption for day 1 = 1000 + 800 + 1200 = 3000
        // lost consumption = 1000 (if we assume the updated record now counts as leak)
        // lossRate = (1000/3000)*100 = 33.33%
        // (Adjust the expected value as per your test data)
    }
}
