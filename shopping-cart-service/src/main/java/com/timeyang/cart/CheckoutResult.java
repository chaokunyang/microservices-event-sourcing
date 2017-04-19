package com.timeyang.cart;

import com.timeyang.order.Order;

import java.io.Serializable;

/**
 * 检出购物车结果
 *
 * @author yangck
 */
public class CheckoutResult implements Serializable {
    private String resultMessage;
    private Order order;

    public CheckoutResult() {
    }

    public CheckoutResult(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public CheckoutResult(String resultMessage, Order order) {
        this.resultMessage = resultMessage;
        this.order = order;
    }

    @Override
    public String toString() {
        return "CheckoutResult{" +
                "resultMessage='" + resultMessage + '\'' +
                ", order=" + order +
                '}';
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
