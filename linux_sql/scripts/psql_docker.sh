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

function create_container() {
  echo "Create"
  return 0
}

function start_container() {
  echo "Start"
  return 0
}

function stop_container() {
  echo "Stop"
  return 0
}

case $cmd in
  create)
    create_container
    exit $?
  ;;
  start)
    start_container
    exit $?
  ;;
  stop)
    stop_container
    exit $?
  ;;
  *)
    echo "Illegal command"
    echo "Commands: start | stop | create"
    exit 1
  ;;
esac

exit 0
