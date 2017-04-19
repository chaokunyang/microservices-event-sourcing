package com.timeyang.api.v1;

import com.timeyang.account.Account;
import com.timeyang.address.AddressType;
import com.timeyang.order.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 订单服务
 *
 * @author yangck
 */
@Service
public class OrderServiceV1 {

    private final Log log = LogFactory.getLog(getClass());

    @Autowired
    private OrderRepositroy orderRepositroy;

    @Autowired
    private OrderEventRepository orderEventRepository;

    @Autowired
    private OAuth2RestTemplate oAuth2RestTemplate;

    public Order createOrder(List<OrderItem> orderItems) {
        Account[] accounts = oAuth2RestTemplate.getForObject("http://account-service/v1/accounts", Account[].class);

        Account defaultAccount = Arrays.stream(accounts)
                .filter(Account::isDefaultAccount)
                .findFirst().orElse(null);

        if(defaultAccount == null) {
            return null;
        }

        Order newOrder = new Order(defaultAccount.getAccountNumber(),
                defaultAccount.getAddresses().stream()
                        .filter(address -> address.getAddressType() == AddressType.SHIPPING)
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("默认账户没有收货地址")));
        newOrder.setOrderItems(orderItems);
        newOrder = orderRepositroy.save(newOrder);

        return newOrder;
    }

    public Boolean addOrderEvent(OrderEvent orderEvent, Boolean validate) throws Exception {
        // 得到订单事件对应的订单
        Order order = orderRepositroy.findOne(orderEvent.getOrderId());

        if(validate) {
            // 验证事件对应的订单的账户号(account number)属于用户。
            validateAccountNumber(order.getAccountNumber());
        }

        // 保存订单事件
        orderEventRepository.save(orderEvent);

        return true;
    }

    public Order getOrder(String orderId, Boolean validate) {
        // 获取订单
        Order order = orderRepositroy.findOne(orderId);

        if(validate) {
            // 验证事件对应的订单的账户号(account number)属于用户
            try {
                validateAccountNumber(order.getAccountNumber());
            } catch (Exception e) {
                return null;
            }
        }

        Flux<OrderEvent> orderEvents = Flux.fromStream(orderEventRepository.findOrderEventsByOrderId(orderId));

        // 聚合订单状态
        return orderEvents.takeWhile(orderEvent -> orderEvent.getType() != OrderEventType.DELIVERED)
                .reduceWith(() -> order, Order::incorporate)
                .get();
    }

    public List<Order> getOrdersForAccount(String accountNUmber) throws Exception {
        validateAccountNumber(accountNUmber);

        List<Order> orders = orderRepositroy.findByAccountNumber(accountNUmber);

        return orders.stream()
                .map(order -> getOrder(order.getOrderId(), true))
                .filter(order -> order != null)
                .collect(Collectors.toList());
    }

    /**
     * 验证账户号是否有效
     * @param accountNumber
     * @return 一个布尔值表示账户号是否有效
     * @throws Exception 账户号无效时抛出异常
     */
    public boolean validateAccountNumber(String accountNumber) throws Exception {
        Account[] accounts = oAuth2RestTemplate.getForObject("http://account-service/v1/accounts", Account[].class);

        // 确保账户号被当前验证用户拥有
        if(accounts != null && !Arrays.stream(accounts).anyMatch(account -> Objects.equals(account.getAccountNumber(), accountNumber))) {
            log.error("账户号无效:" + accountNumber);
            throw new Exception("账户号无效:" + accountNumber);
        }

        return true;
    }

}

