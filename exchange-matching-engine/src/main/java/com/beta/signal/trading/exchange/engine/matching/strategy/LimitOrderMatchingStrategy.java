package com.beta.signal.trading.exchange.engine.matching.strategy;

import com.beta.signal.trading.cache.TradeCache;
import com.beta.signal.trading.exchange.core.enums.OrderType;
import com.beta.signal.trading.exchange.core.pojo.Order;
import com.beta.signal.trading.exchange.core.pojo.Trade;

import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class LimitOrderMatchingStrategy implements OrderMatchingStrategy {

    private final TradeCache tradeCache = new TradeCache();

    @Override
    public void match(Order incoming, OrderType oppSide, TreeMap<Integer, Queue<Order>> book) {
        Iterator<Map.Entry<Integer, Queue<Order>>> it = book.entrySet().iterator();

        while (it.hasNext() && incoming.visibleQuantity > 0) {
            Map.Entry<Integer, Queue<Order>> level = it.next();
            int bookPrice = level.getKey();

            if ((incoming.isBuy() && bookPrice > incoming.price) || (!incoming.isBuy() && bookPrice < incoming.price)) {
                break; // no price match
            }

            Queue<Order> orders = level.getValue();
            Iterator<Order> qIt = orders.iterator();

            while (qIt.hasNext() && incoming.visibleQuantity > 0) {
                Order resting = qIt.next();
                int tradedQty = Math.min(incoming.visibleQuantity, resting.visibleQuantity);
                tradeCache.addTrade(new Trade(incoming.orderId, resting.orderId, bookPrice, tradedQty));

                if (incoming.isIceberg) {
                    // For iceberg orders the full display portion is consumed even if tradedQty is less.
                    resting.visibleQuantity -= tradedQty;
                    if (resting.visibleQuantity == 0) {
                        if (resting.isIceberg && resting.hiddenRemaining > 0) {
                            resting.refreshIceberg();
                        } else {
                            qIt.remove();
                        }
                    }
                    if (incoming.hiddenRemaining > 0) {
                        incoming.refreshIceberg();
                    } else {
                        incoming.visibleQuantity = 0;
                    }
                } else {
                    incoming.visibleQuantity -= tradedQty;
                    resting.visibleQuantity -= tradedQty;
                    if (resting.visibleQuantity == 0) {
                        if (resting.isIceberg && resting.hiddenRemaining > 0) {
                            resting.refreshIceberg();
                        } else {
                            qIt.remove();
                        }
                    }
                }
            }
            if (orders.isEmpty()) {
                it.remove();
            }
        }
    }

    public TradeCache getTradeCache() {
        return tradeCache;
    }
}