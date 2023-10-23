# rowMappers

This folder is used to configure the `rowMapper` objects which are used to map the fields to-and-fro between the SQL tables and the Java classes that we have defined.

It is important that all the fields exisiting in the database object are mapped here, so that they all can be read automarically. In case of custom queries where the names of the response columns would be different, we may require custom mappers or seperate mapping from SQL result sets to the objects.

The fields which are not directly stored on the database instance need not be mapped. The type of mapper used will depend on the fields in the result set of the query being executed. If we map extra fields, the mappers would fail silently and we see unexpected results for our API requests.
