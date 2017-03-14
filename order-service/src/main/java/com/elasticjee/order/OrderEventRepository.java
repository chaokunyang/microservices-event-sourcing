package com.elasticjee.order;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author yangck
 */
public interface OrderEventRepository extends MongoRepository<OrderEventType, String> {
    List<OrderEvent> findOrderEventsByOrderId(@Param("orderId") String orderId);
}