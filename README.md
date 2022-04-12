# pds03-backend
Materia: Prácticas de Desarrollo de Software

# Integrantes


|Alumno           | Github       |
|-----------------|--------------|
| -               | @a           |
|Matías Cabrera   | @maty11c     |
|Jose Luis Cassano| @cassa10     |

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

## Swagger Doc

Documentation of all available api endpoints and Model DTO's

- Visit endpoints "/" or "/swagger-ui.html"

