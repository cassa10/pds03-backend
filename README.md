# UNQUE - pds03-backend 
[![Java CI with Maven](https://github.com/cassa10/pds03-backend/actions/workflows/maven.yml/badge.svg)](https://github.com/cassa10/pds03-backend/actions/workflows/maven.yml) 
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/c747c920e8614c8dbe5f7e1a0e88acc6)](https://www.codacy.com/gh/cassa10/pds03-backend/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=cassa10/pds03-backend&amp;utm_campaign=Badge_Grade)
[![Codacy Badge](https://app.codacy.com/project/badge/Coverage/c747c920e8614c8dbe5f7e1a0e88acc6)](https://www.codacy.com/gh/cassa10/pds03-backend/dashboard?utm_source=github.com&utm_medium=referral&utm_content=cassa10/pds03-backend&utm_campaign=Badge_Coverage)
[![View in Swagger](http://jessemillar.github.io/view-in-swagger-button/button.svg)](https://pds03-backend.herokuapp.com/swagger-ui.html) 

## Frontend

[Repo Frontend](https://github.com/emiliano07/quotaAllocationSystem)

## Gestion de tareas

[Trello](https://trello.com/b/cgnbUuhB/unque)

## Integrantes


| Alumno            | Github          |
|-------------------|-----------------|
| Daniel Villegas   | @DaniVillegas14 |
| Matías Cabrera    | @maty11c        |
| Jose Luis Cassano | @cassa10        |


## Pre-Requirements

    - java 11
    - maven
    - postgresql
    - Docker (optional)
    - Bash (docker)

## Install and run API with Docker (Recommended)

1. Clone repository
2. CD to repository cloned
3. Execute command:
    >mvn clean install

4. CD back and give execution permission to runDocker.sh
    >chmod +x runDocker.sh

5. Execute runDocker.sh script

## High-level explanation of runDocker.sh

Requires fat jar of app (that was generated at 3rd step at previous section) for building image of app

Build docker image of app with Dockerfile

Copy /deploy/dev.env as .env in root repo folder

Execute docker compose and start containers (database and app) with configuration at docker-compose.yml

## Install and run API (without Docker)

1. Clone repository

2. CD to repository cloned

3. Config. Properties: set up all environments variables at ./deploy/local.env (change if needs credentials or host envs)

4. Execute command
    >mvn clean install

5. Create postgresql database with name 'pds03-backend' with no quotes

6. Execute command for start API
   >mvn spring-boot:run

## Swagger Doc & Health checks

Documentation of all available api endpoints and Model DTO's

- Visit endpoints "/" or "/swagger-ui.html"

- Visit "/actuator/health" for health checks

