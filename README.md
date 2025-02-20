# CityData MVP

CityData MVP is a smart city data management system that demonstrates advanced architectural patterns, scalability, and performance techniques. The system manages data for three main models—Water Supply, Electricity, and Waste—using various input mechanisms and provides analytical dashboards via Chart.js. The application is containerized using Docker Compose, uses Flyway for database migrations, and is built with Spring Boot and MySQL.

## 0. System Design (simple version)
![CityData drawio (1)](https://github.com/user-attachments/assets/95e94bc8-33b1-4bb0-aa2b-93db01b1a99a)

### Technical stack
- Java 23
- Spring boot 3.4.2 (Spring Data JPA: For Hibernate ORM, Spring Web: For our web application / Rest API)
- Mysql 8 (Replica model, WRITE on master nodes, and READ on slave nodes)
- RabbitMQ
- Docker compose
- Maven
- Flyway: version control for database (for migration)
- Chart.js: metric visualization

## 1. Data Models

### **Water Supply**
- **source:** The water source (e.g., *River, Reservoir, Groundwater*).
- **consumption:** Water consumption in liters per household per day.
- **ph:** pH level indicating water quality.
- **turbidity:** Turbidity in NTU indicating water clarity.
- **status:** Infrastructure status (e.g., *Normal, Leak, Maintenance*).
- **ts:** Timestamp when the reading was recorded.

### **Electricity**
- **source:** Energy source (e.g., *Solar, Wind, Hydro, Coal*).
- **district:** District or area (e.g., *Manhattan, Brooklyn, Queens, Bronx, Staten Island*).
- **consumption:** Electricity consumption in kWh.
- **peak:** Peak load hours (e.g., *17:00-19:00*).
- **status:** Grid status (e.g., *Active, Overloaded, Maintenance*).
- **outage_ts:** Outage start timestamp (nullable).
- **outage_dur:** Outage duration in minutes (nullable).
- **outage_area:** Affected areas during an outage (nullable).
- **ts:** Timestamp when the reading was recorded.

### **Waste**
- **freq:** Collection frequency (e.g., *Daily, Weekly*).
- **segregation:** Waste segregation levels (e.g., *Organic, Plastic, Metal, Electronic*).
- **facility:** Facility status (e.g., *Normal, AtCapacity, Offline*).
- **total:** Total waste collected (in tons).
- **recycle:** Recycling rate (percentage).
- **report_date:** Date of the report.

## 2. Data Input Mechanisms

- **Electricity (Integration):**  
  Data is generated by automated systems (e.g., SCADA or smart meters) that provide near real-time readings.

- **Water Supply (Manual Input):**  
  Data is entered manually via user forms—ideal for cases where reports are aggregated by municipal departments.

- **Waste (File Upload):**  
  Data is imported through file uploads (e.g., CSV files) to capture field inspections or sensor data.

## 3. Database Design

```sql
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
) ENGINE=InnoDB
PARTITION BY RANGE (YEAR(ts)*10 + QUARTER(ts)) (
    PARTITION p2025q1 VALUES LESS THAN (20252), -- Covers Q1 2025: values 20251 and below (i.e., January–March)
    PARTITION p2025q2 VALUES LESS THAN (20253), -- Covers Q2 2025: values from 20252 up to 20252
    PARTITION p2025q3 VALUES LESS THAN (20254), -- Covers Q3 2025: values from 20253 up to 20253
    PARTITION p2025q4 VALUES LESS THAN (20255), -- Covers Q4 2025: values from 20254 up to 20254
    PARTITION pMax VALUES LESS THAN MAXVALUE   -- Catch-all partition for any data beyond Q4 2025
);
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

```
## 4. Database Consideration

We chose **MySQL** for this MVP because:

- **Simplicity & Speed to MVP**: is essential given the 4–6 hour time frame.
- **Sufficient for Data Models**: data for water supply, electricity, and waste management are mostly structured with a clear schema.
  When properly indexed, we can handle time-range queries and aggregations well enough.


**[Improvement]** Consider a Time-Series Database when:
- **High Ingestion Rates**
- **Realtime Queries**
- **Long-Term Scalability**


## 5. Data Analysis and Visualization

### Water Supply Metrics

**1.	Average Consumption**: The average number of liters consumed per household per day.
Helps in understanding resource usage and planning capacity.

**2.	Water Quality Metrics**: Key measurements such as pH and turbidity, which indicate the quality and safety of the water.
Directly impacts public health and helps in monitoring the effectiveness of water treatment processes.

**3.	Water Efficiency & Loss Rate**: The percentage of water lost due to leakages or infrastructure inefficiencies.
Provides insight into the operational efficiency and can indicate when maintenance is needed.

<img width="1570" alt="Screenshot 2025-02-10 at 2 37 14 AM" src="https://github.com/user-attachments/assets/43571a30-971e-42ab-beec-92c4269aee9f" />
<img width="1582" alt="Screenshot 2025-02-10 at 2 37 25 AM" src="https://github.com/user-attachments/assets/389872a3-8652-4f33-8bab-b18598c12b95" />


### Electricity Metrics

**1.	Average Electricity Consumption**: The average electricity usage in kilowatt-hours (kWh), often segmented by district or area.
Essential for load planning, grid management, and cost control.

**2.	Peak Load Analysis**: Identification of the peak load hours and the magnitude of demand during those times.
Helps to identify stress periods on the grid and plan for capacity improvements or demand response measures.

**3.	Outage Metrics**: Frequency, duration, and affected areas of power outages.
Indicates reliability and helps in prioritizing infrastructure upgrades.

<img width="788" alt="Screenshot 2025-02-10 at 2 37 32 AM" src="https://github.com/user-attachments/assets/52da74f6-dffd-44e3-b60b-73587bd5af18" />


### Waste Metric

**1.	Total Waste Collected**: The total weight (in tons) of waste collected over a specific period (day, week, etc.).
Indicates the volume of waste that needs processing and informs logistical planning.

**2.	Recycling Rate**: The percentage of total waste that is recycled.
A key indicator of environmental performance and sustainability.

**3.	Waste Collection Frequency**: How often waste is collected (e.g., daily or weekly).
Reflects operational efficiency and can affect service quality and cost.

<img width="413" alt="Screenshot 2025-02-10 at 11 30 36 PM" src="https://github.com/user-attachments/assets/64e1f2ac-1501-41af-9588-20d1020e24d5" />
<img width="415" alt="Screenshot 2025-02-10 at 11 30 42 PM" src="https://github.com/user-attachments/assets/65f8b70e-f466-4f3d-bb98-aea6430e520c" />


## 6. Design Patterns

The MVP demonstrates several design patterns:
- **Repository Pattern**: Using Spring Data JPA repositories to abstract data access.
- **Service (Facade) Pattern**: Service classes encapsulate business logic.
- **DTO Pattern**: Data Transfer Objects (DTOs) decouple the domain models from the API layer.
- **Dependency Injection (DI)**: Managed by Spring Boot for loose coupling and testability.
- **Dynamic Data Source Routing**: Implemented via Spring’s AbstractRoutingDataSource and an AOP aspect to route read and write operations to different databases.
- **Template Method Pattern**: In the scheduler layer, common integration logic is encapsulated in an abstract base class for reuse and extensibility.

## 7. Techniques for Performance

To handle heavy data loads, the following techniques are applied:
- **Decoupling Ingestion from Processing**: RabbitMQ is used to decouple data ingestion from processing.
- **Bulk/Batched Writes**: Data is inserted in batches to reduce overhead.
- **Read/Write Splitting**: Dynamic data source routing or MySQL Router directs writes to the master and reads to replicas.
- **Optimized Queries and Indexing**: SQL queries are carefully designed with appropriate indexes.
- **Table partitioning**: improve write and query performance when the data volume is high

## 8. Further Improvements

For a scalable, production-grade system, further enhancements could include:
- **Time-Series Database**: If the real-time data streams (especially from the electricity integration) were to produce extremely high-frequency data,
a time-series database like InfluxDB or TimescaleDB would be more efficient.
- **Caching**:
Integrating Redis to cache frequently accessed queries.
- **API Gateway and Microservices**:
Splitting the application into microservices with an API gateway.
- **Enhanced Security & Monitoring**:
Implementing OAuth2/JWT for API security and monitoring tools like Prometheus, Grafana, or ELK for observability.
- **Container Orchestration**:
Deploying on Kubernetes or Docker Swarm for automated scaling and high availability.

## 9. Running the Application

**1. Start the docker compose**:
```bash
docker-compose up
```
**2. Flyway Migrations**:
Flyway automatically runs on startup to manage the database schema.

**3. Access the Application:**
- The CityData application will be available at http://localhost:8080
- RabbitMQ management UI at http://localhost:15672
- **Waste**
  - API Upload : [POST] http://localhost:8080/api/waste/upload
  - CSV sample content:
    ```
    freq,segregation,facility,total,recycle,report_date
    Daily,Organic,Normal,15.0,65.0,2025-02-09
    Daily,Plastic,Normal,1.0,100,2025-02-09
    ``` 
    ![Screenshot 2025-02-10 at 8 44 24 AM](https://github.com/user-attachments/assets/1d6e0ae3-a953-484f-91ae-0e482ef9328f)

- **Water Supply**
  - API for Input manually: [POST] http://localhost:8080/api/water
  
  - Sample request
    ```json
    {
        "source": "River",
        "consumption": 980.0,
        "ph": 6.9,
        "turbidity": 3.8,
        "status": "Normal",
        "ts": "2025-02-01T09:15:00.000+00:00"
    }
    ```
- **Electricity**: for this MVP (4-6 hrs working)
  - Integration type: pulling data from other source via API 
  - Integration end-point (simulate): http://localhost:8080/api/simulate/electricity/batch
  - Interval: every 10 seconds
    
**4. Visualization:**
  - Open: **frontend/index.html**
