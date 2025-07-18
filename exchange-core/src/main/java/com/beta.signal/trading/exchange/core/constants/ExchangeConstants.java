package com.beta.signal.trading.exchange.core.constants;

/**
 * ExchangeConstants class contains constants used in the exchange application.
 * These constants include default port numbers, order message indices, order types,
 * and other configuration values.
 */
public class ExchangeConstants {

    public static final int DEFAULT_PORT = 8999;
    public static final int ORDER_ID_INDEX = 0;
    public static final int ORDER_TYPE_INDEX = 1;
    public static final int ORDER_PRICE_INDEX = 2;
    public static final int ORDER_QUANTITY_INDEX = 3;
    public static final int BUY_ORDER_QUEUE_CAPACITY = 512;
    public static final int SELL_ORDER_QUEUE_CAPACITY = 512;
    public static final int PLACE_ORDER = 1;
    public static final int CANCEL_ORDER = 2;
    public static final int INVALID_DEFAULT_ORDER = -1;
    public static final int DISCONNECT = -2;

    public static final String DEFAULT_CLIENT_IDENTIFIER = "-1";
    public static final String ORDER_MESSAGE_SPLITTER = "\\,";
    public static final String LOCALHOST = "localhost";
    public static final String PRINT_OPTIONS = "\nYou are connected to the exchange, input(1/2/3):\n1 : Place Order\n2 : Cancel Order\n3 : Quit\n";
    public static final String DIGIT_REGEX = "[0-9]+";
    public static final String NUMERIC_REGEX = "-?\\d+(\\.\\d+)?";
    public static final String BUY = "BUY";
    public static final String SELL = "SELL";
}
