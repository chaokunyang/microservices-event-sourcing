一、Spring Data Rest说明：

1、use the @RepositoryRestResource annotation to direct Spring MVC to create RESTful endpoints at /user. @RepositoryRestResource is not required for a repository to be exported. It is only used to change the export details, such as using /people instead of the default value of /persons. Using Spring Boot this is all autoconfigured; if you want to investigate how that works, you could start by looking at the RepositoryRestMvcConfiguration in Spring Data REST.

2、