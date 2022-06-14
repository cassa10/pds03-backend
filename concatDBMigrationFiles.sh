#!/bin/bash

# Script for concat db migration files

working_dir=./src/main/resources/db/scripts/migration
V0=$working_dir/V1.0.0__Initial_Build.sql
V1=$working_dir/V1.0.1__Add_Hours_And_Changes.sql
V2=$working_dir/V1.0.2__Update_Model_Studied_Degree_And_Hour.sql
V3=$working_dir/V1.0.3__ModelChanges.sql
V4=$working_dir/V1.0.4__AddNewModelTables.sql

cat $V0 $V1 $V2 $V3 $V4 >> resultMigrationScript.sql