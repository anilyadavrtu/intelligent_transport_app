## Task Description
You should be able to start the example application by executing com.transport.transport_app, which starts a webserver on port 8080 (http://localhost:8080) and serves SwaggerUI where can inspect and try existing endpoints.

The project is based on a small web service which uses the following technologies:

* Java 1.8
* Spring Boot
* Database H2 (In-Memory)
* Maven
*Swagger


 * All new entities have an ID with type of Long and a date_created with type of ZonedDateTime.
 * The architecture of the web service is built with the following components:
 	* DataTransferObjects: Objects which are used for outside communication via the API
    * Controller: Implements the processing logic of the web service, parsing of parameters and validation of in- and outputs.
    * Service: Implements the business logic and handles the access to the DataAccessObjects.
    * DataAccessObjects: Interface for the database. Inserts, updates, deletes and reads objects from the database.
    * DomainObjects: Functional Objects which might be persisted in the database.
 * TestDrivenDevelopment to test code.
 
