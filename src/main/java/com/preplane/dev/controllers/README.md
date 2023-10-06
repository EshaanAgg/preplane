# Controllers

This repository contains all the source code for all the controllers that handle the request and the response to all the API endpoints that we are exposing. Each subfolder should be used to represent an additonal segment in the path of the route that it is controlling.

- These controllers are expected to use the created repositories and provide the appropiate repsonse on the basis of the URL and incoming requests.
- All the controllers can use `RBAC` with the help of `hasRole()` annotation to privilege the access of certain endpoints to certain users only.
- All the controllers are expected to perform their own validation before mutations, that is they must verify that they are mutating the resource which is actually owned by the signed in user. The controllers has perform these checks in line, or use separate imported classes for separation of concerns.
- The controllers return the `Status Code` that is provided by the repository methods, and then either a `message` or the `response` entity, whichever is more relevant in the context of the particular endpoint. They are then serialized and sent to the user as `JSON`.
