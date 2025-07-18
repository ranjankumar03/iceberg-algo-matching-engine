package com.beta.signal.trading.exchange.core.exception;

import com.beta.signal.trading.exchange.core.enums.ExchangeError;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExchangeExceptionTest {

    @Test
    void testConstructorWithMessageAndCause() {
        Throwable cause = new RuntimeException("Root cause");
        ExchangeException exception = new ExchangeException("Custom message", cause);

        assertEquals("Custom message", exception.getMessage());
        assertEquals("UNKNOWN", exception.getCode());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testConstructorWithMessageOnly() {
        ExchangeException exception = new ExchangeException("Custom message");

        assertEquals("Custom message", exception.getMessage());
        assertEquals("UNKNOWN", exception.getCode());
    }

    @Test
    void testGetCode() {
        ExchangeException exception = new ExchangeException(ExchangeError.INVALID_ORDER, "ERR002");

        assertEquals("ERR002", exception.getCode());
    }
}