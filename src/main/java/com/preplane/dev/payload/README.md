# Payload

This directory consists of the various kinds of payloads that we get as requests and may require to send as responses to the user. The direct inclusion of these classes would help to make the function definitions of the controllers less verbose and clean.

- We will define a folder to organize the responses and the requests.
- All the fields of these payloads are kept public, as they are only ever made to be used within the context of the controller and thus are safe to expose their fields to everyone. Other classes which are used in multiple other classes but expose their data via getters and setters.
