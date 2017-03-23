一、neo4j
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