package com.beta.signal.trading.exchange.core.enums;

/**
 * Enum representing various error messages for the exchange.
 */
public enum ExchangeError {

    INVALID_ORDER("Please provide a valid Order!!"),
    INVALID_SIDE("Invalid Order Type. Please supply a valid order type (BUY/SELL)!!"),
    INVALID_PRICE("Invalid Price. Please supply a valid order price!!"),
    INVALID_SIZE("Invalid Quantity. Please supply a valid order quantity!!");

    public String error;

    ExchangeError(String error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return this.error;
    }
}
