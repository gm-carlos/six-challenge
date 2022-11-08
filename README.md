# TRADING PLATFORM

Basic trading platform implementation with a REST API available for the management and operation of the platform.

## About the implementation

The platform has been implemented based on:
- Java 11
- Maven 3.6
- Spring Boot
- H2 as database
- TestNG as test runner
- Swagger for API documentation

## HOW TO...

### Running unit tests

There is just one class for Unit Testing, if you want to run it use:

`mvn clean test`

### Build from source

Clone the repository, navigate to the root folder of the project and run:

`mvn clean install`

or we can use the simple Docker image and run:

`docker build . -t trading:latest`

### Running the application
After building the code with the previous command, we can run the application:

`java -jar target/trading-platform-0.0.1-SNAPSHOT.jar TradingPlatform.java`

or using Docker:

`docker run -p 8080:8080 --name trading trading:latest`

### Running acceptance tests
In order to run the acceptance tests, run the application with the previous command and execute:

`mvn test -Dtest=CucumberRunner`

### Use the API
The application is integrated with swagger. We can see the API documentation in the following URL once the application is running:

[API documentation](http://localhost:8080/api-docs)

Or if you want to interact with the application through the API use Swagger:

[Swagger URL](http://localhost:8080/swagger-ui/index.html)

## TODOs

- Business application:
  - HTTP API User authentication.
  - User profiles.
  - Manage remove objects.
  - Implement Price/Time Algorithm.
  - Improve management of HTTP request/responses.
  - Improve data validation.
  - Ability to choose algorithm.
- Testing:
  - Implement more unit and integration tests.
  - Integrate with code analysis service (i.e Sonar).
  - Load Testing.
- Application life cycle:
  - Integrate with CICD Solution (i.e. Github Actions).
  - Improve Docker image.
  - Apply Semantic Versioning.