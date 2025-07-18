package com.beta.signal.trading.exchange.core.constants;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExchangeConstantsTest {

    @Test
    void testDefaultPort() {
        assertEquals(8999, ExchangeConstants.DEFAULT_PORT);
    }

    @Test
    void testOrderIndices() {
        assertEquals(0, ExchangeConstants.ORDER_ID_INDEX);
        assertEquals(1, ExchangeConstants.ORDER_TYPE_INDEX);
        assertEquals(2, ExchangeConstants.ORDER_PRICE_INDEX);
        assertEquals(3, ExchangeConstants.ORDER_QUANTITY_INDEX);
    }

    @Test
    void testOrderQueueCapacities() {
        assertEquals(512, ExchangeConstants.BUY_ORDER_QUEUE_CAPACITY);
        assertEquals(512, ExchangeConstants.SELL_ORDER_QUEUE_CAPACITY);
    }

    @Test
    void testOrderActions() {
        assertEquals(1, ExchangeConstants.PLACE_ORDER);
        assertEquals(2, ExchangeConstants.CANCEL_ORDER);
        assertEquals(-1, ExchangeConstants.INVALID_DEFAULT_ORDER);
        assertEquals(-2, ExchangeConstants.DISCONNECT);
    }

    @Test
    void testDefaultClientIdentifier() {
        assertEquals("-1", ExchangeConstants.DEFAULT_CLIENT_IDENTIFIER);
    }

    @Test
    void testRegexPatterns() {
        assertEquals("[0-9]+", ExchangeConstants.DIGIT_REGEX);
        assertEquals("-?\\d+(\\.\\d+)?", ExchangeConstants.NUMERIC_REGEX);
    }

    @Test
    void testOrderMessageSplitter() {
        assertEquals("\\,", ExchangeConstants.ORDER_MESSAGE_SPLITTER);
    }

    @Test
    void testLocalhost() {
        assertEquals("localhost", ExchangeConstants.LOCALHOST);
    }

    @Test
    void testPrintOptions() {
        assertEquals("\nYou are connected to the exchange, input(1/2/3):\n1 : Place Order\n2 : Cancel Order\n3 : Quit\n", ExchangeConstants.PRINT_OPTIONS);
    }

    @Test
    void testOrderTypes() {
        assertEquals("BUY", ExchangeConstants.BUY);
        assertEquals("SELL", ExchangeConstants.SELL);
    }
}