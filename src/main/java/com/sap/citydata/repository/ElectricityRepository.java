package com.sap.citydata.repository;

import com.sap.citydata.model.Electricity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface ElectricityRepository extends JpaRepository<Electricity, Long> {
    @Query("SELECT e.district AS district, AVG(e.consumption) AS avgConsumption " +
            "FROM Electricity e " +
            "WHERE e.ts >= :startDate AND e.ts <= :endDate " +
            "GROUP BY e.district " +
            "ORDER BY e.district ASC")
    List<ElectricityConsumptionProjection> findAvgConsumptionByDistrict(@Param("startDate") Timestamp startDate,
                                                                        @Param("endDate") Timestamp endDate);

    interface ElectricityConsumptionProjection {
        String getDistrict();
        Double getAvgConsumption();
    }

    // Query for Peak Load Analysis: group by peak period
    @Query("SELECT e.peak AS peak, " +
            "AVG(e.consumption) AS avgConsumption, " +
            "MAX(e.consumption) AS maxConsumption, " +
            "SUM(e.consumption) AS totalConsumption " +
            "FROM Electricity e " +
            "WHERE e.ts >= :startTime " +
            "GROUP BY e.peak " +
            "ORDER BY totalConsumption DESC")
    List<PeakLoadProjection> findPeakLoadAnalysis(@Param("startTime") Timestamp startTime);

    interface PeakLoadProjection {
        String getPeak();
        Double getAvgConsumption();
        Double getMaxConsumption();
        Double getTotalConsumption();
    }

    @Query("SELECT CAST(e.outageTs AS date) AS outageDate, " +
            "COUNT(e) AS outageFrequency, " +
            "AVG(e.outageDur) AS avgOutageDuration, " +
            "SUM(e.outageDur) AS totalOutageDuration " +
            "FROM Electricity e " +
            "WHERE e.outageTs IS NOT NULL AND e.ts >= :startTime " +
            "GROUP BY CAST(e.outageTs AS date) " +
            "ORDER BY outageDate ASC")
    List<OutageMetricsProjection> findOutageMetrics(@Param("startTime") Timestamp startTime);

    interface OutageMetricsProjection {
        java.sql.Date getOutageDate();
        Long getOutageFrequency();
        Double getAvgOutageDuration();
        Double getTotalOutageDuration();
    }
}