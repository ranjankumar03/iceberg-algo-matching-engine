package com.beta.signal.trading.exchange.core;

import java.io.Serializable;

/**
 * This class is used to wrap the order message received from the exchange.
 * It contains the raw message and its length.
 */
public class OrderMessageWrapper implements Serializable {

    private String[] rawMessage;
    private int messageLength;

    public OrderMessageWrapper(String[] message) {
        this.rawMessage = message;
    }

    public String[] getRawMessage() {
        return rawMessage;
    }

    public void setRawMessage(String[] rawMessage) {
        this.rawMessage = rawMessage;
    }

    public int getMessageLength() {
        return messageLength;
    }

    public void setMessageLength(int messageLength) {
        this.messageLength = messageLength;
    }

    public void determineLength() {
        if (rawMessage != null) {
            messageLength = rawMessage.length;
        } else {
            messageLength = 0;
        }
    }

    @Override
    public String toString() {
        if (rawMessage == null || rawMessage.length == 0) {
            return "Original Order: No data available\n";
        }

        StringBuilder message = new StringBuilder("Original Order:\n");
        for (String line : rawMessage) {
            if (line != null && !line.isEmpty()) {
                message.append(line).append("\n");
            } else {
                message.append("Empty or null line\n");
            }
        }
        return message.toString();
    }
}

