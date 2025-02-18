ALTER USER 'replication_user'@'%' IDENTIFIED WITH 'mysql_native_password' BY 'Password@54321';
GRANT REPLICATION SLAVE ON *.* TO 'replication_user'@'%';
FLUSH PRIVILEGES;
SHOW MASTER STATUS;