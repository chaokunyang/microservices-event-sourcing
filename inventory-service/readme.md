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
MATCH (product:Product)<-[:PRODUCT_TYPE]-(inventory:Inventory) WHERE product.productId in ['SKU-24642', 'SKU-34563', 'SKU-12464', 'SKU-64233'] AND NOT (inventory)<-[:CONTAINS_PRODUCT]-() RETURN inventory
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