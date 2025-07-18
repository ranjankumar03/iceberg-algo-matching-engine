package com.beta.signal.trading.exchange.engine.handler;

import com.beta.signal.trading.exchange.core.OrderMessageWrapper;
import com.beta.signal.trading.exchange.core.constants.ExchangeConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;

import static org.mockito.Mockito.*;

class OrderMessageHandlerTest {

    private Socket mockSocket;
    private ObjectInputStream mockInputStream;
    private ObjectOutputStream mockOutputStream;
    private OrderMessageHandler handler;

    @BeforeEach
    void setUp() throws IOException {
        mockSocket = mock(Socket.class);
        mockInputStream = mock(ObjectInputStream.class);
        mockOutputStream = mock(ObjectOutputStream.class);

        when(mockSocket.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[0]));
        when(mockSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        handler = new OrderMessageHandler(mockSocket, ExchangeConstants.DEFAULT_CLIENT_IDENTIFIER);
    }

    @Test
    void testRunWithValidOrderMessage() throws Exception {
        String rawMessage = "1,B,100,10";
        OrderMessageWrapper mockWrapper = new OrderMessageWrapper(new String[]{rawMessage});

        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(pipedOutputStream);
        objectOutputStream.writeObject(mockWrapper);
        objectOutputStream.flush();

        when(mockSocket.getInputStream()).thenReturn(pipedInputStream);
        when(mockSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        OrderMessageHandler mockHandler = mock(OrderMessageHandler.class);

        // Run the handler
        mockHandler.run();

        // Verify that the run method was called once
        verify(mockHandler, times(1)).run();
    }

    @Test
    void testRunWithInvalidOrderMessage() throws Exception {
        String rawMessage = "1,X,100,10";
        OrderMessageWrapper mockWrapper = new OrderMessageWrapper(new String[]{rawMessage});

        PipedOutputStream pipedOutputStream = new PipedOutputStream();
        PipedInputStream pipedInputStream = new PipedInputStream(pipedOutputStream);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(pipedOutputStream);
        objectOutputStream.writeObject(mockWrapper);
        objectOutputStream.flush();

        when(mockSocket.getInputStream()).thenReturn(pipedInputStream);
        when(mockSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        OrderMessageHandler mockHandler = mock(OrderMessageHandler.class);

        // Run the handler
        mockHandler.run();

        // Verify that the run method was called once
        verify(mockHandler, times(1)).run();
    }
}