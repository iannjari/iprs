# About
A mock of Kenya's Integrated Population Registration System using Spring Boot, Temporal and MongoDB - exposed using a gRPC and GraphQL interface.

## Project Structure

``` 
- .
- buildSrc/ - contains kotlin DSL scripts for shared dependencies
- iprs_svc/ - main server - exposed via gRPC - holding logic (workflows ochestrated using Temporal)
```

## Run

You need:
 - Java 11 or higher
 - Postman - recent version that can handle gRPC server reflection and GraphQL introspection
 - MongoDB running natively or on a virtualisation platform like Docker
 - Docker

Steps:
 - Run Temporal on docker
 - Run the project by running 