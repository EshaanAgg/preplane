# Preplane

Preplane is a program that helps you to master internships and placements!

## Technical Details

This is a `Maven` project that is built on top of the `SpringBoot` framework. You can visit the [design documentaion](./DESIGN.md) to refer to the fine nauances implemented while developing this project. All the nested repositories too have separate READMEs so as to convey the guidelines of implementation and design.

### Tech Stack

- Java 20
- Thymeleaf for the frontend UI
- MySQL
- JDBC for database management and backend API development

## Setting up the Project

1. Install and setup [`Java 20`](https://www.oracle.com/in/java/technologies/downloads/). The [following guide](https://ubuntuhandbook.org/index.php/2022/03/install-jdk-18-ubuntu/) might be helpful in setting up the same.
2. Make sure you have a running MySQL server instance running. Update the connection URL and login credentials in the [application.properties file](./src/main/resources/application.properties).
3. Make sure that your running MySQL instance has the updated schema. For the same, you can run the [`schema.sql`](./src/main/resources/schema.sql) file against your MySQL connection. It would drop the exisiting database `preplane` (if it exists) and then setup all the tables.
4. You can run the application by clicking `F5` in `VSCode`. The server is exposed on `http://localhost:8080/` by default. To build the project, use the command `./mvnw spring-boot:run`.
5. You can use any client to query the same and test the controllers.
