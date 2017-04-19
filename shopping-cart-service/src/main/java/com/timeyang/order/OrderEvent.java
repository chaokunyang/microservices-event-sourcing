package com.timeyang.order;


import com.timeyang.data.BaseEntity;

/**
 * 订单事件
 *
 * @author yangck
 */
public class OrderEvent extends BaseEntity {
    private String id;
    private OrderEventType type;
    private String orderId;

    public OrderEvent(OrderEventType type, String orderId) {
        this.type = type;
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "OrderEvent{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", orderId='" + orderId + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OrderEventType getType() {
        return type;
    }

    public void setType(OrderEventType type) {
        this.type = type;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}