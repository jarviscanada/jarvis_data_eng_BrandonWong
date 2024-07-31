#!/bin/bash

# All user CLI arguments
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

total_args="$#"

# Main functionality of the host info script
./verify_num_arg.sh $total_args 5
