package com.elasticjee.api.v1;

import com.elasticjee.cart.CartEvent;
import com.elasticjee.cart.CartItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * The {@link ShoppingCartServiceV1} implements business logic for aggregating the state of a user's actions represented a sequence of {@link CartEvent}. The generated aggregate use event sourcing to produce a {@link com.elasticjee.cart.ShoppingCart} containing a collection of {@link CartItem}
 * @author yangck
 */
@Service
public class ShoppingCartServiceV1 {
    public static final double TAX = .06;
    private final Log log = LogFactory.getLog(getClass());

}