package com.sap.citydata.repository;

import com.sap.citydata.model.Waste;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

@DataJpaTest
public class WasteRepositoryTest {

    @Autowired
    private WasteRepository wasteRepository;

    @Test
    public void testFindOverallRecyclingRate() {
        // Create sample data for March 1, 2025
        Waste waste1 = new Waste();
        waste1.setFreq("Daily");
        waste1.setSegregation("Organic, Plastic");
        waste1.setFacility("Normal");
        waste1.setTotal(10.0);
        waste1.setRecycle(50.0);
        waste1.setReportDate(Date.valueOf(LocalDate.of(2025, 3, 1)));
        wasteRepository.save(waste1);

        Waste waste2 = new Waste();
        waste2.setFreq("Daily");
        waste2.setSegregation("Organic, Plastic, Metal");
        waste2.setFacility("Normal");
        waste2.setTotal(20.0);
        waste2.setRecycle(75.0);
        waste2.setReportDate(Date.valueOf(LocalDate.of(2025, 3, 1)));
        wasteRepository.save(waste2);

        // Expected overall recycling rate (weighted):
        // Recycled amount = 10*50/100 + 20*75/100 = 5 + 15 = 20
        // Total = 10 + 20 = 30, so overall rate = (20/30)*100 = 66.67%
        Double overallRate = wasteRepository.findOverallRecyclingRate(
                Date.valueOf(LocalDate.of(2025, 3, 1)),
                Date.valueOf(LocalDate.of(2025, 3, 1))
        );
        assertThat(overallRate).isCloseTo(66.67, within(0.01));
    }

    @Test
    public void testFindWasteMetrics() {
        // Create sample data for two different days
        Waste wasteDay1 = new Waste();
        wasteDay1.setFreq("Daily");
        wasteDay1.setSegregation("Organic");
        wasteDay1.setFacility("Normal");
        wasteDay1.setTotal(10.0);
        wasteDay1.setRecycle(50.0); // Recycled = 5.0
        wasteDay1.setReportDate(Date.valueOf(LocalDate.of(2025, 3, 1)));
        wasteRepository.save(wasteDay1);

        Waste wasteDay2 = new Waste();
        wasteDay2.setFreq("Daily");
        wasteDay2.setSegregation("Plastic");
        wasteDay2.setFacility("AtCapacity");
        wasteDay2.setTotal(20.0);
        wasteDay2.setRecycle(75.0); // Recycled = 15.0
        wasteDay2.setReportDate(Date.valueOf(LocalDate.of(2025, 3, 2)));
        wasteRepository.save(wasteDay2);

        // Call repository query over both days.
        List<WasteRepository.WasteMetricsProjection> metrics = wasteRepository.findWasteMetrics(
                Date.valueOf(LocalDate.of(2025, 3, 1)),
                Date.valueOf(LocalDate.of(2025, 3, 2))
        );
        // We expect two entries (one per day)
        assertThat(metrics).hasSize(2);

        // Verify the values for day 1
        WasteRepository.WasteMetricsProjection day1 = metrics.get(0);
        assertThat(day1.getReportDate()).isEqualTo(Date.valueOf(LocalDate.of(2025, 3, 1)));
        assertThat(day1.getTotalWasteCollected()).isEqualTo(10.0);
        assertThat(day1.getTotalWasteRecycle()).isEqualTo(5.0);

        // Verify the values for day 2
        WasteRepository.WasteMetricsProjection day2 = metrics.get(1);
        assertThat(day2.getReportDate()).isEqualTo(Date.valueOf(LocalDate.of(2025, 3, 2)));
        assertThat(day2.getTotalWasteCollected()).isEqualTo(20.0);
        assertThat(day2.getTotalWasteRecycle()).isEqualTo(15.0);
    }
}
