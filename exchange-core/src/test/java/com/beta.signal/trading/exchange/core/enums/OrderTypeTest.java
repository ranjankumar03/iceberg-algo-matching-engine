package com.beta.signal.trading.exchange.core.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderTypeTest {

    @Test
    void testEnumValues() {
        OrderType[] orderTypes = OrderType.values();
        assertEquals(2, orderTypes.length);
        assertEquals(OrderType.BUY, orderTypes[0]);
        assertEquals(OrderType.SELL, orderTypes[1]);
    }

    @Test
    void testEnumValueOf() {
        assertEquals(OrderType.BUY, OrderType.valueOf("BUY"));
        assertEquals(OrderType.SELL, OrderType.valueOf("SELL"));
    }
}