#!/bin/bash

# User CLI arguments
user_num_args=$1
req_num_args=$2

# Make sure that user input the valid number of arguments
function verify_arguments() {
  local user=$1
  local req=$2
  if [ $user -ne $req ]; then
    echo 'Illegal number of parameters'
    exit 1
  fi
}

# Main functionality of the host info script
verify_arguments $user_num_args $req_num_args
