package com.elasticjee.cart;

import com.elasticjee.data.BaseEntity;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

/**
 * 购物车事件
 * @author yangck
 */
@Entity(name = "CartEvent")
public class CartEvent extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    private CartEventType cartEventType;
    private Long userId;
    private String productId;
    private Integer quantity;

    public CartEvent() {
    }

    /**
     * 检出/清除购物车时使用这个构造器
     * @param cartEventType
     * @param userId
     */
    public CartEvent(CartEventType cartEventType, Long userId) {
        this.cartEventType = cartEventType;
        this.userId = userId;
    }

    /**
     * ADD_ITEM/REMOVE_ITEM时使用这个构造器
     * @param cartEventType
     * @param userId
     * @param productId
     * @param quantity
     */
    public CartEvent(CartEventType cartEventType, Long userId, String productId, Integer quantity) {
        this.cartEventType = cartEventType;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartEvent{" +
                "id=" + id +
                ", cartEventType=" + cartEventType +
                ", userId=" + userId +
                ", productId='" + productId + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CartEventType getCartEventType() {
        return cartEventType;
    }

    public void setCartEventType(CartEventType cartEventType) {
        this.cartEventType = cartEventType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
}
