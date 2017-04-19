package com.timeyang.api.v1;

import com.timeyang.cart.*;
import com.timeyang.catalog.Catalog;
import com.timeyang.inventory.Inventory;
import com.timeyang.order.Order;
import com.timeyang.order.OrderEvent;
import com.timeyang.order.OrderEventType;
import com.timeyang.order.OrderItem;
import com.timeyang.user.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
/**
 * The {@link ShoppingCartServiceV1} implements business logic for aggregating the state of a user's actions represented a sequence of {@link CartEvent}. The generated aggregate use event sourcing to produce a {@link com.timeyang.cart.ShoppingCart} containing a collection of {@link CartItem}
 * @author yangck
 */
@Service
public class ShoppingCartServiceV1 {
    public static final double TAX = .06;

    private final Log log = LogFactory.getLog(getClass());

    @Autowired
    private OAuth2RestTemplate oAuth2RestTemplate;

    @Autowired
    @Qualifier("loadBalancedRestTemplate")
    private RestTemplate restTemplate;

    @Autowired
    private CartEventRepository cartEventRepository;

    /**
     * 从user-service得到已验证的用户
     * @return 当前验证的用户
     */
    public User getAuthenticatedUser() {
        //System.out.println(oAuth2RestTemplate.getForObject("http://user-service/auth/v1/me", User.class));
        return oAuth2RestTemplate.getForObject("http://user-service/auth/v1/me", User.class);
    }

    /**
     * 为当前已验证用户添加一个购物车事件
     * @param cartEvent 详细描述用户执行的action的事件
     * @return 一个标识表示结果是否成功
     */
    public Boolean addCartEvent(CartEvent cartEvent) {
        User user = getAuthenticatedUser();
        if(user != null) {
            cartEvent.setUserId(user.getId());
            cartEventRepository.save(cartEvent);
        }else {
           return null;
        }
        return true;
    }

    public Boolean addCartEvent(CartEvent cartEvent, User user) {
        if(user != null) {
            cartEvent.setUserId(user.getId());
            cartEventRepository.save(cartEvent);
        }else {
            return null;
        }
        return true;
    }


    /**
     * 为当前已验证用户得到购物车
     * @return an aggregate object derived from events performed by the user
     * @throws Exception
     */
    public ShoppingCart getShoppingCart() throws Exception {
        User user = oAuth2RestTemplate.getForObject("http://user-service/auth/v1/me", User.class);
        ShoppingCart shoppingCart = null;
        if(user != null) {
            Catalog catalog = restTemplate.getForObject("http://catalog-service/v1/catalog", Catalog.class);
            shoppingCart = aggregateCartEvents(user, catalog);
        }
        return shoppingCart;
    }

    /**
     * 聚合(Aggregate)一个用户的cart events，返回一个 {@link ShoppingCart}
     * @param user 获取购物车的用户
     * @param catalog 用于生成购物车的目录
     * @return 一个表示用户购物车聚合状态的购物车
     * @throws Exception 如果在购物车中的一个产品不在目录里面，则抛出异常
     */
    public ShoppingCart aggregateCartEvents(User user, Catalog catalog) throws Exception {
        Flux<CartEvent> cartEvents = Flux.fromStream(cartEventRepository.getCartEventStreamByUserId(user.getId()));

        // 聚合购物车的状态
        ShoppingCart shoppingCart = cartEvents.takeWhile(cartEvent -> !ShoppingCart.isTerminal(cartEvent.getCartEventType()))
                .reduceWith(() -> new ShoppingCart(catalog), ShoppingCart::incorporate)
                .get();

        shoppingCart.getCartItems();
        return shoppingCart;
    }

    /**
     * 检出用户当前的购物车，在处理完支付后生成一张新的订单
     * TODO 支付集成
     * @return 检出操作的结果
     * @throws Exception
     */
    public CheckoutResult checkOut() throws Exception {
        CheckoutResult checkOutResult = new CheckoutResult();

        // 检查可用库存
        ShoppingCart currentCart = null;
        try {
            currentCart = getShoppingCart();
        }catch (Exception e) {
            log.error("获取购物车失败", e);
        }

        if(currentCart != null) {
            // 协调当前购物车与库存
            Inventory[] inventory = oAuth2RestTemplate.getForObject(String.format("http://inventory-service/v1/inventory?productIds=%s", currentCart.getCartItems()
                    .stream()
                    .map(CartItem::getProductId)
                    .collect(Collectors.joining(","))), Inventory[].class);

            if(inventory != null) {
                Map<String, Long> inventoryItems = Arrays.stream(inventory)
                        .map(inv -> inv.getProduct().getProductId())
                        .collect(groupingBy(Function.identity(), counting()));
                        /*.collect(Collectors.groupingBy(
                                (inv -> inv.getProduct().getProductId()), Collectors.counting()));*/

                if(checkAvailableInventory(checkOutResult, currentCart, inventoryItems)) {
                    // 预定库存 Reserve the available inventory

                    // 创建订单
                    Order order = oAuth2RestTemplate.postForObject("http://order-service/v1/orders",
                            currentCart.getCartItems()
                                    .stream()
                                    .map(cartItem -> new OrderItem(cartItem.getProduct().getName(), cartItem.getProductId(),
                                            cartItem.getQuantity(), cartItem.getProduct().getUnitPrice(), TAX))
                                    .collect(Collectors.toList()),
                            Order.class);

                    if(order != null) {
                        // 订单创建成功
                        checkOutResult.setResultMessage("订单已成功创建");

                        // 增加订单事件
                        oAuth2RestTemplate.postForEntity(
                                String.format("http://order-service/v1/orders/%s/events", order.getOrderId()),
                                new OrderEvent(OrderEventType.CREATED, order.getOrderId()), ResponseEntity.class);

                        checkOutResult.setOrder(order);

                        User user = oAuth2RestTemplate.getForObject("http://user-service/auth/v1/me", User.class);

                        // 增加CartEventType.CHECKOUT事件，清除购物车，因为已经生成订单成功
                        addCartEvent(new CartEvent(CartEventType.CHECKOUT, user.getId()), user);
                    }
                }
            }
        }

        // 返回检出结果：要么库存不足，要么检出成功
        return checkOutResult;
    }

    /**
     * 检查是否有充足的库存
     * @param checkOutResult
     * @param currentCart
     * @param inventoryItems
     * @return
     */
    public Boolean checkAvailableInventory(CheckoutResult checkOutResult, ShoppingCart currentCart, Map<String, Long> inventoryItems) {
        Boolean hasInventory = true;
        // 判断库存是否可用
        try {
            List<CartItem> inventoryUnAvailableCartItems = currentCart.getCartItems()
                    .stream()
                    .filter(item -> inventoryItems.get(item.getProductId()) - item.getQuantity() < 0)
                    .collect(Collectors.toList());

            if(inventoryUnAvailableCartItems.size() > 0) {
                String productIds = inventoryUnAvailableCartItems
                        .stream()
                        .map(CartItem::getProductId)
                        .collect(Collectors.joining(", "));
                checkOutResult.setResultMessage(
                        String.format("以下产品没有充足的库存可用：%s. " +
                            "请降低这些产品的数量再次尝试.", productIds));
                hasInventory = false;
            }
        } catch (Exception e) {
            log.error("检查是否有可利用的库存时出错", e);
        }

        return hasInventory;
    }
}