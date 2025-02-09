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
}