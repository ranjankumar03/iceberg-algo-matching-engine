package com.beta.signal.trading.cache;

import com.beta.signal.trading.exchange.core.pojo.Trade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TradeCacheTest {

    private TradeCache tradeCache;

    @BeforeEach
    void setUp() {
        tradeCache = new TradeCache();
    }

    @Test
    void testAddTrade() {
        // Arrange
        Trade trade = new Trade("1", "2", 100, 10);

        // Act
        tradeCache.addTrade(trade);

        // Assert
        List<Trade> trades = tradeCache.getTrades();
        assertEquals(1, trades.size());
        assertEquals(trade, trades.get(0));
    }

    @Test
    void testGetTradesReturnsCopy() {
        // Arrange
        Trade trade = new Trade("1", "2", 100, 10);
        tradeCache.addTrade(trade);

        // Act
        List<Trade> trades = tradeCache.getTrades();
        trades.clear(); // Attempt to modify the returned list

        // Assert
        assertEquals(1, tradeCache.getTrades().size()); // Original list should remain unchanged
    }

    @Test
    void testClearTrades() {
        // Arrange
        Trade trade = new Trade("1", "2", 100, 10);
        tradeCache.addTrade(trade);

        // Act
        tradeCache.clearTrades();

        // Assert
        assertTrue(tradeCache.getTrades().isEmpty());
    }
}