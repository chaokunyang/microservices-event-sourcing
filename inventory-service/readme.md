一、
1、问题
data-rest依赖的 spring-data-commons是1.12.7.RELEASE的，而我使用的spring-data-neo4j是4.0.0.RELEASE的，而其依赖的spring-data-commons是1.11.0.RELEASE的，在1.12.7.RELEASE上无法生成自定义查询方法，两者接口不匹配，具体可查看GraphQueryLookupStrategy甚至会看到这个类本身报错（与spring-data-commons版本不兼容）。通过命令可查看到冲突：C:\chaokunyang\Devlopment\DevProjects\spring-event-sourcing\inventory-service>gradle dI --dependency spring-data-commons / gradle dependencies。
        // spring-boot插件也会指定org.springframework.data:spring-data-commons:1.12.7.RELEASE。即使在configurations中强制版本为1.11.0.RELEASE也不管用。唯一办法就是再显示加上依赖：compile 'org.springframework.data:spring-data-commons:1.11.0.RELEASE'。三者一起方才能够改变spring-data-commons版本。如果可以的话最好还是升级spring-data-neo4j，使之采用spring-boot的默认版本，把neo4j的配置重写下就可以了。


二、neo4j
1、关系/路径长度

Adding Relationship Length

Now, if we want to retrieve friends of friends that our user doesn’t know yet, we can simply expand the Cypher pattern :

```
MATCH (user:User {login:'heller.perry'})-[:KNOWS]->(friend)-[:KNOWS]->(foaf)
WHERE NOT((user)-[:KNOWS]->(foaf))
RETURN user, foaf
```
This will work without any problem, but we can simplify the query by introducing a “relationship length” (or “path length”):

```
MATCH (user:User {login:'heller.perry'})-[:KNOWS*2]->(foaf)
WHERE NOT((user)-[:KNOWS]->(foaf))
RETURN user, foaf
```


2、在执行查询：
```
MATCH (product:Product)<-[:PRODUCT_TYPE]-(inventory:Inventory) WHERE product.productId = 'SKU-24642' AND NOT (inventory)<-[:CONTAINS_PRODUCT]-() RETURN inventory
```
```
MATCH (product:Product)<-[:PRODUCT_TYPE]-(inventory:Inventory)-[:STOCKED_IN]->(:Warehouse {name:'Pivotal SF'}) WHERE product.productId = 'SKU-24642' AND NOT (inventory)<-[:CONTAINS_PRODUCT]-() RETURN inventory
```
```
MATCH (product:Product)<-[:PRODUCT_TYPE]-(inventory:Inventory) WHERE product.productId in ['PD-00001', 'SKU-34563', 'SKU-12464', 'SKU-64233'] AND NOT (inventory)<-[:CONTAINS_PRODUCT]-() RETURN inventory
```
能够得出正确结果，表面查询语法写的没错

3、Cypher示例
1)
```
MATCH (user)-[:friend]->(follower)
WHERE user.name IN ['Joe', 'John', 'Sara', 'Maria', 'Steve'] AND follower.name =~ 'S.*'
RETURN user.name, follower.name
```
2)id()
***Returns the id of the relationship or node.***
Syntax: id( expression )
Arguments:

Name	|   Description
----    |   ----
expression | An expression that returns a node or a relationship.
Query:
```
MATCH (a)
RETURN id(a)
This returns the node id for three nodes.
```
Result:

+-------+
| id(a) |
+-------+
| 0     |
| 1     |
| 2     |
| 3     |
| 4     |
+-------+
5 rows

4、Spring Data Rest:
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

5、检查Spring Data Rest能正常工作：
1）http://localhost:${port}/api/     返回所有资源的链接的json表示等
2)http://localhost:${port}/api/catalogs
3）http://localhost:${port}/api/products

6、配置文档 http://docs.spring.io/spring-data/data-neo4j/docs/4.1.7.RELEASE/reference/html/#reference_setup

7、不要使用以下方法配置驱动 http://docs.spring.io/spring-data/data-neo4j/docs/4.1.7.RELEASE/reference/html/#reference_setup 

* SDN 4.1 now provides support for connecting to Neo4j using different drivers. As a result, the RemoteServer and InProcessServer classes from previous versions should not be used, and are no longer supported.
```java
    // 不要使用
    /*@Bean
    public Neo4jServer neo4jServer() {
        String uri = properties.getUri();
        String username = properties.getUsername();
        String password= properties.getPassword();
        return new RemoteServer(uri, username, password);
    }*/
```
    
* To configure the Driver programmatically, create a Configuration bean and pass it as the first argument to the SessionFactory constructor in your Spring configuration
```java
    // 不要使用
    /*@Bean
    public Configuration getConfiguration() {
        Configuration config = new Configuration();
        config
                .driverConfiguration()
                .setDriverClassName("org.neo4j.ogm.drivers.http.driver.HttpDriver")
                .setCredentials(properties.getUsername(), properties.getPassword())
                .setURI(properties.getUri()); // http://user:password@localhost:7474
        return config;
    }*/
```

**文档中推荐第二种方法配置驱动，但实际上并不能使用。初次能够读取配置，但之后url会变为null。暂不知道是spring data neo4j的问题还是neo4j-ogm-http-driver-2.0.6本身的问题。所以目前只使用ogm.properties配置驱动**

8、spring-data-neo4j4.1.2之后，4,2,0之前存在bug:Id must be assignable to Serializable
https://jira.spring.io/browse/DATAREST-928
https://jira.spring.io/browse/DATAGRAPH-955