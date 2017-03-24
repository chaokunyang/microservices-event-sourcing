1、Looking at an individual instances Hystrix data is not very useful in terms of the overall health of the system. Turbine is an application that aggregates all of the relevant /hystrix.stream endpoints into a combined /turbine.stream for use in the Hystrix Dashboard. Individual instances are located via Eureka. Running Turbine is as simple as annotating your main class with the @EnableTurbine annotation (e.g. using spring-cloud-starter-turbine to set up the classpath). All of the documented configuration properties from the Turbine 1 wiki apply. The only difference is that the turbine.instanceUrlSuffix does not need the port prepended as this is handled automatically unless turbine.instanceInsertPort=false.
http://cloud.spring.io/spring-cloud-netflix/spring-cloud-netflix.html

2、Spring Data Rest:
Spring Data REST builds on top of Spring Data repositories, analyzes your application’s domain model and exposes hypermedia-driven HTTP resources for aggregates contained in the model.

Features

- Exposes a discoverable REST API for your domain model using HAL as media type.
- Exposes collection, item and association resources representing your model.
- Supports pagination via navigational links.
- Allows to dynamically filter collection resources.
- Exposes dedicated search resources for query methods defined in your repositories.
- Allows to hook into the handling of REST requests by handling Spring ApplicationEvents.
- Exposes metadata about the model discovered as ALPS and JSON Schema.
- Allows to define client specific representations through projections.
- Ships a customized variant of the HAL Browser to leverage the exposed metadata.
- Currently supports JPA, MongoDB, Neo4j, Solr, Cassandra, Gemfire.
Allows advanced customizations of the default resources exposed.

3、检查能正常工作：
1)http://localhost:8787/api/catalog/v1/catalog 能返回所有目录实体列表List<Catalog>