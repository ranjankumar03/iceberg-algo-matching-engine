package com.beta.signal.trading.exchange.engine.matching.strategy;

import com.beta.signal.trading.exchange.core.enums.OrderType;
import com.beta.signal.trading.exchange.core.pojo.Order;

import java.util.Queue;
import java.util.TreeMap;

public interface OrderMatchingStrategy {

    void match(Order incoming, OrderType oppSide, TreeMap<Integer, Queue<Order>> book);
}
