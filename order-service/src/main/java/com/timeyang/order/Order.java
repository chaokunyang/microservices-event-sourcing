package com.timeyang.order;

import com.timeyang.address.Address;
import com.timeyang.address.AddressType;
import com.timeyang.data.BaseEntity;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单领域类
 * @author chaokunyang
 */
@Document
public class Order extends BaseEntity {
    @Id
    private ObjectId orderId; // 使用ObjectId，因为它带有时间和自增顺序。注意在别的服务订单id还是要使用String，因为别的服务不应该有mongo依赖，所以getOrderId方法返回值也是String类型
    private String accountNumber;
    @Transient
    private OrderStatus orderStatus; // 订单状态是对事件聚合产生的，而不是持久化到数据库的
    private List<OrderItem> orderItems = new ArrayList<>();
    private Address shippingAddress;

    public Order() {
        this.orderStatus = OrderStatus.PURCHASED;
    }

    public Order(String accountNumber, Address shippingAddress) {
        this();
        this.accountNumber = accountNumber;
        this.shippingAddress = shippingAddress;
        if (shippingAddress.getAddressType() == null)
            this.shippingAddress.setAddressType(AddressType.SHIPPING);
    }

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

        orderStatus = orderStatus.nextStatus(orderEvent);

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

