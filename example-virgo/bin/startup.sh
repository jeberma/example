#!/bin/bash

BASE_DIR=$(cd $(dirname "$0"); cd ..; pwd)

SERVER_HOME=$BASE_DIR/provision/server

cd $BASE_DIR/provision
mvn initialize
$SERVER_HOME/bin/startup.sh 
