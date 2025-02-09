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
