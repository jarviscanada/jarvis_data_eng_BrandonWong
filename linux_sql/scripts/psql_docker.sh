#!/bin/bash

# Capture CLI arguments
cmd=$1
db_username=$2
db_password=$3

# Track number of CLI arguments
total_args="$#"
container_name='jrvs-psql'
volume_name='pgdata'

# Make sure that user input the valid number of arguments
function verify_arguments() {
  local arg_num=$1
  if [ $total_args -ne $arg_num ]; then
    echo 'Illegal number of parameters'
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
  check_container_status $container_name
  local container_status=$?

  # Check if the container is already created
  if [ $container_status -eq 0 ]; then
    echo 'Container already exists'
    return 1
  fi
  
  # Check number of CLI arguments
  verify_arguments 3

  # Check if database username exists
  if [ -z $db_username ]; then 
    echo 'Database Username not provided'
    echo 'Command: psql_docker.sh create <db_username> <db_password>'
    return 1
  fi

  # Check if database password exists
  if [ -z $db_password ]; then 
    echo 'Database Password not provided'
    echo 'Command: psql_docker.sh create <db_username> <db_password>'
    return 1
  fi
  
  # Create volume (NOTE: Ensure volume has not existed before)
  docker volume create $volume_name
  # Create container
  docker run --name $container_name -e POSTGRES_USER=$db_username -e POSTGRES_PASSWORD=$db_password -d -v $volume_name:/var/lib/postgresql/data -p 5432:5432 postgres:9.6-alpine

  return $?
}

function start_container() {
  echo "Start"
  return $?
}

function stop_container() {
  echo "Stop"
  return $?
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
