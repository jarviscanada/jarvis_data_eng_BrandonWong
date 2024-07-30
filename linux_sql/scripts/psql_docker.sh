#!/bin/bash

# Capture CLI arguments
cmd=$1
db_username=$2
db_password=$3

# Track number of CLI arguments
total_args="$#"

# Make sure that user input the valid number of arguments
function verify_arguments() {
  local arg_num=$1
  if [ $total_args -ne $arg_num ]; then
    echo "Illegal number of parameters"
    exit 1
  fi
}

# Make sure that docker engine is runnning
function run_docker() {
  sudo systemctl status docker || sudo systemctl start docker
}

function check_container_status() {
  local container_name=$1
  docker container inspect $container_name
  return $? 
}

function create_container() {}

function start_container() {}

function stop_container() {}

exit 0
