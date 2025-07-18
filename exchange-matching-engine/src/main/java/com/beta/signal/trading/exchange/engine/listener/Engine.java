package com.beta.signal.trading.exchange.engine.listener;

import com.beta.signal.trading.exchange.core.constants.ExchangeConstants;
import com.beta.signal.trading.exchange.engine.handler.OrderMessageHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Engine is the main class that starts the server and listens for incoming order messages from clients.
 * It creates a new thread for each client connection to handle order messages concurrently.
 */
public class Engine {

    private static final Logger LOGGER = Logger.getLogger(Engine.class.getName());

    static String clientIdentifier = ExchangeConstants.DEFAULT_CLIENT_IDENTIFIER;
    private final ServerSocket serverSocket;
    private final ExecutorService threadExecutor;
    private boolean testMode = false;

    public Engine() throws IOException {
        this(new ServerSocket(ExchangeConstants.DEFAULT_PORT), Executors.newCachedThreadPool());
    }

    public Engine(ServerSocket serverSocket, ExecutorService threadExecutor) {
        this.serverSocket = serverSocket;
        this.threadExecutor = threadExecutor;
    }

    // Setter for testMode
    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }

    public void start() throws IOException {
        Socket connection;
        try {
            LOGGER.log(Level.INFO,"Waiting on Client to connect and place new Order to be executed on Exchange!!");
            while (true) {
                connection = serverSocket.accept();
                clientIdentifier = ((InetSocketAddress) connection.getRemoteSocketAddress()).getHostName();
                LOGGER.log(Level.INFO,"Starting Thread for Client - " + clientIdentifier + "\n\n");
                OrderMessageHandler orderMessageHandler = new OrderMessageHandler(connection, clientIdentifier);
                threadExecutor.execute(orderMessageHandler);

                if (testMode) {
                    break; // Exit loop after one connection in test mode
                }
            }
        } catch (IOException exception) {
            LOGGER.log(Level.SEVERE,"OrderMessageListener encountered error while listening for order message {}" + exception.getMessage());
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
            threadExecutor.shutdownNow();
        }
    }

    public static void main(String[] args) {
        try {
            Engine engine = new Engine();
            engine.start();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE,"Failed to start engine: " + e.getMessage());
        }
    }
}