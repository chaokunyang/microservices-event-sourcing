package com.elasticjee.order;

import com.elasticjee.address.Address;
import com.elasticjee.data.BaseEntityDto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangck
 */
public class Order extends BaseEntityDto {
    private String orderId;
    private String accountNumber;
    private OrderStatus orderStatus;
    private List<OrderItem> lineItems = new ArrayList<>();
    private Address shippingAddress;

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", orderStatus=" + orderStatus +
                ", lineItems=" + lineItems +
                ", shippingAddress=" + shippingAddress +
                '}';
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

    public List<OrderItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<OrderItem> lineItems) {
        this.lineItems = lineItems;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
