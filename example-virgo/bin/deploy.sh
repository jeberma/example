#!/bin/bash

BASE_DIR=$(cd $(dirname "$0"); cd ..; pwd)

SERVER_HOME=$BASE_DIR/provision/server

cp $1 $SERVER_HOME/pickup
