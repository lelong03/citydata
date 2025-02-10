INSERT INTO water_supply (source, consumption, ph, turbidity, status, ts) VALUES
('Reservoir', 1150.0, 7.1, 3.2, 'Normal', NOW()),
('River', 980.0, 6.9, 3.8, 'Normal', DATE_SUB(NOW(), INTERVAL 2 HOUR)),
('Groundwater', 1300.0, 7.3, 2.9, 'Normal', DATE_SUB(NOW(), INTERVAL 1 DAY)),

('Reservoir', 1200.0, 7.0, 3.1, 'Normal', DATE_SUB(NOW(), INTERVAL 3 HOUR)),
('River', 850.0, 6.8, 4.0, 'Leak', DATE_SUB(NOW(), INTERVAL 1 DAY)),
('Groundwater', 1400.0, 7.4, 3.0, 'Maintenance', DATE_SUB(NOW(), INTERVAL 2 DAY)),

('Reservoir', 950.0, 7.2, 3.3, 'Normal', DATE_SUB(NOW(), INTERVAL 4 HOUR)),
('River', 1020.0, 6.7, 3.9, 'Normal', DATE_SUB(NOW(), INTERVAL 1 DAY)),
('Groundwater', 1250.0, 7.1, 2.8, 'Normal', DATE_SUB(NOW(), INTERVAL 5 HOUR)),

('Reservoir', 1100.0, 7.0, 3.0, 'Normal', DATE_SUB(NOW(), INTERVAL 1 DAY)),
('River', 920.0, 6.9, 4.2, 'Normal', DATE_SUB(NOW(), INTERVAL 2 HOUR)),
('Groundwater', 1350.0, 7.3, 3.1, 'Normal', DATE_SUB(NOW(), INTERVAL 1 DAY)),

('Reservoir', 1180.0, 7.1, 3.4, 'Normal', DATE_SUB(NOW(), INTERVAL 3 DAY)),
('River', 1050.0, 6.8, 3.7, 'Leak', DATE_SUB(NOW(), INTERVAL 2 HOUR)),
('Groundwater', 1420.0, 7.5, 3.0, 'Normal', DATE_SUB(NOW(), INTERVAL 4 DAY)),

('Reservoir', 1250.0, 7.2, 3.3, 'Normal', DATE_SUB(NOW(), INTERVAL 6 HOUR)),
('River', 990.0, 6.8, 4.1, 'Normal', DATE_SUB(NOW(), INTERVAL 1 DAY)),
('Groundwater', 1380.0, 7.4, 3.2, 'Maintenance', DATE_SUB(NOW(), INTERVAL 3 DAY)),

('Reservoir', 1120.0, 7.0, 3.1, 'Normal', DATE_SUB(NOW(), INTERVAL 5 HOUR)),
('River', 970.0, 6.9, 4.0, 'Normal', DATE_SUB(NOW(), INTERVAL 1 DAY)),
('Groundwater', 1300.0, 7.3, 2.9, 'Normal', DATE_SUB(NOW(), INTERVAL 2 DAY)),

('Reservoir', 1190.0, 7.1, 3.2, 'Normal', DATE_SUB(NOW(), INTERVAL 7 HOUR)),
('River', 1010.0, 6.8, 3.8, 'Normal', DATE_SUB(NOW(), INTERVAL 2 DAY)),
('Groundwater', 1370.0, 7.4, 3.0, 'Normal', DATE_SUB(NOW(), INTERVAL 4 DAY)),

('Reservoir', 1150.0, 7.2, 3.3, 'Normal', DATE_SUB(NOW(), INTERVAL 3 HOUR)),
('River', 950.0, 6.8, 4.1, 'Normal', DATE_SUB(NOW(), INTERVAL 1 HOUR)),
('Groundwater', 1320.0, 7.3, 2.9, 'Normal', DATE_SUB(NOW(), INTERVAL 2 DAY)),

