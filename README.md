# pds03-backend
Practicas de Desarrollo de Software


## Pre-Requirements
java 11
maven
postgresql
Docker (optional)
Bash (docker)
Install and run API with Docker (Recommended)
Clone repository
CD to repository cloned
CD to backend
Execute command:
mvn clean install

CD back and give execution permission to runDocker.sh
chmod +x runDocker.sh

Execute runDocker.sh script
High-level explanation of runDocker.sh
Requires fat jar of app (Generated at 4th step in last section) for building image of app

Build docker image of app with Dockerfile

Execute docker compose and start containers (database and app) with configuration at docker-compose.yml

Install and run API (without Docker)
Clone repository

CD to repository cloned

Config. Properties (2 options)

A. Set up all enviroments variables at ./deploy/dev.env (Recommended)

B. Replace all properties of application.properties with values of ./deploy/dev.env

Execute command "mvn clean install"

Create database with name "pds03-backend"

Execute command "mvn spring-boot:run" for start API`

