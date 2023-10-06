# Security

This folder contains all the relevant handlers and services to support `JWT` based authentication and `RBAC` (Role Based Access Control). As per the current configuration:

- All the `api/auth/**` routes require no user authentication.
- All other routes require an user to be signed in (that is they have a valid `JWT`) in the request headers.

All the [controllers](../controllers/) can use `RBAC` with the help of `hasRole()` annotation to privilege the access of certain endpoints to certain users only.
