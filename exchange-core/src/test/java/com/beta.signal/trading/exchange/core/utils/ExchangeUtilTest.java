package com.beta.signal.trading.exchange.core.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExchangeUtilTest {

    @Test
    void testIsDigitWithValidDigit() {
        assertTrue(ExchangeUtil.isDigit("12345"));
    }

    @Test
    void testIsDigitWithInvalidDigit() {
        assertFalse(ExchangeUtil.isDigit("123a45"));
    }

    @Test
    void testIsDigitWithEmptyString() {
        assertFalse(ExchangeUtil.isDigit(""));
    }

    @Test
    void testIsDigitWithNull() {
        assertFalse(ExchangeUtil.isDigit(null));
    }

    @Test
    void testIsNumericWithValidNumber() {
        assertTrue(ExchangeUtil.isNumeric("123.45"));
    }

    @Test
    void testIsNumericWithInvalidNumber() {
        assertFalse(ExchangeUtil.isNumeric("123.45a"));
    }

    @Test
    void testIsNumericWithEmptyString() {
        assertFalse(ExchangeUtil.isNumeric(""));
    }

    @Test
    void testIsNumericWithNull() {
        assertFalse(ExchangeUtil.isNumeric(null));
    }
}