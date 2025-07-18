package com.beta.signal.trading.exchange.engine.matching.strategy;

import com.beta.signal.trading.cache.TradeCache;
import com.beta.signal.trading.exchange.core.enums.OrderType;
import com.beta.signal.trading.exchange.core.pojo.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Queue;
import java.util.TreeMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class LimitOrderMatchingStrategyTest {

    private LimitOrderMatchingStrategy strategy;
    private TreeMap<Integer, Queue<Order>> buyOrders;
    private TreeMap<Integer, Queue<Order>> sellOrders;

    @BeforeEach
    void setUp() {
        strategy = new LimitOrderMatchingStrategy();
        buyOrders = new TreeMap<>();
        sellOrders = new TreeMap<>();
    }

    @Test
    void testMatch_buyOrderMatchesSellOrder() {
        // Arrange
        Order buyOrder = new Order("1", OrderType.BUY, 100, 10);
        Order sellOrder = new Order("2", OrderType.SELL, 100, 5);

        sellOrders.computeIfAbsent(100, k -> new LinkedList<>()).add(sellOrder);

        // Act
        strategy.match(buyOrder, OrderType.SELL, sellOrders);

        // Assert
        assertEquals(5, buyOrder.getVisibleQuantity());
        assertEquals(0, sellOrder.getVisibleQuantity());
        TradeCache tradeCache = strategy.getTradeCache();
        assertEquals(1, tradeCache.getTrades().size());
        assertEquals(100, tradeCache.getTrades().get(0).price());
        assertEquals(5, tradeCache.getTrades().get(0).quantity());
    }

    @Test
    void testMatch_noPriceMatch() {
        // Arrange
        Order buyOrder = new Order("1", OrderType.BUY, 100, 10);
        Order sellOrder = new Order("2", OrderType.SELL, 105, 5);

        sellOrders.computeIfAbsent(105, k -> new LinkedList<>()).add(sellOrder);

        // Act
        strategy.match(buyOrder, OrderType.SELL, sellOrders);

        // Assert
        assertEquals(10, buyOrder.getVisibleQuantity());
        assertEquals(5, sellOrder.getVisibleQuantity());
        assertFalse(sellOrders.get(105).isEmpty());
        TradeCache tradeCache = strategy.getTradeCache();
        assertEquals(0, tradeCache.getTrades().size());
    }

    @Test
    void testMatch_icebergOrderReplenishment() {
        // Arrange
        Order buyOrder = new Order("1", OrderType.BUY, 100, 10, 5, true);
        Order sellOrder = new Order("2", OrderType.SELL, 100, 10);

        sellOrders.computeIfAbsent(100, k -> new LinkedList<>()).add(sellOrder);

        // Act
        strategy.match(buyOrder, OrderType.SELL, sellOrders);

        // Assert
        assertEquals(5, buyOrder.getVisibleQuantity());
        assertEquals(5, sellOrder.getVisibleQuantity());
        assertTrue(!sellOrders.get(100).isEmpty());
        TradeCache tradeCache = strategy.getTradeCache();
        assertEquals(1, tradeCache.getTrades().size());
        assertEquals(100, tradeCache.getTrades().get(0).price());
        assertEquals(5, tradeCache.getTrades().get(0).quantity());
    }
}