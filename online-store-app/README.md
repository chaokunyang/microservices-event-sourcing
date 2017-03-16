1、The ingredients of the client are online store. When we run this app it will look for a configuration file which we provide, part as follows:
```yaml
security:
  enable-csrf: false
  oauth2:
    resource:
      # to complete the authentication it needs to know where the user info endpoint is in user-service
      userInfoUri: http://localhost:8181/auth/user
    # the filter also needs to know about the client registration with user-service
    client:
      clientId: elasticjee
      clientSecret: elasticjeesecret
      accessTokenUri: http://localhost:8181/oauth/token
      userAuthorizationUri: http://localhost:8181/oauth/authorize
  ignored: /assets/**
```
### The configuration refers to a user info endpoint "/user" which we implemented in user-service.