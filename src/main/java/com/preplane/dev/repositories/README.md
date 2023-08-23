# Repositories

In this folder we provide the implementation of various repositoires, that is we actually interact with the database layer with the help of the `JDBC Connector`. For each model `X`, we create a folder in which there are primarily two files:

- `XRepository.java`: Create an interface that provides abstract methods for the model. This includes all the `CRUD` operations and the custom finders (which may make use of joins and relations). This file can be used for developer reference to get an idea of all the available methods.
- `JDBCXRepository.java`: It is implements `XRepository` interface. It uses `JdbcTemplate` object for executing SQL queries or updates to interact with MySQL Database.
