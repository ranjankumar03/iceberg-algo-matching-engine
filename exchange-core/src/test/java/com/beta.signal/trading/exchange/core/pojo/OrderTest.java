package com.beta.signal.trading.exchange.core.pojo;

import com.beta.signal.trading.exchange.core.enums.OrderType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void testRegularOrderConstructor() {
        Order order = new Order("123", OrderType.BUY, 100, 50);

        assertEquals("123", order.getOrderId());
        assertEquals(OrderType.BUY, order.getSide());
        assertEquals(100, order.getPrice());
        assertEquals(50, order.getTotalQuantity());
        assertFalse(order.isIceberg());
        assertEquals(50, order.getVisibleQuantity());
        assertEquals(0, order.getHiddenRemaining());
    }

    @Test
    void testIcebergOrderConstructor() {
        Order order = new Order("123", OrderType.SELL, 200, 100, 30, true);

        assertEquals("123", order.getOrderId());
        assertEquals(OrderType.SELL, order.getSide());
        assertEquals(200, order.getPrice());
        assertEquals(100, order.getTotalQuantity());
        assertTrue(order.isIceberg());
        assertEquals(30, order.getVisibleQuantity());
        assertEquals(70, order.getHiddenRemaining());
    }

    @Test
    void testIsBuy() {
        Order buyOrder = new Order("123", OrderType.BUY, 100, 50);
        Order sellOrder = new Order("124", OrderType.SELL, 100, 50);

        assertTrue(buyOrder.isBuy());
        assertFalse(sellOrder.isBuy());
    }

    @Test
    void testIsDepleted() {
        Order order = new Order("123", OrderType.BUY, 100, 50, 30, true);

        assertFalse(order.isDepleted());

        order.refreshIceberg();
        order.refreshIceberg(); // Deplete the order
        assertTrue(order.isDepleted());
    }

    @Test
    void testRefreshIceberg() {
        Order order = new Order("123", OrderType.BUY, 100, 50, 20, true);

        assertEquals(20, order.getVisibleQuantity());
        assertEquals(30, order.getHiddenRemaining());

        order.refreshIceberg();
        assertEquals(20, order.getVisibleQuantity());
        assertEquals(10, order.getHiddenRemaining());

        order.refreshIceberg();
        assertEquals(10, order.getVisibleQuantity());
        assertEquals(0, order.getHiddenRemaining());
    }

    @Test
    void testToString() {
        Order regularOrder = new Order("123", OrderType.BUY, 100, 50);
        Order icebergOrder = new Order("124", OrderType.SELL, 200, 100, 30, true);

        assertEquals("123,B,100,50", regularOrder.toString());
        assertEquals("124,S,200,100,30", icebergOrder.toString());
    }
}