package com.sap.citydata.repository;

import com.sap.citydata.model.WaterSupply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface WaterSupplyRepository extends JpaRepository<WaterSupply, Long> {

    @Query("SELECT AVG(ws.consumption) FROM WaterSupply ws WHERE ws.ts >= :startTime AND (:source IS NULL OR ws.source = :source)")
    Double findAverageConsumption(@Param("startTime") Timestamp startTime, @Param("source") String source);

    @Query("SELECT ws.source as source, AVG(ws.consumption) as avgConsumption " +
            "FROM WaterSupply ws " +
            "WHERE ws.ts >= :startTime " +
            "GROUP BY ws.source")
    List<AvgConsumptionBySource> findAverageConsumptionGroupedBySource(@Param("startTime") Timestamp startTime);

    interface AvgConsumptionBySource {
        String getSource();
        Double getAvgConsumption();
    }

    // Query that groups average consumption by day (date part of ts)
    @Query("SELECT FUNCTION('DATE', ws.ts) as day, AVG(ws.consumption) as avgConsumption " +
            "FROM WaterSupply ws " +
            "WHERE ws.ts >= :startTime " +
            "GROUP BY FUNCTION('DATE', ws.ts) " +
            "ORDER BY day ASC")
    List<DailyConsumptionProjection> findDailyAverageConsumption(@Param("startTime") Timestamp startTime);

    // Projection interface for grouping result
    interface DailyConsumptionProjection {
        java.sql.Date getDay();
        Double getAvgConsumption();
    }

    // New query to group daily average water quality (pH and turbidity)
    @Query("SELECT FUNCTION('DATE', ws.ts) AS day, AVG(ws.ph) AS avgPh, AVG(ws.turbidity) AS avgTurbidity " +
            "FROM WaterSupply ws " +
            "WHERE ws.ts >= :startTime " +
            "GROUP BY FUNCTION('DATE', ws.ts) " +
            "ORDER BY day ASC")
    List<DailyWaterQualityProjection> findDailyAverageWaterQuality(@Param("startTime") Timestamp startTime);

    interface DailyWaterQualityProjection {
        java.sql.Date getDay();
        Double getAvgPh();
        Double getAvgTurbidity();
    }

    @Query("SELECT FUNCTION('DATE', ws.ts) AS day, " +
            "SUM(ws.consumption) AS totalConsumption, " +
            "SUM(CASE WHEN ws.status = 'Leak' THEN ws.consumption ELSE 0 END) AS lostConsumption, " +
            "ROUND((SUM(CASE WHEN ws.status = 'Leak' THEN ws.consumption ELSE 0 END) / SUM(ws.consumption)) * 100, 2) AS lossRate " +
            "FROM WaterSupply ws " +
            "WHERE ws.ts >= :startTime " +
            "GROUP BY FUNCTION('DATE', ws.ts) " +
            "ORDER BY day ASC")
    List<DailyWaterLossProjection> findDailyWaterLossRate(@Param("startTime") Timestamp startTime);

    interface DailyWaterLossProjection {
        java.sql.Date getDay();
        Double getTotalConsumption();
        Double getLostConsumption();
        Double getLossRate();
    }
}
