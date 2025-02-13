DROP TABLE IF EXISTS water_supply;
CREATE TABLE water_supply (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              source VARCHAR(20) NOT NULL,
                              consumption DOUBLE NOT NULL,
                              ph DOUBLE NOT NULL,
                              turbidity DOUBLE NOT NULL,
                              status VARCHAR(20) NOT NULL,
                              ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS electricity;
CREATE TABLE electricity (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             source VARCHAR(20) NOT NULL,
                             district VARCHAR(50) NOT NULL,
                             consumption DOUBLE NOT NULL,
                             peak VARCHAR(20) NOT NULL,
                             status VARCHAR(20) NOT NULL,
                             outage_ts TIMESTAMP NULL,
                             outage_dur INT NULL,
                             outage_area VARCHAR(100) NULL,
                             ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS waste;
CREATE TABLE waste (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       freq VARCHAR(20) NOT NULL,
                       segregation VARCHAR(50) NOT NULL,
                       facility VARCHAR(20) NOT NULL,
                       total DOUBLE NOT NULL,
                       recycle DOUBLE NOT NULL,
                       report_date DATE NOT NULL
);
