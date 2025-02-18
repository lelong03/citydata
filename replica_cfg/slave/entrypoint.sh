#!/bin/bash
set -e

echo "Waiting for mysql-master to be ready..."
until mysql -h mysql-master -uroot -p"$MYSQL_ROOT_PASSWORD" -e "SELECT 1" >/dev/null 2>&1; do
    echo "Waiting for master..."
    sleep 5
done

echo "Master is available."

echo "Starting mysqld on slave in the background..."
exec /entrypoint.sh mysqld "$@" &
MYSQ_PID=$!

echo "Waiting for slave mysqld to be ready..."
until mysql -uroot -p"$MYSQL_ROOT_PASSWORD" -e "SELECT 1" >/dev/null 2>&1; do
    echo "Waiting for slave mysqld..."
    sleep 5
done

echo "Slave mysqld is ready. Retrieving master binlog coordinates..."
MASTER_STATUS=$(mysql -h mysql-master -uroot -p"$MYSQL_ROOT_PASSWORD" -e "SHOW MASTER STATUS\G")
MASTER_LOG_FILE=$(echo "$MASTER_STATUS" | grep 'File:' | awk '{print $2}')
MASTER_LOG_POS=$(echo "$MASTER_STATUS" | grep 'Position:' | awk '{print $2}')

echo "Obtained master coordinates: Log File = $MASTER_LOG_FILE, Position = $MASTER_LOG_POS"

# Retrieve the file size of the current binlog from the master
FILE_SIZE=$(mysql -h mysql-master -uroot -p"$MYSQL_ROOT_PASSWORD" -e "SHOW BINARY LOGS\G" | grep -A 1 "Log_name: $MASTER_LOG_FILE" | grep "File_size:" | awk '{print $2}')

if [ -n "$FILE_SIZE" ] && [ "$MASTER_LOG_POS" -gt "$FILE_SIZE" ]; then
    echo "Warning: Master log position ($MASTER_LOG_POS) is greater than file size ($FILE_SIZE). Using position 4 instead."
    MASTER_LOG_POS=4
fi

echo "Configuring slave replication..."
mysql -uroot -p"$MYSQL_ROOT_PASSWORD" <<EOF
STOP SLAVE;
CHANGE MASTER TO
  MASTER_HOST='mysql-master',
  MASTER_USER='replication_user',
  MASTER_PASSWORD='Password@54321',
  MASTER_LOG_FILE='$MASTER_LOG_FILE',
  MASTER_LOG_POS=$MASTER_LOG_POS;
START SLAVE;
EOF

echo "Replication configuration completed."
wait $MYSQ_PID
