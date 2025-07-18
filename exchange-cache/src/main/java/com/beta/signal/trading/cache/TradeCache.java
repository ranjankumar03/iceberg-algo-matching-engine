package com.beta.signal.trading.cache;

import com.beta.signal.trading.exchange.core.pojo.Trade;

import java.util.ArrayList;
import java.util.List;

/**
 * TradeCache is a simple in-memory cache for storing trade data.
 * It allows adding trades, retrieving all trades, and clearing the cache.
 */
public class TradeCache {

    private final List<Trade> trades = new ArrayList<>();

    public void addTrade(Trade trade) {
        trades.add(trade);
    }

    public List<Trade> getTrades() {
        return new ArrayList<>(trades); // Return a copy to prevent external modification
    }

    public void clearTrades() {
        trades.clear();
    }
}