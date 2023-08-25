# Controllers

This repository contains all the source code for all the controllers that handle the request and the response to all the API endpoints that we are exposing. Each subfolder should be used to represent an additonal segment in the path of the route that it is controlling.

These controllers are expected to use the created repositories and provide the appropiate repsonse on the basis of the URL and incoming requests.

These controllers return the `Status Code` that is provided by the repository methods, and then either a `message` or the `response` entity, whichever is more relevant in the context of the particular endpoint.
