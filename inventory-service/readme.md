一、neo4j
1、关系/路径长度

Adding Relationship Length

Now, if we want to retrieve friends of friends that our user doesn’t know yet, we can simply expand the Cypher pattern :

MATCH (user:User {login:'heller.perry'})-[:KNOWS]->(friend)-[:KNOWS]->(foaf)
WHERE NOT((user)-[:KNOWS]->(foaf))
RETURN user, foaf
This will work without any problem, but we can simplify the query by introducing a “relationship length” (or “path length”):

MATCH (user:User {login:'heller.perry'})-[:KNOWS*2]->(foaf)
WHERE NOT((user)-[:KNOWS]->(foaf))
RETURN user, foaf





