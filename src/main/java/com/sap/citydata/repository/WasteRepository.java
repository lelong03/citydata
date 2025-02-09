package com.sap.citydata.repository;

import com.sap.citydata.model.Waste;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WasteRepository extends JpaRepository<Waste, Long> {
}
