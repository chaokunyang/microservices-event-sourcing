package com.elasticjee.order;

import org.apache.tomcat.jni.Address;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

import static com.elasticjee.order.OrderStatus.CONFIRMED;

/**
 * 订单领域对象
 * @author chaokunyang
 */
@Document
public class Order {
    @Id
    private ObjectId orderId; // 使用ObjectId，因为它带有时间和自增顺序。注意在别的服务订单id还是要使用String，因为别的服务不应该有mongo依赖，所以getOrderId方法返回值也是String类型
    private String accountNumber;
    @Transient
    private OrderStatus orderStatus; // 订单状态是对事件聚合产生的，而不是持久化到数据库的
    private List<OrderItem> orderItems = new ArrayList<>();
    private Address shippingAddress;

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }

    /**
     * <p>incorporate 方法通过一个针对订单状态的简单的状态机(state machine) ，使用事件源(event sourcing)
     * 和聚合(aggregation)来生成订单的当前状态</p>
     * <p>下面的事件流图表示事件怎么被合并来生成订单状态。订单状态的事件日志可以被用来在分布式事务失败时回滚订单的状态。每个状态只对应两个操作：前进/后退。PURCHASED状态没有回退，DELIVERED状态没有前进</p>
     * <p>
     * Events:   +<--PURCHASED+  +<--CREATED+  +<---ORDERED+  +<----SHIPPED+ 回退 <br/>
     * *         |            |  |          |  |           |  |            | <br/>
     * Status    +PURCHASED---+PENDING------+CONFIRMED-----+SHIPPED--------+DELIVERED <br/>
     * *         |            |  |          |  |           |  |            | <br/>
     * Events:   +CREATED---->+  +ORDERED-->+  +SHIPPED--->+  +DELIVERED-->+ 前进<br/>
     * </p>
     * @param orderEvent 是将要被合并进状态机的事件
     * @return 有着聚合的订单状态的 {@link Order} 聚合
     */
    public Order incorporate(OrderEvent orderEvent) {
        if(orderStatus == null)
            orderStatus = OrderStatus.PURCHASED;

        switch (orderStatus) {
            case PURCHASED:
                if(orderEvent.getType() == OrderEventType.CREATED)
                    orderStatus = OrderStatus.PENDING; // 表示订单状态前进：PURCHASED -> PENDING
                break;
            case PENDING:
                if(orderEvent.getType() == OrderEventType.ORDERED)
                    orderStatus = CONFIRMED; // 表示订单状态前进：PENDING -> CONFIRMED
                else if (orderEvent.getType() == OrderEventType.PURCHASED)
                    orderStatus = OrderStatus.PURCHASED; // 表示订单状态回退：PENDING -> PURCHASED
                break;
            case CONFIRMED:
                if(orderEvent.getType() == OrderEventType.SHIPPED) {
                    orderStatus = OrderStatus.SHIPPED; // 表示订单状态前进：CONFIRMED -> SHIPPED
                }else if(orderEvent.getType() == OrderEventType.CREATED) {
                    orderStatus = OrderStatus.PENDING; // 表示订单状态回退：CONFIRMED -> PENDING
                }
                break;
            case SHIPPED:
                if(orderEvent.getType() == OrderEventType.DELIVERED) {
                    orderStatus = OrderStatus.DELIVERED; // 表示订单状态前进：SHIPPED -> DELIVERED
                }else if(orderEvent.getType() == OrderEventType.ORDERED) {
                    orderStatus = OrderStatus.CONFIRMED; // 表示订单状态回退：CONFIRMED -> PENDING
                }
                break;
            case DELIVERED:
                if(orderEvent.getType() == OrderEventType.SHIPPED) {
                    orderStatus = OrderStatus.SHIPPED; // 表示订单状态回退：DELIVERED -> SHIPPED
                }
                break;
            default:
                // 无效的订单状态类型
                break;
        }

        return this;
    }

    /**
     * 使用ObjectId，因为它带有时间和自增顺序
     * @return
     */
    public String getOrderId() {
        return orderId != null ? orderId.toHexString() : null;
    }

    public void setOrderId(ObjectId orderId) {
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

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", orderStatus=" + orderStatus +
                ", orderItems=" + orderItems +
                ", shippingAddress=" + shippingAddress +
                '}';
    }
}

