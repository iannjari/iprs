# About
My interpretation of Kenya's Integrated Population Registration System using Spring Boot, Temporal (WIP) and MongoDB - exposed using a gRPC and GraphQL(WIP) interface.

## Project Structure

``` 
- .
- .github/ - github actions scripts
- docker/ - doker compose files...
- infra/ - common infra like shared k8s components 
- common_utils/ - common utilities shared across applications e.g grpc, kafka configs, custom protobuf repository interface
- buildSrc/ - kotlin DSL scripts for shared dependencies
- iprs_svc/ - main server - exposed via gRPC - holding logic (workflows ochestrated using Temporal - WIP)
- graphql_server/ - server that is a backend-for-frontends proxy to servers
- protobuf/ - contains the protocal buffer definitions of the data and rpc services
```

## Run
### You need:
- Java 11 or higher
- Postman - recent version that can handle gRPC server reflection and GraphQL introspection
- MongoDB running natively or on a virtualisation platform like Docker
- Docker
- minikube - if running this in a Kubernetes cluster is desired


### docker
build an image of desired app using:
```
/gradlew app_name:jibDockerBuild
```
then:
```
docker run app_name
```

### kubernetes
to run locally, you need minikube, or docker's kubernetes env set up

- apply the manifests in `infra/k8s` to set up shared components in the cluster
- build docker images for desired apps using the gradle jib task on each
```
/gradlew app_name:jibDockerBuild
```
- with images in your env (for minikube, load images from your docker env using `minikube image load image_name`), apply 
 the application manifest located in `app_module/k8s/`