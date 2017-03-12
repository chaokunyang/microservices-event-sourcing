package com.elasticjee.warehouse;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

/**
 * @author chaokunyang
 */
public interface WarehouseRepository extends GraphRepository<Warehouse> {
}
