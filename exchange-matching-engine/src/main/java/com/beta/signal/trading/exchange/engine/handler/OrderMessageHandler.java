package com.beta.signal.trading.exchange.engine.handler;

import com.beta.signal.trading.exchange.core.OrderMessageWrapper;
import com.beta.signal.trading.exchange.core.constants.ExchangeConstants;
import com.beta.signal.trading.exchange.core.enums.OrderType;
import com.beta.signal.trading.exchange.core.exception.ExchangeException;
import com.beta.signal.trading.exchange.core.pojo.Order;
import com.beta.signal.trading.exchange.core.validator.OrderValidator;
import com.beta.signal.trading.exchange.engine.matching.strategy.OrderBook;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * OrderMessageHandler is responsible for handling incoming order messages from clients.
 * It processes the order messages and updates the order book accordingly.
 */
public class OrderMessageHandler implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(OrderMessageHandler.class.getName());

    private final Socket connection;
    private final String clientIdentifier;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private Object orderMessageWrapper;
    private OrderBook orderBook = new OrderBook();
    private OrderValidator<Order> orderValidator = new OrderValidator<>();

    public OrderMessageHandler(Socket connection, String clientIdentifier) {
        this.connection = connection;
        this.clientIdentifier = clientIdentifier;
    }

    public void run() {
        try {
            objectInputStream = new ObjectInputStream(connection.getInputStream());
            objectOutputStream = new ObjectOutputStream(connection.getOutputStream());

            while (true) {
                orderMessageWrapper = objectInputStream.readObject();
                if (orderMessageWrapper instanceof OrderMessageWrapper) {
                    LOGGER.log(Level.INFO, "Order message from client %s: %s".formatted(clientIdentifier, Arrays.toString(((OrderMessageWrapper) orderMessageWrapper).getRawMessage())));
                    String[] reader = ((OrderMessageWrapper) orderMessageWrapper).getRawMessage();

                    for (String line : reader) {
                        String[] parts = line.split(ExchangeConstants.ORDER_MESSAGE_SPLITTER);
                        if (parts.length < 4) continue; // skip malformed lines

                        String id = parts[ExchangeConstants.ORDER_ID_INDEX];

                        OrderType side = parts[ExchangeConstants.ORDER_TYPE_INDEX].equals("B") ? OrderType.BUY : OrderType.SELL;
                        int price = Integer.parseInt(parts[ExchangeConstants.ORDER_PRICE_INDEX]);
                        int volume = Integer.parseInt(parts[ExchangeConstants.ORDER_QUANTITY_INDEX]);

                        Order order;
                        if (parts.length == 5) {
                            int peak = Integer.parseInt(parts[4]);
                            order = new Order(id, side, price, volume, peak, true);
                        } else {
                            order = new Order(id, side, price, volume);
                        }

                        try {
                            /**
                             * Validate the order
                             * The validate method will throw an exception if the order is invalid
                             */
                            orderValidator.validate(order);
                            /**
                             * Process the order and update the order book
                             * The process method will handle both regular and iceberg orders
                             */
                            orderBook.process(order);
                        } catch (ExchangeException e) {
                            throw new ExchangeException("Validation failed for order: " + e.getMessage());
                        }
                    }
                    orderBook.printTrades();
                    orderBook.printBook();
                }
            }
        } catch (ClassNotFoundException | IOException | ExchangeException e) {
            throw new RuntimeException(e);
        }
    }
}