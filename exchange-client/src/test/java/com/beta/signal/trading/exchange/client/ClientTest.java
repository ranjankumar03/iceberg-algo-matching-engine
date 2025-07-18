package com.beta.signal.trading.exchange.client;

import com.beta.signal.trading.exchange.core.OrderMessageWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ClientTest {

    @Mock
    private Socket mockSocket;

    @Mock
    private ObjectOutputStream mockObjectOutputStream;

    @Mock
    private ObjectInputStream mockObjectInputStream;

    @Mock
    private Scanner mockScanner;

    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        when(mockSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());
        when(mockSocket.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[0]));
    }

    @Test
    void testGetClientChoice_ValidInput() {
        when(mockScanner.nextLine()).thenReturn("1");
        int choice = Client.getClientChoice(mockScanner);
        assertEquals(1, choice);
    }

    @Test
    void testGetClientChoice_InvalidInput() {
        when(mockScanner.nextLine()).thenReturn("invalid");
        int choice = Client.getClientChoice(mockScanner);
        assertEquals(-1, choice);
    }

    @Test
    void testHandlePlaceOrder_ValidOrder() throws IOException, ClassNotFoundException {
        when(mockObjectInputStream.readObject()).thenReturn("Order placed successfully");
        ByteArrayInputStream input = new ByteArrayInputStream("10006,B,105,16000\nDONE\n".getBytes());
        System.setIn(input);

        Client.handlePlaceOrder(mockObjectOutputStream, mockObjectInputStream);

        verify(mockObjectOutputStream, times(1)).writeObject(any(OrderMessageWrapper.class));
        verify(mockObjectOutputStream, times(1)).flush();
        verify(mockObjectInputStream, times(1)).readObject();
    }

    @Test
    void testHandlePlaceOrder_NoOrders() throws IOException, ClassNotFoundException {
        ByteArrayInputStream input = new ByteArrayInputStream("DONE\n".getBytes());
        System.setIn(input);

        Client.handlePlaceOrder(mockObjectOutputStream, mockObjectInputStream);

        verify(mockObjectOutputStream, never()).writeObject(any(OrderMessageWrapper.class));
        verify(mockObjectOutputStream, never()).flush();
        verify(mockObjectInputStream, never()).readObject();
    }
}