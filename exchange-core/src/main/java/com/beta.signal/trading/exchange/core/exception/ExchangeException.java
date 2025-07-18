package com.beta.signal.trading.exchange.core.exception;

import com.beta.signal.trading.exchange.core.enums.ExchangeError;

/**
 * Custom exception class for handling exchange-related errors.
 * This class extends the Exception class and provides additional functionality
 * to handle error codes and messages specific to the exchange.
 */
public class ExchangeException extends Exception {

    private final String code;

    public ExchangeException(ExchangeError message, String code) {
        super(message.error);
        this.code = code;
    }

    public ExchangeException(String message, Throwable cause) {
        super(message, cause);
        this.code = "UNKNOWN"; // Default value for code
    }

    public ExchangeException(String message) {
        super(message);
        this.code = "UNKNOWN"; // Default value for code
    }

    public String getCode() {
        return code;
    }
}