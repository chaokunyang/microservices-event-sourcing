package com.timeyang.cart;

import com.timeyang.product.Product;

/**
 * 购物车项
 *
 * @author yangck
 */
public class CartItem {
    private String productId;
    private Product product;
    private Integer quantity;

    public CartItem(String productId, Product product, Integer quantity) {
        this.productId = productId;
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "productId='" + productId + '\'' +
                ", product=" + product +
                ", quantity=" + quantity +
                '}';
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
