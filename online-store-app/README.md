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
      clientId: timeyang
      clientSecret: timeyangsecret
      accessTokenUri: http://localhost:8181/oauth/token
      userAuthorizationUri: http://localhost:8181/oauth/authorize
  ignored: /assets/**
```
### The configuration refers to a user info endpoint "/user" which we implemented in user-service.

2、The /logout endpoint requires us to POST to it, and to protect the user from Cross Site Request Forgery (CSRF, pronounced "sea surf"), it requires a token to be included in the request. The value of the token is linked to the current session, which is what provides the protection, so we need a way to get that data into our JavaScript app.
  
  AngularJS also has built in support for CSRF (they call it XSRF), but it is implemented in a slightly different way than the out-of-the box behaviour of Spring Security. What Angular would like is for the server to send it a cookie called "XSRF-TOKEN" and if it sees that, it will send the value back as a header named "X-XSRF-TOKEN". To teach Spring Security about this we need to add a filter that creates the cookie and also we need to tell the existing CRSF filter about the header name. 