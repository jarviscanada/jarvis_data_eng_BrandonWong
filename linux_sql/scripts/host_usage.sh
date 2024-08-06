#!/bin/bash

# All user CLI arguments
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

total_args="$#"

# Main functionality of the host usage script
# Verify valid number of user arguments
# ./verify_num_arg.sh $total_args 5

# Save all machine info, i.e., CPU, Virtual Machine statitics and Hostname
vmstat=$(vmstat --unit M)
hostname=$(hostname -f)
meminfo=$(cat "/proc/meminfo")

# Retrieve specific hardware information
timestamp=$(vmstat -t | awk '{print $18, $19}' | tail -n1 | xargs)
memory_free=$(echo "$meminfo" | egrep "^MemFree:" | awk '{print $2}' | xargs)
cpu_idle=$(echo "$vmstat" | tail -1 | awk '{print $15}' | xargs)
cpu_kernel=$(echo "$vmstat" | tail -1 | awk '{print $14}' | xargs)
# Reference on IO write/read: https://litux.nl/mirror/linuxperformanceguide/0131486829/ch06lev1sec2.html
disk_write=$(echo "$vmstat" | tail -1 | awk '{print $9}' | xargs)
disk_read=$(echo "$vmstat" | tail -1 | awk '{print $10}' | xargs)
disk_io=$(expr ${disk_write} + ${disk_read})
disk_available=$(df -BM / | tail -1 | awk '{print $4}' | sed 's/[^0-9]*//g')

# SQL select subquery to fetch the hostname's ID
select_stmt="(SELECT id FROM host_info WHERE hostname='$hostname')"

# SQL insert query to insert the host usage info
insert_stmt=$(cat << EOF
INSERT INTO host_usage (
  "timestamp",
  host_id,
  memory_free,
  cpu_idle,
  cpu_kernel,
  disk_io,
  disk_available
) VALUES (
  '$timestamp',
  $select_stmt,
  $memory_free,
  $cpu_idle,
  $cpu_kernel,
  $disk_io,
  $disk_available
);
EOF
)

export PGPASSWORD=$psql_password

# Update the host_usage table with new host usage info
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"

exit $?
