package com.timeyang.order;

/**
 * <h1>描述订单 {@link Order} 状态。同时作为订单状态机</h1>
 * <p>订单状态前进: PURCHASED ---> PENDING ---> CONFIRMED ---> SHIPPED ---> DELIVERED</p>
 * <p>订单状态回退: PURCHASED <--- PENDING <--- CONFIRMED <--- SHIPPED <--- DELIVERED</p>
 * @author yangck
 */
public enum OrderStatus {
    /**
     * <p>已购买。incorporate(合并) OrderEventType.PURCHASED 事件后，订单状态变为 PURCHASED</p>
     * <p>即系统已响应用户在页面点击的购买请求，但系统尚未创建订单</p>
     */
    PURCHASED {
        @Override
        public OrderStatus nextStatus(OrderEvent orderEvent) {
            if(orderEvent.getType() == OrderEventType.CREATED)
               return OrderStatus.PENDING; // 表示订单状态前进：PURCHASED -> PENDING
            throw new IllegalArgumentException("Illegal order event type " + orderEvent + ". only accept " + OrderEventType.CREATED);
        }
    },

    /**
     * 系统已生成订单，订单待处理。incorporate(合并) OrderEventType.CREATED 事件后，订单状态变为 PENDING
     * <p>即订单已创建，等待系统处理</p>
     */
    PENDING {
        @Override
        public OrderStatus nextStatus(OrderEvent orderEvent) {
            if(orderEvent.getType() == OrderEventType.ORDERED)
                return CONFIRMED; // 表示订单状态前进：PENDING -> CONFIRMED
            else if (orderEvent.getType() == OrderEventType.PURCHASED)
                return OrderStatus.PURCHASED; // 表示订单状态回退：PENDING -> PURCHASED
            throw new IllegalArgumentException("Illegal order event type " + orderEvent + ". only accept " + OrderEventType.ORDERED + " or " + OrderEventType.PURCHASED);
        }
    },

    /**
     * 订单已确认。incorporate(合并) OrderEventType.ORDERED 事件后，订单状态变为 CONFIRMED
     * <P>即系统已确认订单</P>
     */
    CONFIRMED {
        @Override
        public OrderStatus nextStatus(OrderEvent orderEvent) {
            if(orderEvent.getType() == OrderEventType.SHIPPED) {
                return OrderStatus.SHIPPED; // 表示订单状态前进：CONFIRMED -> SHIPPED
            }else if(orderEvent.getType() == OrderEventType.CREATED) {
                return OrderStatus.PENDING; // 表示订单状态回退：CONFIRMED -> PENDING
            }
            throw new IllegalArgumentException("Illegal order event type " + orderEvent + ". only accept " + OrderEventType.SHIPPED + " or " + OrderEventType.CREATED);
        }
    },

    /**
     * 已发货。incorporate(合并) OrderEventType.SHIPPED 事件后，订单状态变为 SHIPPED
     * <p>即在线商店已发货</p>
     */
    SHIPPED {
        @Override
        public OrderStatus nextStatus(OrderEvent orderEvent) {
            if(orderEvent.getType() == OrderEventType.DELIVERED) {
                return OrderStatus.DELIVERED; // 表示订单状态前进：SHIPPED -> DELIVERED
            }else if(orderEvent.getType() == OrderEventType.ORDERED) {
                return OrderStatus.CONFIRMED; // 表示订单状态回退：CONFIRMED -> PENDING
            }
            throw new IllegalArgumentException("Illegal order event type " + orderEvent + ". only accept " + OrderEventType.DELIVERED + " or " + OrderEventType.ORDERED);
        }
    },

    /**
     * 已交货。incorporate(合并) OrderEventType.DELIVERED 事件后，订单状态变为 DELIVERED
     * <p>即用户已收货</p>
     */
    DELIVERED {
        @Override
        public OrderStatus nextStatus(OrderEvent orderEvent) {
            if(orderEvent.getType() == OrderEventType.SHIPPED) {
                return OrderStatus.SHIPPED; // 表示订单状态回退：DELIVERED -> SHIPPED
            }
            throw new IllegalArgumentException("Illegal order event type " + orderEvent + ". only accept " + OrderEventType.SHIPPED);
        }
    };

    /**
     * get next order status according to order event
     *
     * @param orderEvent order event
     * @return next order status, according to order event, next status maybe forward or rollback
     */
    public abstract OrderStatus nextStatus(OrderEvent orderEvent);
}