package com.timeyang.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.stream.Stream;

/**
 * 为购物车事件{@link CartEvent}提供数据访问功能
 *
 * @author yangck
 */
public interface CartEventRepository extends JpaRepository<CartEvent, Long> {
    /**
     * <p>先从cart_event中取得事件类型为CLEAR_CART/CHECKOUT的事件，然后以时间降序排列，然后通过'LIMIT 1'只取时间最大的那个事件，命名为t，(临时的意思)</p>
     * <p>将整个cart_event与之前的t进行右外连接，条件是两个user_id相同，同时事件创建时间在t与2900亿年后之间，通过事件id不等于t的事件id(因为't'对应的cart_event是CLEAR_CART/CHECKOUT，不应该被聚合)</p>
     * <p>这样保证获取到的事件流只包含从最后一个CLEAR_CART/CHECKOUT之后的ADD_ITEM、REMOVE_ITEM的事件，因为CLEAR_CART/CHECKOUT不应该被聚合</p>
     * @param userId
     * @return
     */
    @Query(value = "SELECT c.*\n" +
            "FROM (\n" +
            "       SELECT *\n" +
            "       FROM cart_event\n" +
            "       WHERE user_id = :userId AND (cart_event_type = 'CLEAR_CART' OR cart_event_type = 'CHECKOUT')\n" +
            "       ORDER BY cart_event.created_at DESC\n" +
            "       LIMIT 1\n" +
            "     ) t\n" +
            "RIGHT JOIN cart_event c ON c.user_id = t.user_id\n" +
            "WHERE c.created_at BETWEEN coalesce(t.created_at, 0) AND 9223372036854775807 AND coalesce(t.id, -1) != c.id\n" +
            "ORDER BY c.created_at ASC", nativeQuery = true)
    Stream<CartEvent> getCartEventStreamByUserId(@Param("userId")Long userId);
}