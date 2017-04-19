package com.timeyang.api.v1;

import com.timeyang.order.Order;
import com.timeyang.order.OrderEvent;
import com.timeyang.order.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author yangck
 */
@RestController
@RequestMapping("/v1")
public class OrderControllerV1 {

    @Autowired
    private OrderServiceV1 orderService;

    @RequestMapping(value = "/accounts/{accountNumber}/orders")
    public ResponseEntity getOrders(@PathVariable("accountNumber") String accountNumber) throws Exception {
        return Optional.ofNullable(orderService.getOrdersForAccount(accountNumber))
                .map(orders -> new ResponseEntity<>(orders, HttpStatus.OK))
                .orElseThrow(() -> new Exception("用户不存在该账户"));
    }

    @RequestMapping(value = "/orders/{orderId}/events", method = RequestMethod.POST)
    public ResponseEntity addOrderEvent(@RequestBody OrderEvent orderEvent, @PathVariable("orderId") String orderId) throws Exception {
        Assert.notNull(orderEvent);
        Assert.notNull(orderId);
        return Optional.ofNullable(orderService.addOrderEvent(orderEvent, true))
                .map(result -> new ResponseEntity<>(result, HttpStatus.NO_CONTENT))
                .orElseThrow(() -> new Exception("订单事件不能被应用到该订单"));
    }

    @RequestMapping(value = "/orders/{orderId}", method = RequestMethod.GET)
    public ResponseEntity<Order> getOrder(@PathVariable("orderId") String orderId) throws Exception {
        Assert.notNull(orderId);
        return Optional.ofNullable(orderService.getOrder(orderId, true))
                .map(order -> new ResponseEntity<>(order, HttpStatus.OK))
                .orElseThrow(() -> new Exception("不能够获取订单"));
    }

    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public ResponseEntity<Order> createOrder(@RequestBody List<OrderItem> orderItems) throws Exception {
        Assert.notNull(orderItems);
        Assert.isTrue(orderItems.size() > 0);
        return Optional.ofNullable(orderService.createOrder(orderItems))
                .map(order -> new ResponseEntity<>(order, HttpStatus.OK))
                .orElseThrow(() -> new Exception("不能够创建订单"));
    }

}
