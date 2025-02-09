CREATE TABLE water_supply (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    source VARCHAR(20) NOT NULL COMMENT 'Water source: River, Reservoir, Groundwater',
    consumption DOUBLE NOT NULL COMMENT 'Liters per household per day',
    ph DOUBLE NOT NULL COMMENT 'Water quality pH',
    turbidity DOUBLE NOT NULL COMMENT 'Water quality turbidity (NTU)',
    status VARCHAR(20) NOT NULL COMMENT 'Infrastructure status: Normal, Leak, Maintenance',
    ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Reading timestamp'
) ENGINE=InnoDB;
CREATE INDEX idx_water_source ON water_supply(source);
CREATE INDEX idx_water_ts ON water_supply(ts);

CREATE TABLE electricity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    source VARCHAR(20) NOT NULL COMMENT 'Energy source: Solar, Wind, Hydro, Coal',
    district VARCHAR(50) NOT NULL COMMENT 'District/Area',
    consumption DOUBLE NOT NULL COMMENT 'Consumption in kWh',
    peak VARCHAR(20) NOT NULL COMMENT 'Peak load hours (e.g., 17:00-19:00)',
    status VARCHAR(20) NOT NULL COMMENT 'Grid status: Active, Overloaded, Maintenance',
    outage_ts TIMESTAMP NULL COMMENT 'Outage start timestamp',
    outage_dur INT NULL COMMENT 'Outage duration in minutes',
    outage_area VARCHAR(100) NULL COMMENT 'Affected areas during outage',
    ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Reading timestamp'
) ENGINE=InnoDB;
CREATE INDEX idx_elec_source ON electricity(source);
CREATE INDEX idx_elec_district ON electricity(district);
CREATE INDEX idx_elec_ts ON electricity(ts);
CREATE INDEX idx_elec_outage_ts ON electricity(outage_ts);


CREATE TABLE waste (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    freq VARCHAR(20) NOT NULL COMMENT 'Collection frequency: Daily, Weekly',
    segregation VARCHAR(50) NOT NULL COMMENT 'Segregation level (e.g., Organic, Plastic, Metal, Electronic)',
    facility VARCHAR(20) NOT NULL COMMENT 'Facility status: Normal, AtCapacity, Offline',
    total DOUBLE NOT NULL COMMENT 'Total waste collected (tons)',
    recycle DOUBLE NOT NULL COMMENT 'Recycling rate (%)',
    report_date DATE NOT NULL COMMENT 'Reporting date'
) ENGINE=InnoDB;
CREATE INDEX idx_waste_freq ON waste(freq);
CREATE INDEX idx_waste_report_date ON waste(report_date);


-- Insert sample data spanning 10 days with varied consumption
INSERT INTO water_supply (source, consumption, ph, turbidity, status, ts) VALUES
('Reservoir', 1150.0, 7.1, 3.2, 'Normal', '2025-02-01 08:00:00'),
('River', 980.0, 6.9, 3.8, 'Normal', '2025-02-01 09:15:00'),
('Groundwater', 1300.0, 7.3, 2.9, 'Normal', '2025-02-01 10:30:00'),

('Reservoir', 1200.0, 7.0, 3.1, 'Normal', '2025-02-02 08:05:00'),
('River', 850.0, 6.8, 4.0, 'Leak', '2025-02-02 10:45:00'),
('Groundwater', 1400.0, 7.4, 3.0, 'Maintenance', '2025-02-02 11:30:00'),

('Reservoir', 950.0, 7.2, 3.3, 'Normal', '2025-02-03 09:00:00'),
('River', 1020.0, 6.7, 3.9, 'Normal', '2025-02-03 10:00:00'),
('Groundwater', 1250.0, 7.1, 2.8, 'Normal', '2025-02-03 11:15:00'),

('Reservoir', 1100.0, 7.0, 3.0, 'Normal', '2025-02-04 08:30:00'),
('River', 920.0, 6.9, 4.2, 'Normal', '2025-02-04 09:45:00'),
('Groundwater', 1350.0, 7.3, 3.1, 'Normal', '2025-02-04 10:50:00'),

('Reservoir', 1180.0, 7.1, 3.4, 'Normal', '2025-02-05 08:10:00'),
('River', 1050.0, 6.8, 3.7, 'Leak', '2025-02-05 10:30:00'),
('Groundwater', 1420.0, 7.5, 3.0, 'Normal', '2025-02-05 11:40:00'),

('Reservoir', 1250.0, 7.2, 3.3, 'Normal', '2025-02-06 08:20:00'),
('River', 990.0, 6.8, 4.1, 'Normal', '2025-02-06 09:50:00'),
('Groundwater', 1380.0, 7.4, 3.2, 'Maintenance', '2025-02-06 11:00:00'),

('Reservoir', 1120.0, 7.0, 3.1, 'Normal', '2025-02-07 08:15:00'),
('River', 970.0, 6.9, 4.0, 'Normal', '2025-02-07 10:00:00'),
('Groundwater', 1300.0, 7.3, 2.9, 'Normal', '2025-02-07 11:20:00'),

('Reservoir', 1190.0, 7.1, 3.2, 'Normal', '2025-02-08 08:00:00'),
('River', 1010.0, 6.8, 3.8, 'Normal', '2025-02-08 09:30:00'),
('Groundwater', 1370.0, 7.4, 3.0, 'Normal', '2025-02-08 11:00:00'),

('Reservoir', 1150.0, 7.2, 3.3, 'Normal', '2025-02-09 08:10:00'),
('River', 950.0, 6.8, 4.1, 'Normal', '2025-02-09 09:45:00'),
('Groundwater', 1320.0, 7.3, 2.9, 'Normal', '2025-02-09 10:50:00'),

('Reservoir', 1170.0, 7.0, 3.0, 'Normal', '2025-02-10 08:05:00'),
('River', 990.0, 6.9, 4.0, 'Leak', '2025-02-10 10:20:00'),
('Groundwater', 1390.0, 7.4, 3.1, 'Normal', '2025-02-10 11:30:00');
