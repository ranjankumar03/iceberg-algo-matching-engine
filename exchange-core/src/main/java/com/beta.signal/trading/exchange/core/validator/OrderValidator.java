package com.beta.signal.trading.exchange.core.validator;

import com.beta.signal.trading.exchange.core.enums.ExchangeError;
import com.beta.signal.trading.exchange.core.exception.ExchangeException;
import com.beta.signal.trading.exchange.core.pojo.Order;

/**
 * OrderValidator class implements the Validator interface to validate order objects.
 * It checks if the order has a valid side, price, and qty.
 */
public class OrderValidator<T> implements Validator<T> {

    @Override
    public <T> void validate(T type) throws ExchangeException {
        Order order = (Order) type;

        validateOrderType(order);
        validatePrice(order);
        validateQuantity(order);
        if (order.isIceberg()) {
            validateIcebergOrder(order);
        }
    }

    private void validateOrderType(Order order) throws ExchangeException {
        if (order.getSide() == null) {
            throw new ExchangeException(ExchangeError.INVALID_SIDE, "Order type cannot be null");
        }
    }

    private void validatePrice(Order order) throws ExchangeException {
        if (order.getPrice() < 1) {
            throw new ExchangeException(ExchangeError.INVALID_PRICE, "Price must be greater than 0");
        }
    }

    private void validateQuantity(Order order) throws ExchangeException {
        if (order.getTotalQuantity() < 1) {
            throw new ExchangeException(ExchangeError.INVALID_SIZE, "Total quantity must be greater than 0");
        }
    }

    private void validateIcebergOrder(Order order) throws ExchangeException {
        if (order.getPeak() < 1) {
            throw new ExchangeException(ExchangeError.INVALID_SIZE, "Peak size must be greater than 0 for iceberg orders");
        }
        if (order.getPeak() > order.getTotalQuantity()) {
            throw new ExchangeException(ExchangeError.INVALID_SIZE, "Peak size cannot exceed total quantity");
        }
    }
}