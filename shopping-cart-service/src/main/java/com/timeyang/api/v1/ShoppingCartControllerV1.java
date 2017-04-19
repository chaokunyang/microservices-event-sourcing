package com.timeyang.api.v1;

import com.timeyang.cart.CartEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author yangck
 */
@RestController
@RequestMapping("/v1")
public class ShoppingCartControllerV1 {
    @Autowired
    private ShoppingCartServiceV1 shoppingCartService;

    /**
     * 增加购物车事件
     * @param cartEvent
     * @return
     * @throws Exception
     */
    @RequestMapping(path = "/events", method = RequestMethod.POST)
    public ResponseEntity addCartEvent(@RequestBody CartEvent cartEvent) throws Exception {
        return Optional.ofNullable(shoppingCartService.addCartEvent(cartEvent))
                .map(event -> new ResponseEntity(HttpStatus.NO_CONTENT))
                .orElseThrow(() -> new Exception("不能够找到购物车"));
    }

    /**
     * 检出购物车
     * @return
     * @throws Exception
     */
    @RequestMapping(path = "/checkout", method = RequestMethod.POST)
    public ResponseEntity checkoutCart() throws Exception {
        return Optional.ofNullable(shoppingCartService.checkOut())
                .map(checkoutResult -> new ResponseEntity<>(checkoutResult, HttpStatus.OK))
                .orElseThrow(() -> new Exception("不能够检出购物车"));
    }

    /**
     * 获取购物车
     * @return
     * @throws Exception
     */
    @RequestMapping(path = "/cart", method = RequestMethod.GET)
    public ResponseEntity getCart() throws Exception {
        return Optional.ofNullable(shoppingCartService.getShoppingCart())
                .map(cart -> new ResponseEntity(cart, HttpStatus.OK))
                .orElseThrow(() -> new Exception("不能够获取购物车"));
    }
}