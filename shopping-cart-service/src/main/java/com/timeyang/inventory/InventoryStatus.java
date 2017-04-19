package com.timeyang.inventory;

/**
 * 库存状态
 * @author chaokunyang
 */
public enum InventoryStatus {
    IN_STOCK, // 有库存
    ORDERED, // 已下单
    RESERVED, // 预定的
    SHIPPED, // 已发货
    DELIVERED // 已交货
}
