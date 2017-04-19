package com.timeyang.order;

import java.io.Serializable;

/**
 * 订单项
 *
 * @author yangck
 */
public class OrderItem implements Serializable {
    private String name, productId;
    private Integer quantity;
    private Double price, tax;

    public OrderItem() {
    }

    public OrderItem(String name, String productId, Integer quantity, Double price, Double tax) {
        this.name = name;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.tax = tax;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }
}