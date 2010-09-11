#!/bin/bash

mvn install:install-file -Dfile=transactions-osgi-3.7.0-SNAPSHOT.jar -DgroupId=com.atomikos -DartifactId=transactions-osgi -Dversion=3.7.0-SNAPSHOT -Dpackaging=jar
