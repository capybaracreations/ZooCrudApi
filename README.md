# ZooCrudApi

| CI Build           | Coverage           | Deployment           |
|:-------------:|:-------------:|:-------------:|
[![Build Status](https://travis-ci.org/capybaracreations/ZooCrudApi.svg?branch=master)](https://travis-ci.org/capybaracreations/ZooCrudApi) | [![codecov](https://codecov.io/gh/capybaracreations/ZooCrudApi/branch/master/graph/badge.svg)](https://codecov.io/gh/capybaracreations/ZooCrudApi/branch/master) | [![Heroku](http://heroku-badge.herokuapp.com/?app=zoo-crud-api&style=flat&svg=1&root=health)](https://zoo-crud-api.herokuapp.com/) | 

## Information
This is an application representing Zoo.

The Entity Relationship Diagram is as follows:

![Entity Relationship Diagram](https://github.com/capybaracreations/ZooCrudApi/blob/master/documentation/ERD.png)

Main idea behind it, is that there is a Species representing entity such as "Lion".<br>
Each Enclosure can only host Animals of the same Species.<br>
Entities hold only "name" field for simplicity sake.

## Workflow
I decided to forfeit git flow to speed up development since I'm the only person developing this app.
1. On commit to master branch, Travis CI job get's triggered
2. Application is build and code coverage report gets generated
3. If build was successful, code coverage report is sent to Codecov
4. Then, built app is deployed to Heroku for live hosting
5. If there's no interaction with deployed application during 30 min, application is shutdown to preserve hosting costs
6. Any new interaction will awake server. Application may need some time to boot.

## Architecture
![Layered Architecture](https://github.com/capybaracreations/ZooCrudApi/blob/master/documentation/spring-web-app-architecture.png)

## Features
1. General
    1. Application is built using Java + Spring Boot
    2. Layered Architecture
    3. TODO write JavaDocs
2. Web layer
    1. Swagger UI documentation at host/swagger-ui.html
    2. DTO requests
3. Service layer
    1. Serves as a handelr for business logic
    2. Mapping between DTO and Entities via Mapstruct
4. Persistence Layer
    1. PostgreSQL for persistence
    2. HikariCP for JDBC connection pooling
    3. Liquibase for database schema versioning
    4. TODO Second-level cache, preferably EhCache 3
5. Test Layer
    1. Embedded Database for JPA testing
    2. Transactional tests
6. Presentation Layer
    1. Vaadin 8
    2. TODO needs better abstraction layer
    3. TODO needs better ui
