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

