#!/bin/bash

# Evaluates
if [ ! -f "./docker-compose.yml" ]
then
    echo "[ERROR] - File ./docker-compose.yml not found"
    echo "[INFO] - Execute script at same level folder where it belongs"
    exit 1
fi

# Eval if exist .jar
if [ ! -f "./target/pds03-backend-0.0.1-SNAPSHOT.jar" ]
then
    echo "[ERROR] - File ./target/pds03-backend-0.0.1-SNAPSHOT.jar not found"
    echo "[INFO] - Execute \"mvn clean install\" for generating .jar file"
fi

# Build and instance containers with docker-compose info
docker-compose up -d --build