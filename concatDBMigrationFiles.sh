#!/bin/bash

# Script for concat db migration files

working_dir=./src/main/resources/db/scripts/migration

FILES=$(ls -lv -1 $working_dir)

RESULT_FILE=./resultMigrationScript.sql

# clear result file if it already exists
> $RESULT_FILE

for file in $FILES; do
  echo "[DEBUG] appending $file"
  cat "$working_dir/$file" >> $RESULT_FILE
done

echo "[INFO] File $RESULT_FILE created/updated :D"