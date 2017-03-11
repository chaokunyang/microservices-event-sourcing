package com.elasticjee.warehouse;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

/**
 * @author chaokunyang
 */
@Repository
public interface WarehouseRepository extends GraphRepository<Warehouse> {
}
