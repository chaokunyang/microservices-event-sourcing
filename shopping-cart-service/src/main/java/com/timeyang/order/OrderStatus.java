package com.timeyang.order;

/**
 * 订单状态: PURCHASED ---> PENDING ---> CONFIRMED ---> SHIPPED ---> DELIVERED
 * @author yangck
 */
public enum OrderStatus {
    /**
     * <p>已购买。incorporate(合并) OrderEventType.PURCHASED 事件后，订单状态变为 PURCHASED</p>
     * <p>即系统已响应用户在页面点击的购买请求，但系统尚未创建订单</p>
     */
    PURCHASED,

    /**
     * 系统已生成订单，订单待处理。incorporate(合并) OrderEventType.CREATED 事件后，订单状态变为 PENDING
     * <p>即订单已创建，等待系统处理</p>
     */
    PENDING,

    /**
     * 订单已确认。incorporate(合并) OrderEventType.ORDERED 事件后，订单状态变为 CONFIRMED
     * <P>即系统已确认订单</P>
     */
    CONFIRMED,

    /**
     * 已发货。incorporate(合并) OrderEventType.SHIPPED 事件后，订单状态变为 SHIPPED
     * <p>即在线商店已发货</p>
     */
    SHIPPED,

    /**
     * 已交货。incorporate(合并) OrderEventType.DELIVERED 事件后，订单状态变为 DELIVERED
     * <p>即用户已收货</p>
     */
    DELIVERED
}