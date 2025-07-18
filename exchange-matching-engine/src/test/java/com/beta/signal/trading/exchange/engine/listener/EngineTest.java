package com.beta.signal.trading.exchange.engine.listener;

import com.beta.signal.trading.exchange.core.constants.ExchangeConstants;
import com.beta.signal.trading.exchange.engine.handler.OrderMessageHandler;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import static org.mockito.Mockito.*;

class EngineTest {

    @Test
    void testEngineStartsAndHandlesClientConnections() throws IOException, InterruptedException {
        ServerSocket mockServerSocket = mock(ServerSocket.class);
        Socket mockSocket = mock(Socket.class);
        ExecutorService mockExecutorService = mock(ExecutorService.class);

        when(mockServerSocket.accept()).thenReturn(mockSocket).thenThrow(new IOException("Stop test loop"));
        when(mockSocket.getRemoteSocketAddress()).thenReturn(new InetSocketAddress("localhost", ExchangeConstants.DEFAULT_PORT));

        ArgumentCaptor<OrderMessageHandler> orderMessageHandlerCaptor = ArgumentCaptor.forClass(OrderMessageHandler.class);

        // Create Engine instance with mocked dependencies
        Engine engine = new Engine(mockServerSocket, mockExecutorService);
        engine.setTestMode(true);

        // Run the engine in a separate thread
        Thread engineThread = new Thread(() -> {
            try {
                engine.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        engineThread.start();

        // Give the engine some time to start and process
        Thread.sleep(100);

        verify(mockServerSocket, times(1)).accept();
        verify(mockExecutorService, times(1)).execute(orderMessageHandlerCaptor.capture());

        // Interrupt the engine thread to stop it
        engineThread.interrupt();
    }
}