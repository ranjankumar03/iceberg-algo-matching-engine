package com.beta.signal.trading.exchange.engine.matching.strategy;

import com.beta.signal.trading.cache.TradeCache;
import com.beta.signal.trading.exchange.core.enums.OrderType;
import com.beta.signal.trading.exchange.core.pojo.Order;
import com.beta.signal.trading.exchange.core.pojo.Trade;

import java.util.*;

public class OrderBook {
    private final TreeMap<Integer, Queue<Order>> buyOrders = new TreeMap<>(Collections.reverseOrder());
    private final TreeMap<Integer, Queue<Order>> sellOrders = new TreeMap<>();
    private final LimitOrderMatchingStrategy matchingStrategy = new LimitOrderMatchingStrategy();
    private final TradeCache tradeCache = matchingStrategy.getTradeCache();

    /**
     * Processes an incoming order by matching it with the opposite side of the order book
     * and adding it to the appropriate side if not fully matched.
     *
     * @param incoming The incoming order to process.
     */
    public void process(Order incoming) {
        if (incoming.isBuy()) {
            matchingStrategy.match(incoming, OrderType.SELL, sellOrders);
            if (!incoming.isDepleted()) add(buyOrders, incoming);
        } else {
            matchingStrategy.match(incoming, OrderType.BUY, buyOrders);
            if (!incoming.isDepleted()) add(sellOrders, incoming);
        }
    }

    /**
     * Adds an order to the specified side of the order book.
     *
     * @param book The order book side (buy or sell).
     * @param order The order to add.
     */
    private void add(TreeMap<Integer, Queue<Order>> book, Order order) {
        book.computeIfAbsent(order.price, k -> new LinkedList<>()).add(order);
    }

    /**
     * Prints all trades that occurred during the current processing cycle.
     * Clears the trade cache after printing.
     */
    public void printTrades() {
        for (Trade t : tradeCache.getTrades()) {
            System.out.println(t);
        }
        tradeCache.clearTrades(); // Reset after printing
    }

    /**
     * Prints the current state of the order book to the console.
     * Displays buy orders on the left and sell orders on the right.
     */
    public void printBook() {
        NavigableSet<Integer> allPrices = new TreeSet<>(buyOrders.keySet());
        allPrices.addAll(sellOrders.keySet());

        List<Integer> buyPrices = new ArrayList<>(buyOrders.keySet());
        List<Integer> sellPrices = new ArrayList<>(sellOrders.keySet());

        int maxDepth = Math.max(buyPrices.size(), sellPrices.size());
        for (int i = 0; i < maxDepth; i++) {
            String left = i < buyPrices.size() ? bookLevelToStr(buyOrders.get(buyPrices.get(i)), buyPrices.get(i), false) : "";
            String right = i < sellPrices.size() ? bookLevelToStr(sellOrders.get(sellPrices.get(i)), sellPrices.get(i), true) : "";
            System.out.printf("%-15s | %s%n", left, right);
        }
        // Reset after client 'DONE' for the current set of orders posting
        buyOrders.clear();
        sellOrders.clear();
    }

    /**
     * Helper method to format the order book level for printing.
     *
     * @param orders Queue of orders at the given price level.
     * @param price The price level.
     * @return A formatted string representing the order book level.
     */
    private String bookLevelToStr(Queue<Order> orders, int price, boolean isSellOrder) {
        int total = orders.stream().mapToInt(o -> o.visibleQuantity).sum();
        return isSellOrder ? String.format("%d %d", price, total) : String.format("%d %d", total, price);
    }
}