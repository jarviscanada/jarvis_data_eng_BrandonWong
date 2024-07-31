#!/bin/bash

# All user CLI arguments
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

total_args="$#"

# Main functionality of the host info script
# Verify valid number of user arguments
# ./verify_num_arg.sh $total_args 5

# Save all machine info i.e., CPU, Virtual Machine statitics and Hostname
lscpu=$(lscpu)
cpuinfo=$(cat "/proc/cpuinfo")
vmstat=$(vmstat --unit M)
hostname=$(hostname -f)

# Retrieve specific hardware information
cpu_number=$(echo "$lscpu" | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu" | egrep "^Architecture:" | awk '{print $2}' | xargs)
cpu_model=$(echo "$lscpu" | egrep "^Model\sname:" | tr -s ' ' | cut -d ' ' -f 3- | xargs)
cpu_mhz=$(echo "$cpuinfo" | egrep "^cpu\sMHz" | tail -n1 | awk '{print $4}' | xargs)
l2_cache=$(echo "$lscpu" | egrep "^L2\scache:" | tr -s ' ' | cut -d ' ' -f 3 | xargs)
timestamp=$(vmstat -t | awk '{print $18, $19}' | tail -n1 | xargs)
total_mem=$(echo "$vmstat" | tail -1 | awk '{print $4}' | xargs)

TAB="$(printf '\t')"
cat << EOF
cpu_number${TAB}${cpu_number}
cpu_arch${TAB}${cpu_architecture}
cpu_model${TAB}${cpu_model}
cpu_mhz ${TAB}${cpu_mhz}
l2_cache${TAB}${l2_cache}
timestamp${TAB}${timestamp}
total_mem${TAB}${total_mem}
EOF
