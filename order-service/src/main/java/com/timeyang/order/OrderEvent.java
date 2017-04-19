package com.timeyang.order;

import com.timeyang.data.BaseEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 订单事件
 *
 * @author yangck
 */
@Document
public class OrderEvent extends BaseEntity {
    @Id
    private String id;
    private OrderEventType type;
    private String orderId;

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