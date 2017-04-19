一、Spring Data Rest说明：

1、use the @RepositoryRestResource annotation to direct Spring MVC to create RESTful endpoints at /user. @RepositoryRestResource is not required for a repository to be exported. It is only used to change the export details, such as using /people instead of the default value of /persons. Using Spring Boot this is all autoconfigured; if you want to investigate how that works, you could start by looking at the RepositoryRestMvcConfiguration in Spring Data REST.


二、Spring Oauth2.0
1、An Authorization Server is nothing more than a bunch of endpoints, and they are implemented in Spring OAuth2 as Spring MVC handlers. We already have a secure application, so it’s really just a matter of adding the @EnableAuthorizationServer annotation： com.timeyang.config.AuthorizationServerConfig

with that new annotation in place Spring Boot will install all the necessary endpoints and set up the security for them, provided we supply a few details of an OAuth2 client we want to support:
```yaml
security:
  oauth2:
    client:
      client-id: timeyang
      client-secret: timeyangsecret
      scope: openid
      auto-approve-scopes: '.*'
```
2、
### The context path has to be explicit if you are running both the client and the auth server on localhost, otherwise the cookie paths clash and the two apps cannot agree on a session identifier.