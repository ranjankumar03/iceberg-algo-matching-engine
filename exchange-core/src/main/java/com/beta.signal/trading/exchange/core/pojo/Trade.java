package com.beta.signal.trading.exchange.core.pojo;

/**
 * Trade class representing a trade between two orders.
 * It contains the IDs of the aggressive and resting orders, the price, and the quantity of the trade.
 */
public record Trade(String aggressiveId, String restingId, int price, int quantity) {

    @Override
    public String toString() {
        return String.format("trade %s,%s,%d,%d", aggressiveId, restingId, price, quantity);
    }
}
