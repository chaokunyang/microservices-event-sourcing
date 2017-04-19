package com.timeyang.address;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

/**
 * @author chaokunyang
 */
public interface AddressRepository extends GraphRepository<Address> {
}
