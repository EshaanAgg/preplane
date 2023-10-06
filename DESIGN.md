# Design Documentation

This document contains some of the design choices made while implementing this project.

## Authentication

- There is no way to create users with `role` other than `USER`. The first root `ADMIN` must be created manually in the backend, who can then later assign roles to other users.
- The SQL file creates a root admin user for us to login and experiment with the `ADMIN` capabilities. The credentials for the same are:

```
Username: admin
Password: Admin123$
```
