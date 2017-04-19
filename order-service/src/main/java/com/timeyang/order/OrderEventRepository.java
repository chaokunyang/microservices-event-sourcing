package com.timeyang.order;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.stream.Stream;

/**
 * @author yangck
 */
public interface OrderEventRepository extends MongoRepository<OrderEvent, String> {
    Stream<OrderEvent> findOrderEventsByOrderId(@Param("orderId") String orderId);
}