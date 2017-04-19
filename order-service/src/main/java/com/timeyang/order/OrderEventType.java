package com.timeyang.order;

/**
 * <p>Events: PURCHASED ---> CREATED ----> ORDERED ---> SHIPPED ---> DELIVERED</p>
 * <strong>注意，在订单事件里面，用户发起购买请求和生成订单是两个不同的事件，一定要分开</strong>
 * @author yangck
 */
public enum OrderEventType {
    /**
     * 用户已购买产品事件。对应用户在页面发起购买请求，系统收到用户发起购买请求<strong>这一事实</strong>，但尚未创建订单
     */
    PURCHASED,

    /**
     * 系统已创建订单事件。系统生成订单，<strong>这一事件事实与用户无关，是系统完成的操作</strong>
     */
    CREATED,

    /**
     * 用户订购物品事件。对应用户确认订单
     */
    ORDERED,

    /**
     * 在线商店已发货事件。
     */
    SHIPPED,

    /**
     * 在线商店已交货事件
     */
    DELIVERED
}