('Reservoir', 1170.0, 7.0, 3.0, 'Normal', DATE_SUB(NOW(), INTERVAL 5 DAY)),
('River', 990.0, 6.9, 4.0, 'Leak', DATE_SUB(NOW(), INTERVAL 1 HOUR)),
('Groundwater', 1390.0, 7.4, 3.1, 'Normal', DATE_SUB(NOW(), INTERVAL 2 DAY));


INSERT INTO electricity (source, district, consumption, peak, status, outage_ts, outage_dur, outage_area, ts) VALUES
('Solar', 'Manhattan', 3200.0, '17:00-19:00', 'Active', NULL, NULL, NULL, NOW()),
('Wind', 'Brooklyn', 2500.0, '18:00-20:00', 'Active', DATE_SUB(NOW(), INTERVAL 22 DAY), 30, 'Brooklyn', DATE_SUB(NOW(), INTERVAL 20 DAY)),
('Coal', 'Queens', 4000.0, '16:00-18:00', 'Overloaded', NULL, NULL, NULL, DATE_SUB(NOW(), INTERVAL 37 HOUR)),

('Solar', 'Manhattan', 3100.0, '17:00-19:00', 'Active', NULL, NULL, NULL, DATE_SUB(NOW(), INTERVAL 29 DAY)),
('Wind', 'Brooklyn', 2600.0, '18:00-20:00', 'Active', DATE_SUB(NOW(), INTERVAL 43 HOUR), 20, 'Brooklyn', DATE_SUB(NOW(), INTERVAL 19 HOUR)),
('Coal', 'Queens', 4200.0, '16:00-18:00', 'Overloaded', DATE_SUB(NOW(), INTERVAL 30 DAY), 15, 'Queens', DATE_SUB(NOW(), INTERVAL 4 DAY)),

('Solar', 'Manhattan', 3300.0, '17:00-19:00', 'Active', NULL, NULL, NULL, DATE_SUB(NOW(), INTERVAL 9 HOUR)),
('Wind', 'Brooklyn', 2400.0, '18:00-20:00', 'Active', DATE_SUB(NOW(), INTERVAL 16 DAY), 25, 'Brooklyn', DATE_SUB(NOW(), INTERVAL 20 DAY)),
('Coal', 'Queens', 4100.0, '16:00-18:00', 'Maintenance', NULL, NULL, NULL, DATE_SUB(NOW(), INTERVAL 38 HOUR)),

('Solar', 'Manhattan', 3150.0, '17:00-19:00', 'Active', NULL, NULL, NULL, DATE_SUB(NOW(), INTERVAL 12 DAY)),
('Wind', 'Brooklyn', 2550.0, '18:00-20:00', 'Active', DATE_SUB(NOW(), INTERVAL 13 HOUR), 30, 'Brooklyn', DATE_SUB(NOW(), INTERVAL 44 HOUR)),
('Coal', 'Queens', 4050.0, '16:00-18:00', 'Overloaded', NULL, NULL, NULL, DATE_SUB(NOW(), INTERVAL 10 DAY)),

('Solar', 'Manhattan', 3250.0, '17:00-19:00', 'Active', NULL, NULL, NULL, DATE_SUB(NOW(), INTERVAL 34 HOUR)),
('Wind', 'Brooklyn', 2450.0, '18:00-20:00', 'Active', DATE_SUB(NOW(), INTERVAL 10 DAY), 20, 'Brooklyn', DATE_SUB(NOW(), INTERVAL 3 HOUR)),
('Coal', 'Queens', 4150.0, '16:00-18:00', 'Maintenance', DATE_SUB(NOW(), INTERVAL 35 HOUR), 10, 'Queens', DATE_SUB(NOW(), INTERVAL 7 DAY)),

('Solar', 'Manhattan', 3350.0, '17:00-19:00', 'Active', NULL, NULL, NULL, DATE_SUB(NOW(), INTERVAL 1 DAY)),
('Wind', 'Brooklyn', 2600.0, '18:00-20:00', 'Overloaded', DATE_SUB(NOW(), INTERVAL 28 DAY), 35, 'Brooklyn', DATE_SUB(NOW(), INTERVAL 47 HOUR)),
('Coal', 'Queens', 4300.0, '16:00-18:00', 'Overloaded', NULL, NULL, NULL, DATE_SUB(NOW(), INTERVAL 25 DAY)),

