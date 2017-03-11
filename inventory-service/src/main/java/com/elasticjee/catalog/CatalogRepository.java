package com.elasticjee.catalog;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author chaokunyang
 */
@Repository
public interface CatalogRepository extends GraphRepository<Catalog> {
    Catalog findCatalogByCatalogNumber(@Param("catalogNumber") Long catalogNumber);
}
