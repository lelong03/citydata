-- V2__init_sample_electricity_data.sql
-- Insert sample data for electricity table for 10 days
-- We assume the sample period is from 2025-02-01 to 2025-02-10.
-- For each day, we insert three rows with different energy sources and districts.

INSERT INTO electricity (source, district, consumption, peak, status, outage_ts, outage_dur, outage_area, ts) VALUES
-- Day 1: 2025-02-01
('Solar', 'Manhattan', 3200.0, '17:00-19:00', 'Active', NULL, NULL, NULL, '2025-02-01 08:00:00'),
('Wind', 'Brooklyn', 2500.0, '18:00-20:00', 'Active', '2025-02-01 12:00:00', 30, 'Brooklyn', '2025-02-01 08:30:00'),
('Coal', 'Queens', 4000.0, '16:00-18:00', 'Overloaded', NULL, NULL, NULL, '2025-02-01 09:00:00'),

-- Day 2: 2025-02-02
('Solar', 'Manhattan', 3100.0, '17:00-19:00', 'Active', NULL, NULL, NULL, '2025-02-02 08:00:00'),
('Wind', 'Brooklyn', 2600.0, '18:00-20:00', 'Active', '2025-02-02 12:00:00', 20, 'Brooklyn', '2025-02-02 08:30:00'),
('Coal', 'Queens', 4200.0, '16:00-18:00', 'Overloaded', '2025-02-02 11:00:00', 15, 'Queens', '2025-02-02 09:00:00'),

-- Day 3: 2025-02-03
('Solar', 'Manhattan', 3300.0, '17:00-19:00', 'Active', NULL, NULL, NULL, '2025-02-03 08:00:00'),
('Wind', 'Brooklyn', 2400.0, '18:00-20:00', 'Active', '2025-02-03 12:00:00', 25, 'Brooklyn', '2025-02-03 08:30:00'),
('Coal', 'Queens', 4100.0, '16:00-18:00', 'Maintenance', NULL, NULL, NULL, '2025-02-03 09:00:00'),

-- Day 4: 2025-02-04
('Solar', 'Manhattan', 3150.0, '17:00-19:00', 'Active', NULL, NULL, NULL, '2025-02-04 08:00:00'),
('Wind', 'Brooklyn', 2550.0, '18:00-20:00', 'Active', '2025-02-04 12:00:00', 30, 'Brooklyn', '2025-02-04 08:30:00'),
('Coal', 'Queens', 4050.0, '16:00-18:00', 'Overloaded', NULL, NULL, NULL, '2025-02-04 09:00:00'),

-- Day 5: 2025-02-05
('Solar', 'Manhattan', 3250.0, '17:00-19:00', 'Active', NULL, NULL, NULL, '2025-02-05 08:00:00'),
('Wind', 'Brooklyn', 2450.0, '18:00-20:00', 'Active', '2025-02-05 12:00:00', 20, 'Brooklyn', '2025-02-05 08:30:00'),
('Coal', 'Queens', 4150.0, '16:00-18:00', 'Maintenance', '2025-02-05 11:30:00', 10, 'Queens', '2025-02-05 09:00:00'),

-- Day 6: 2025-02-06
('Solar', 'Manhattan', 3350.0, '17:00-19:00', 'Active', NULL, NULL, NULL, '2025-02-06 08:00:00'),
('Wind', 'Brooklyn', 2600.0, '18:00-20:00', 'Overloaded', '2025-02-06 12:00:00', 35, 'Brooklyn', '2025-02-06 08:30:00'),
('Coal', 'Queens', 4300.0, '16:00-18:00', 'Overloaded', NULL, NULL, NULL, '2025-02-06 09:00:00'),

-- Day 7: 2025-02-07
('Solar', 'Manhattan', 3200.0, '17:00-19:00', 'Active', NULL, NULL, NULL, '2025-02-07 08:00:00'),
('Wind', 'Brooklyn', 2500.0, '18:00-20:00', 'Active', '2025-02-07 12:00:00', 30, 'Brooklyn', '2025-02-07 08:30:00'),
('Coal', 'Queens', 4200.0, '16:00-18:00', 'Maintenance', NULL, NULL, NULL, '2025-02-07 09:00:00'),

-- Day 8: 2025-02-08
('Solar', 'Manhattan', 3300.0, '17:00-19:00', 'Active', NULL, NULL, NULL, '2025-02-08 08:00:00'),
('Wind', 'Brooklyn', 2450.0, '18:00-20:00', 'Active', '2025-02-08 12:00:00', 25, 'Brooklyn', '2025-02-08 08:30:00'),
('Coal', 'Queens', 4150.0, '16:00-18:00', 'Overloaded', '2025-02-08 11:00:00', 20, 'Queens', '2025-02-08 09:00:00'),

-- Day 9: 2025-02-09
('Solar', 'Manhattan', 3100.0, '17:00-19:00', 'Active', NULL, NULL, NULL, '2025-02-09 08:00:00'),
('Wind', 'Brooklyn', 2550.0, '18:00-20:00', 'Active', '2025-02-09 12:00:00', 30, 'Brooklyn', '2025-02-09 08:30:00'),
('Coal', 'Queens', 4250.0, '16:00-18:00', 'Maintenance', NULL, NULL, NULL, '2025-02-09 09:00:00'),

-- Day 10: 2025-02-10
('Solar', 'Manhattan', 3400.0, '17:00-19:00', 'Active', NULL, NULL, NULL, '2025-02-10 08:00:00'),
('Wind', 'Brooklyn', 2650.0, '18:00-20:00', 'Overloaded', '2025-02-10 12:00:00', 40, 'Brooklyn', '2025-02-10 08:30:00'),
('Coal', 'Queens', 4300.0, '16:00-18:00', 'Overloaded', '2025-02-10 11:00:00', 20, 'Queens', '2025-02-10 09:00:00');
