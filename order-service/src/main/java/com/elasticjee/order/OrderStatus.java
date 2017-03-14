package com.elasticjee.order;

/**
 * 订单状态: PURCHASED ---> PENDING ---> CONFIRMED ---> SHIPPED ---> DELIVERED
 * @author yangck
 */
public enum OrderStatus {
    PURCHASED, // 已购买
    PENDING, // 待处理
    CONFIRMED, // 已确认
    SHIPPED, // 已发货
    DELIVERED // 已交货
}