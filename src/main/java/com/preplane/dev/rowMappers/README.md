# rowMappers

This folder is used to configure the `rowMapper` objects which are used to map the fields to-and-fro between the SQL tables and the Java classes that we have defined.
It is important that all the fields exisiting the database object are mapped here, so that they all can be read automarically. In case of custom queries where the names of the response columns would be different, we may require custom mappers or seperate mapping from SQL result sets to the objects.
