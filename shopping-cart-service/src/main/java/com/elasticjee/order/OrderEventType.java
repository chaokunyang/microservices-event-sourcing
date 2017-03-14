package com.elasticjee.order;

/**
 * Events: PURCHASED ---> CREATED ----> ORDERED ---> SHIPPED ---> DELIVERED
 * @author yangck
 */
public enum OrderEventType {
    PURCHASED, // 购买
    CREATED, // 创建
    ORDERED, // 订购
    SHIPPED, // 发货
    DELEVERED // 交货
}