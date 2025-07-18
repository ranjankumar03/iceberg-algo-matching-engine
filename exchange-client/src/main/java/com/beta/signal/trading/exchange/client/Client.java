package com.beta.signal.trading.exchange.client;

import com.beta.signal.trading.exchange.core.OrderMessageWrapper;
import com.beta.signal.trading.exchange.core.utils.ExchangeUtil;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.beta.signal.trading.exchange.core.constants.ExchangeConstants.*;

public class Client {

    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());
    private static final Consumer<String> printOptionConsumer = Client::printOptions;

    public static void main(String[] args) {
        LOGGER.log(Level.INFO, "Client Connecting to exchange..");

        try (Socket connection = new Socket(LOCALHOST, DEFAULT_PORT);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(connection.getOutputStream());
             ObjectInputStream objectInputStream = new ObjectInputStream(connection.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            boolean isRunning = true;
            while (isRunning) {
                printOptionConsumer.accept(PRINT_OPTIONS);
                int clientChoice = getClientChoice(scanner);

                switch (clientChoice) {
                    case PLACE_ORDER:
                        handlePlaceOrder(objectOutputStream, objectInputStream);
                        break;
                    case CANCEL_ORDER:
                        LOGGER.log(Level.INFO, "Order Cancellation is Not Supported in current version!!");
                        break; // Keep the loop running
                    case DISCONNECT:
                        LOGGER.log(Level.INFO, "Disconnecting!!");
                        isRunning = false;
                        break;
                    case INVALID_DEFAULT_ORDER:
                        LOGGER.log(Level.WARNING, "Invalid Entry!!");
                        break; // Keep the loop running
                    default:
                        LOGGER.log(Level.WARNING, "Unknown option. Please try again.");
                        break; // Keep the loop running
                }
            }
        } catch (IOException | ClassNotFoundException exception) {
            LOGGER.log(Level.SEVERE, "Client encountered an issue: {0}", exception.getMessage());
        }
    }

    static int getClientChoice(Scanner scanner) {
        String inputChoice = scanner.nextLine();
        return ExchangeUtil.isDigit(inputChoice) ? Integer.parseInt(inputChoice) : INVALID_DEFAULT_ORDER;
    }

    static void handlePlaceOrder(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        LOGGER.log(Level.INFO, "Order Entry Format: [orderId],[B/S],[price],[quantity], e.g., 10006,B,105,16000\nType 'DONE' to finish or 'DISCONNECT' to exit.");

        List<String> orderLines = readOrderLines();
        if (orderLines.isEmpty()) {
            LOGGER.log(Level.WARNING, "No orders entered. Returning to main menu.");
            return;
        }

        OrderMessageWrapper orderMessageWrapper = new OrderMessageWrapper(orderLines.toArray(new String[0]));
        objectOutputStream.writeObject(orderMessageWrapper);
        objectOutputStream.flush();

        Object responseMessage = objectInputStream.readObject();
        LOGGER.log(Level.INFO, responseMessage.toString());
    }

    private static List<String> readOrderLines() throws IOException {
        List<String> orderLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String orderLine;
            while (true) {
                orderLine = reader.readLine();
                if ("DONE".equalsIgnoreCase(orderLine.trim())) {
                    break;
                }
                if ("DISCONNECT".equalsIgnoreCase(orderLine.trim())) {
                    System.exit(0);
                }
                orderLines.add(orderLine);
            }
        }
        return orderLines;
    }

    private static void printOptions(String str) {
        LOGGER.log(Level.INFO, str);
    }
}