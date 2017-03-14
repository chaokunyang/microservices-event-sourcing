1、The Edge Service is a Spring Cloud application that is responsible for securely exposing HTTP routes from backend microservices. The Edge Service is a very important component in a Spring Cloud microservices architecture, as it provides frontend applications a way to expose every API from the backend services as a single unified REST API.

2、To take advantage of the Edge Service, a Spring Boot application would simply attach it as a backing service in the target environment. In doing this, the Edge Service will provide secure authenticated access to all REST APIs that are exposed by the backend services. To be able to do this, the Edge Service matches a request route’s URL fragment from a front-end application to a back-end microservice through a reverse proxy to retrieve the remote REST API response.
  
  The end result is that the Edge Service provides a seamless REST API that will become embedded in any Spring Boot application that attaches it as a backing service using Spring Cloud Netflix’s Zuul starter project.
  
4、To skip having a service automatically added, set zuul.ignored-services to a list of service id patterns. If a service matches a pattern that is ignored, but also included in the explicitly configured routes map, then it will be unignored. Example:

```
application.yml
 zuul:
  ignoredServices: '*'
  routes:
    users: /myusers/**
```
In this example, all services are ignored except "users".