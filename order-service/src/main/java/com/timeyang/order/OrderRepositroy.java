package com.timeyang.order;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * 订单{@link Order}常用操作
 * @author yangck
 */
public interface OrderRepositroy extends MongoRepository<Order, String> {
    List<Order> findByAccountNumber(String accountNumber);
}
