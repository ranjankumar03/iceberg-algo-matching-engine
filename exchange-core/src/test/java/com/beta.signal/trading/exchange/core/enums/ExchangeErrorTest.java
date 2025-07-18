package com.beta.signal.trading.exchange.core.enums;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExchangeErrorTest {

    @Test
    void testGetErrorMessage() {
        assertEquals("Please provide a valid Order!!", ExchangeError.INVALID_ORDER.getErrorMessage());
        assertEquals("Invalid Order Type. Please supply a valid order type (BUY/SELL)!!", ExchangeError.INVALID_SIDE.getErrorMessage());
        assertEquals("Invalid Price. Please supply a valid order price!!", ExchangeError.INVALID_PRICE.getErrorMessage());
        assertEquals("Invalid Quantity. Please supply a valid order quantity!!", ExchangeError.INVALID_SIZE.getErrorMessage());
    }

    @Test
    void testEnumValues() {
        ExchangeError[] errors = ExchangeError.values();
        assertEquals(4, errors.length);
        assertEquals(ExchangeError.INVALID_ORDER, errors[0]);
        assertEquals(ExchangeError.INVALID_SIDE, errors[1]);
        assertEquals(ExchangeError.INVALID_PRICE, errors[2]);
        assertEquals(ExchangeError.INVALID_SIZE, errors[3]);
    }

    @Test
    void testEnumValueOf() {
        assertEquals(ExchangeError.INVALID_ORDER, ExchangeError.valueOf("INVALID_ORDER"));
        assertEquals(ExchangeError.INVALID_SIDE, ExchangeError.valueOf("INVALID_SIDE"));
        assertEquals(ExchangeError.INVALID_PRICE, ExchangeError.valueOf("INVALID_PRICE"));
        assertEquals(ExchangeError.INVALID_SIZE, ExchangeError.valueOf("INVALID_SIZE"));
    }
}
