package com.timeyang.cart;

/**
 * 购物车事件类型
 *
 * @author yangck
 */
public enum CartEventType {
    ADD_ITEM, // 添加项
    REMOVE_ITEM, // 移除项
    CLEAR_CART, // 清除购物车
    CHECKOUT // 检出项
}
