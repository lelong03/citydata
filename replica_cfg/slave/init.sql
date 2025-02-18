CHANGE MASTER TO
  MASTER_HOST='mysql-master',
  MASTER_USER='replication_user',
  MASTER_PASSWORD='Password@54321',
  MASTER_LOG_FILE='mysql-bin.000003',  -- Replace with actual log file
  MASTER_LOG_POS=920;                 -- Replace with actual log position

START SLAVE;

SHOW SLAVE STATUS;
