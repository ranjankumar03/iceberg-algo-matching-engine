package com.beta.signal.trading.exchange.core.pojo;

import com.beta.signal.trading.exchange.core.enums.OrderType;
import lombok.Getter;

/**
 * Order class representing a trading order.
 * It can be either a regular order or an iceberg order.
 */
@Getter
public class Order {

    public final String orderId;
    public final OrderType side;
    public final int price;
    /**
     * total quantity of the order
     */
    public final int totalQuantity;
    /**
     * true if iceberg order
     * false if regular order
     */
    public final boolean isIceberg;
    /**
     * peak size for iceberg order
     * ignored for regular order
     */
    public final int peak;
    /**
     * dynamic size for iceberg order
     */
    public int visibleQuantity;
    /**
     * remaining hidden size for iceberg order
     */
    public int hiddenRemaining;

    /**
     * Regular order constructor
     *
     * @param orderId
     * @param side
     * @param price
     * @param totalQuantity
     */
    public Order(String orderId, OrderType side, int price, int totalQuantity) {
        this(orderId, side, price, totalQuantity, totalQuantity, false);
    }

    /**
     * Iceberg order constructor
     *
     * @param orderId
     * @param side
     * @param price
     * @param totalQuantity
     * @param peak
     * @param isIceberg
     */
    public Order(String orderId, OrderType side, int price, int totalQuantity, int peak, boolean isIceberg) {
        this.orderId = orderId;
        this.side = side;
        this.price = price;
        this.totalQuantity = totalQuantity;
        this.isIceberg = isIceberg;
        this.peak = peak;
        this.visibleQuantity = isIceberg ? Math.min(peak, totalQuantity) : totalQuantity;
        this.hiddenRemaining = isIceberg ? totalQuantity - visibleQuantity : 0;
    }

    public boolean isBuy() {
        return side == OrderType.BUY;
    }

    /**
     * Checks if the order is depleted (i.e., both visible and hidden quantities are zero).
     *
     * @return true if the order is depleted, false otherwise.
     */
    public boolean isDepleted() {
        return visibleQuantity == 0 && hiddenRemaining == 0;
    }

    /**
     * Refreshes the iceberg order by refilling the visible quantity
     * from the hidden remaining quantity.
     */
    public void refreshIceberg() {
        int refill = Math.min(peak, hiddenRemaining);
        visibleQuantity = refill;
        hiddenRemaining -= refill;
    }

    public String getOrderType() {
        return side.name();
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%d,%d%s", orderId, side.name().charAt(0), price, totalQuantity, isIceberg ? "," + peak : "");
    }
}