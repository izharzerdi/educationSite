# Education Site

    * API for enrolling students to courses.

# Notes

    * This project is built using 2.1.9.RELEASE of Spring Boot, Java 11 - see pom.xml.
    * The POM builds a JAR file.
    * Use `mvc spring-boot:run` from your project directory to run it, then goto `http://localhost:8085/courses`.

# Dev Setup

## Version 
    * Java 11
    * Mysql 5.7
    * Maven 3.8.5

## Create the new database
```mysql> create database its_education_db;```

## Create the user
```mysql> create user 'testuser'@'localhost' identified by 'testuser';```

## Grant all the privileges to the new user on the newly created database
```mysql> grant all on its_education_db.* to 'testuser'@'testuser';```

## Create local config
- Create `application-local.yml` inside config folder.
- Replace `flyway.url` in `application-local.yml` with local db url.
- Replace `flyway.user` and `flyway.password` with local db credentials.

## Flyway commands (for db migration)

```mvn -Dflyway.configFile=config/application-local.yml flyway:info```
- It will give the status of scripts in the application (migrations/sql/*.sql files)

        RESPONSE :
        +---------+-------------+---------------------+-----+----------------------+---------+
        | Version | Description                             | Installed on         | State   |
        +---------+-------------+---------------------+---------+----------------------+-----+
        | 1.1.1   | create tables course and student        |  2023-09-08 16:15:23 | Success |
        | 1.1     | seed data for course table              |  2023-09-08 16:15:23 | Success |
        +---------+-------------+---------------------+---------+---------------------+-------+


```mvn -Dflyway.configFile=config/application-local.yml flyway:migrate```
- It will execute the migration scripts in the sql files.
- Use ```-Dflyway.target=version_number``` to migrate to specific version.

For deleting all migrations (WARNING: will delete all tables created by migration)
```mvn -Dflyway.configFile=config/application-local.yml flyway:clean```
- It will delete all the tables and data in the database.

# Compile and run (with default config)
```mvn clean package && java -jar target/com-its-education-0.0.1.jar``` or
```mvc spring-boot:run``` from project directory
NB: `config` folder should be present outside the jar file.

# Run with custom config
```java -jar -Dspring.profiles.active=prod com-its-education-0.0.1.jar```
The above command takes application-prod.yml from config folder outside the jar file (externalized config)