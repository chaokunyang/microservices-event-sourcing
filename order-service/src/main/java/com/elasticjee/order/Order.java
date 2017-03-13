package com.elasticjee.order;

import org.apache.tomcat.jni.Address;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单领域对象
 * @author chaokunyang
 */
@Document
public class Order {
    @Id
    private String orderId;
    private String accountNumber;
    @Transient
    private OrderStatus orderStatus; // 订单状态是对事件聚合产生的，而不是持久化到数据库的
    private List<OrderItem> orderItems = new ArrayList<>();
    private Address shippingAddress;

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }

    public Order incorporate(OrderEvent orderEvent) {

    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}

