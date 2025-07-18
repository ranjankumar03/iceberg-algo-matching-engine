package com.beta.signal.trading.exchange.engine.matching.strategy;

import com.beta.signal.trading.exchange.core.enums.OrderType;
import com.beta.signal.trading.exchange.core.pojo.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderBookTest {

    private OrderBook orderBook;

    @BeforeEach
    void setUp() {
        orderBook = new OrderBook();
    }

    @Test
    void testProcess_buyOrderMatchesSellOrder() {
        // Arrange
        Order sellOrder = new Order("1", OrderType.SELL, 100, 10);
        Order buyOrder = new Order("2", OrderType.BUY, 100, 10);

        // Act
        orderBook.process(sellOrder);
        orderBook.process(buyOrder);

        // Assert
        assertTrue(sellOrder.isDepleted());
        assertTrue(buyOrder.isDepleted());
    }

    @Test
    void testProcess_noMatch() {
        // Arrange
        Order sellOrder = new Order("1", OrderType.SELL, 105, 10);
        Order buyOrder = new Order("2", OrderType.BUY, 100, 10);

        // Act
        orderBook.process(sellOrder);
        orderBook.process(buyOrder);

        // Assert
        assertFalse(sellOrder.isDepleted());
        assertFalse(buyOrder.isDepleted());
    }

    @Test
    void testProcess_icebergOrder() {
        // Arrange
        Order icebergBuyOrder = new Order("1", OrderType.BUY, 100, 20, 10, true);
        Order sellOrder = new Order("2", OrderType.SELL, 100, 15);

        // Act
        orderBook.process(sellOrder);
        orderBook.process(icebergBuyOrder);

        // Assert
        assertEquals(10, icebergBuyOrder.getVisibleQuantity());
        assertTrue(!sellOrder.isDepleted());
    }

    @Test
    void testPrintTrades() {
        // Arrange
        Order sellOrder = new Order("1", OrderType.SELL, 100, 10);
        Order buyOrder = new Order("2", OrderType.BUY, 100, 10);

        orderBook.process(sellOrder);
        orderBook.process(buyOrder);

        // Act & Assert
        orderBook.printTrades(); // Verify output manually or redirect System.out for automated testing
    }

    @Test
    void testPrintBook() {
        // Arrange
        Order sellOrder = new Order("1", OrderType.SELL, 105, 10);
        Order buyOrder = new Order("2", OrderType.BUY, 100, 10);

        orderBook.process(sellOrder);
        orderBook.process(buyOrder);

        // Act & Assert
        orderBook.printBook(); // Verify output manually or redirect System.out for automated testing
    }
}