('Solar', 'Manhattan', 3200.0, '17:00-19:00', 'Active', NULL, NULL, NULL, DATE_SUB(NOW(), INTERVAL 4 HOUR)),
('Wind', 'Brooklyn', 2500.0, '18:00-20:00', 'Active', DATE_SUB(NOW(), INTERVAL 4 HOUR), 30, 'Brooklyn', DATE_SUB(NOW(), INTERVAL 19 DAY)),
('Coal', 'Queens', 4200.0, '16:00-18:00', 'Maintenance', NULL, NULL, NULL, DATE_SUB(NOW(), INTERVAL 9 HOUR));



INSERT INTO waste (freq, segregation, facility, total, recycle, report_date) VALUES
('Daily', 'Organic', 'Normal', 12.5, 60.0, NOW()),
('Daily', 'Plastic', 'AtCapacity', 15.0, 55.0, DATE_SUB(NOW(), INTERVAL 25 DAY)),
('Daily', 'Metal', 'Offline', 10.8, 65.0, DATE_SUB(NOW(), INTERVAL 32 HOUR)),
('Daily', 'Electronic', 'Normal', 14.2, 58.0, DATE_SUB(NOW(), INTERVAL 25 DAY)),
('Daily', 'Organic', 'AtCapacity', 13.1, 62.0, DATE_SUB(NOW(), INTERVAL 45 HOUR)),
('Daily', 'Plastic', 'Normal', 16.5, 57.0, DATE_SUB(NOW(), INTERVAL 24 DAY)),
('Daily', 'Metal', 'Offline', 11.0, 64.0, DATE_SUB(NOW(), INTERVAL 35 HOUR)),
('Daily', 'Electronic', 'Normal', 15.3, 59.0, DATE_SUB(NOW(), INTERVAL 11 DAY)),
('Daily', 'Organic', 'Normal', 12.9, 61.0, DATE_SUB(NOW(), INTERVAL 29 HOUR)),
('Daily', 'Plastic', 'AtCapacity', 14.0, 55.0, DATE_SUB(NOW(), INTERVAL 13 DAY)),
('Daily', 'Metal', 'Normal', 13.7, 63.0, DATE_SUB(NOW(), INTERVAL 13 HOUR)),
('Daily', 'Electronic', 'Offline', 15.8, 57.5, DATE_SUB(NOW(), INTERVAL 29 DAY)),
('Daily', 'Organic', 'AtCapacity', 12.2, 60.5, DATE_SUB(NOW(), INTERVAL 2 HOUR)),
('Daily', 'Plastic', 'Normal', 16.0, 56.0, DATE_SUB(NOW(), INTERVAL 15 DAY)),
('Daily', 'Metal', 'Offline', 11.5, 64.0, DATE_SUB(NOW(), INTERVAL 14 HOUR)),
('Daily', 'Electronic', 'Normal', 14.4, 58.0, DATE_SUB(NOW(), INTERVAL 13 DAY)),
('Daily', 'Organic', 'Normal', 13.3, 61.0, DATE_SUB(NOW(), INTERVAL 47 HOUR)),
('Daily', 'Plastic', 'AtCapacity', 15.2, 55.5, DATE_SUB(NOW(), INTERVAL 25 DAY)),
('Daily', 'Metal', 'Normal', 12.8, 62.0, DATE_SUB(NOW(), INTERVAL 47 HOUR)),
('Daily', 'Electronic', 'Offline', 14.9, 57.0, DATE_SUB(NOW(), INTERVAL 10 DAY)),
('Daily', 'Organic', 'AtCapacity', 13.0, 60.0, DATE_SUB(NOW(), INTERVAL 21 HOUR));



