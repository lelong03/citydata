package com.sap.citydata.repository;

import com.sap.citydata.model.Waste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;


public interface WasteRepository extends JpaRepository<Waste, Long> {
    @Query("SELECT ROUND((SUM(w.total * w.recycle / 100) / SUM(w.total)) * 100, 2) " +
            "FROM Waste w " +
            "WHERE w.reportDate BETWEEN :startDate AND :endDate")
    Double findOverallRecyclingRate(@Param("startDate") Date startDate,
                                    @Param("endDate") Date endDate);

    @Query("SELECT w.reportDate AS reportDate, " +
            "ROUND(SUM(w.total), 2) AS totalWasteCollected, " +
            "ROUND(SUM(w.total * w.recycle / 100), 2) AS totalWasteRecycle " +
            "FROM Waste w " +
            "WHERE w.reportDate BETWEEN :startDate AND :endDate " +
            "GROUP BY w.reportDate " +
            "ORDER BY w.reportDate ASC")
    List<WasteMetricsProjection> findWasteMetrics(@Param("startDate") Date startDate,
                                                  @Param("endDate") Date endDate);

    interface WasteMetricsProjection {
        Date getReportDate();
        Double getTotalWasteCollected();
        Double getTotalWasteRecycle();
    }
}
