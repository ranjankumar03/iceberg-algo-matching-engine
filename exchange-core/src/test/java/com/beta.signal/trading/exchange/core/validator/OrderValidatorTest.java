package com.beta.signal.trading.exchange.core.validator;

import com.beta.signal.trading.exchange.core.enums.OrderType;
import com.beta.signal.trading.exchange.core.exception.ExchangeException;
import com.beta.signal.trading.exchange.core.pojo.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderValidatorTest {

    private final OrderValidator<Order> orderValidator = new OrderValidator<>();

    @Test
    void testInvalidOrderType() {
        Order invalidOrder = new Order("BUY", null, 100, 10); // Invalid order type
        assertThrows(ExchangeException.class, () -> orderValidator.validate(invalidOrder),
                "Expected INVALID_ORDER_TYPE exception");
    }

    @Test
    void testInvalidPrice() {
        Order invalidOrder = new Order("BUY", OrderType.BUY, 0, 10); // Invalid price
        assertThrows(ExchangeException.class, () -> orderValidator.validate(invalidOrder),
                "Expected INVALID_PRICE exception");
    }

    @Test
    void testInvalidSize() {
        Order invalidOrder = new Order("BUY", OrderType.BUY, 100, 0); // Invalid size
        assertThrows(ExchangeException.class, () -> orderValidator.validate(invalidOrder),
                "Expected INVALID_SIZE exception");
    }
}