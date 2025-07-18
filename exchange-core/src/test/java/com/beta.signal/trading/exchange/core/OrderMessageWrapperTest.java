package com.beta.signal.trading.exchange.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderMessageWrapperTest {

    @Test
    void testConstructorAndGetters() {
        String[] messages = {"Order1", "Order2"};
        OrderMessageWrapper wrapper = new OrderMessageWrapper(messages);

        assertArrayEquals(messages, wrapper.getRawMessage());
    }

    @Test
    void testSetRawMessage() {
        String[] messages = {"Order1", "Order2"};
        OrderMessageWrapper wrapper = new OrderMessageWrapper(null);

        wrapper.setRawMessage(messages);
        assertArrayEquals(messages, wrapper.getRawMessage());
    }

    @Test
    void testDetermineLength() {
        String[] messages = {"Order1", "Order2"};
        OrderMessageWrapper wrapper = new OrderMessageWrapper(messages);

        wrapper.determineLength();
        assertEquals(2, wrapper.getMessageLength());
    }

    @Test
    void testDetermineLengthWithNullMessage() {
        OrderMessageWrapper wrapper = new OrderMessageWrapper(null);

        wrapper.determineLength();
        assertEquals(0, wrapper.getMessageLength());
    }

    @Test
    void testToStringWithMessages() {
        String[] messages = {"Order1", "Order2"};
        OrderMessageWrapper wrapper = new OrderMessageWrapper(messages);

        String expected = "Original Order:\nOrder1\nOrder2\n";
        assertEquals(expected, wrapper.toString());
    }

    @Test
    void testToStringWithEmptyMessages() {
        String[] messages = {};
        OrderMessageWrapper wrapper = new OrderMessageWrapper(messages);

        String expected = "Original Order: No data available\n";
        assertEquals(expected, wrapper.toString());
    }

    @Test
    void testToStringWithNullMessages() {
        OrderMessageWrapper wrapper = new OrderMessageWrapper(null);

        String expected = "Original Order: No data available\n";
        assertEquals(expected, wrapper.toString());
    }
}