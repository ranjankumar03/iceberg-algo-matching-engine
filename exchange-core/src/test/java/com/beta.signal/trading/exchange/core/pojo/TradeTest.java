package com.beta.signal.trading.exchange.core.pojo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TradeTest {

    @Test
    void testConstructorAndGetters() {
        Trade trade = new Trade("Aggressive123", "Resting456", 100, 50);

        assertEquals("Aggressive123", trade.aggressiveId());
        assertEquals("Resting456", trade.restingId());
        assertEquals(100, trade.price());
        assertEquals(50, trade.quantity());
    }

    @Test
    void testToString() {
        Trade trade = new Trade("Aggressive123", "Resting456", 100, 50);

        String expected = "trade Aggressive123,Resting456,100,50";
        assertEquals(expected, trade.toString());
    }
}