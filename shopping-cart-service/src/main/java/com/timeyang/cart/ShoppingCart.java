package com.timeyang.cart;

import com.timeyang.catalog.Catalog;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.log4j.Logger;
import reactor.core.publisher.Flux;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 购物车{@link ShoppingCart}对象代表用户从购物车add/remove/clear/checkout products 动作产生的{@link CartEvent}的聚合
 *
 * @author yangck
 */
public class ShoppingCart {
    private Logger logger = Logger.getLogger(ShoppingCart.class);
    private Map<String, Integer> productMap = new HashMap<>();
    private List<CartItem> cartItems = new ArrayList<>();
    private Catalog catalog;

    public ShoppingCart(Catalog catalog) {
        this.catalog = catalog;
    }

    /**
     * 从购物车事件的聚合(aggregate)生成并得到cart items
     * @return 代表购物车状态的一个新的 {@link CartItem} list
     * @throws Exception 如果在购物车中的一个产品不在目录里面，则抛出异常
     */
    public List<CartItem> getCartItems() throws Exception {
        cartItems = productMap.entrySet().stream()
                .map(item -> new CartItem(item.getKey(), catalog.getProducts().stream()
                        .filter(product -> Objects.equals(product.getProductId(), item.getKey()))
                        .findFirst()
                        .orElse(null), item.getValue()))
                .filter(item -> item.getQuantity() > 0)
                .collect(Collectors.toList());

        if(cartItems.stream().anyMatch(item -> item.getProduct() == null))
            throw new Exception("没有在目录里找到产品");

        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    /**
     * Incorporates a new {@link CartEvent} and update the shopping cart
     * @param cartEvent 是将要改变购物车状态的{@link CartEvent}
     * @return 在应用了新的 {@link CartEvent} 后返回 {@link ShoppingCart} 的状态
     */
    public ShoppingCart incorporate(CartEvent cartEvent) {
        Flux<CartEventType> validCartEventTypes =
                Flux.fromStream(Stream.of(CartEventType.ADD_ITEM, CartEventType.REMOVE_ITEM));

        // CartEvent类型必须是 ADD_ITEM or REMOVE_ITEM
        if(validCartEventTypes.exists(cartEventType ->
                cartEvent.getCartEventType().equals(cartEventType)).get()) {
            // 根据事件类型更新购物车每个产品的数量的聚合
            productMap.put(cartEvent.getProductId(),
                    productMap.getOrDefault(cartEvent.getProductId(), 0) +
                            (cartEvent.getQuantity() * (cartEvent.getCartEventType()
                                    .equals(CartEventType.ADD_ITEM) ? 1 : -1))
            );
        }

        // Return the updated state of the aggregate to the reactive stream's reduce method
        return this;
    }

    /**
     * Determines whether or not the {@link CartEvent} is a terminal event，causing the stream to end while generating an aggregate {@link ShoppingCart}
     * @param cartEventType is the {@link CartEventType} to evaluate
     * @return a flag 表示是否事件是terminal
     */
    public static Boolean isTerminal(CartEventType cartEventType) {
        return (cartEventType == CartEventType.CLEAR_CART || cartEventType == CartEventType.CHECKOUT);
    }

    @JsonIgnore
    public Map<String, Integer> getProductMap() {
        return productMap;
    }

    public void setProductMap(Map<String, Integer> productMap) {
        this.productMap = productMap;
    }

    @JsonIgnore
    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "productMap=" + productMap +
                ", cartItems=" + cartItems +
                ", catalog=" + catalog +
                '}';
    }
}