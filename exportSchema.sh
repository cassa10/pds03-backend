#!/bin/bash

# Script for migrating database versions
# Need to be executed in root project folder ("./pds03-backend")

# Example:
# ./this_file.sh 1.0.0 localhost 5432 postgres public pds03-backend

# |-------------|
# | Parameters  |
# |-------------|

VERSION=$1
DB_HOST=$2
DB_PORT=$3
DB_USERNAME=$4
DB_SCHEMA=$5
DB_NAME=$6

#-----------------

FILE="./src/main/resources/db/scripts/migration/V${VERSION}__create_tables.sql"
pg_dump --file $FILE --host $DB_HOST --port $PORT --username $DB_USERNAME --no-password --verbose --format=p --schema-only --schema $DB_SCHEMA $DB_NAME

# Validate if file was created ok
if [ ! -f $FILE ]
then
    echo "[ERROR] - File could not be created :("
    echo "[INFO] - Check six parameters (VERSION, DB_HOST, DB_PORT, DB_USERNAME, DB_SCHEMA, DB_NAME) and execute script in root folder, please"
    exit 1
fi

echo "[INFO] - File: ${FILE} created"
echo "[INFO] - OK"