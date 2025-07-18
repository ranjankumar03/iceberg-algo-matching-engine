# Iceberg Algorithmic Trading Exchange

## Overview

This project implements a matching engine for a trading exchange, supporting standard and iceberg limit orders. The engine matches buy and sell orders, manages an order book, and handles trade execution with special logic for iceberg orders.

## Problem

- **Limit Order Matching**: Matches incoming orders against the order book using price-time priority.
- **Iceberg Orders**: Supports iceberg orders, where only a portion of the total quantity is visible in the book at any time.
- **Trade Recording**: All trades are recorded in a trade cache for auditing and reporting.
- **Order Book State Recording**: Order Book state is maintained for each placement at the end.

## Iceberg Order Logic

- **Visible Quantity**: Only a part of the iceberg order is visible in the order book.
- **Hidden Quantity**: The remaining quantity is hidden and revealed as the visible part is filled.
- **Refresh**: When the visible quantity is consumed, it is replenished from the hidden quantity until the order is fully filled.
- **Matching**: For iceberg orders, the visible portion is treated as fully consumed after a trade, even if the trade quantity is less, and the order is refreshed immediately.

For more details, see [Trading Technologies: Iceberg Orders](https://library.tradingtechnologies.com/trade/tto-iceberg-order.html).

## Prerequisites

- **Java 21** (used for this project) or higher installed on your system.
- **Gradle 8.14** (used for this project).
- A terminal or IDE to run the application.

## Setup Instructions

1. Clone the project repository.
2. Navigate to the project directory:
   ```bash
   cd ..\trading-exchange\
   ```
3. Compile the project:
   ```bash
   ./gradlew build --refresh-dependencies
   ```
4. Run the application:
   ```bash
   .\trading-exchange\exchange-matching-engine\build\libs>java -jar exchange-matching-engine-1.0.jar
   .\trading-exchange\exchange-client\build\libs>java -jar exchange-client-1.0.jar
   ```
5Running the test:
   ```bash
   ./gradlew clean test
   ```
   
## Example
```
##########################
Scenario 1: No Trade Executed
##########################
C:\RANJAN KUMAR\RANJAN KUMAR\trading-exchange\exchange-client\build\libs>java -jar exchange-client-1.0.jar
May 04, 2025 5:12:01 PM com.beta.signal.trading.exchange.client.Client main
INFO: Client Connecting to exchange..

You are connected to the exchange, input(1/2/3):
1 : Place Order
2 : Cancel Order
3 : Quit
1
May 04, 2025 5:12:03 PM com.beta.signal.trading.exchange.client.Client handlePlaceOrder
INFO: Order Entry Format: [orderId],[B/S],[price],[quantity], e.g., 10006,B,105,16000
Type 'DONE' to finish or 'DISCONNECT' to exit.
10000,B,98,25500
10005,S,105,20000
10001,S,100,500
10002,S,100,10000
10003,B,99,50000
10004,S,103,100
DONE

C:\RANJAN KUMAR\RANJAN KUMAR\trading-exchange\exchange-matching-engine\build\libs>java -jar exchange-matching-engine-1.0.jar
Waiting on Client to connect and place new Order to be executed on Exchange!!
Starting Thread for Client - view-localhost


Order message from client view-localhost: [Ljava.lang.String;@5e4cdd77
50000 99        | 100 10500
25500 98        | 103 100
                | 105 20000


##########################
Scenario 2: Trade Executed
##########################
C:\RANJAN KUMAR\RANJAN KUMAR\trading-exchange\exchange-client\build\libs>java -jar exchange-client-1.0.jar
May 04, 2025 5:09:19 PM com.beta.signal.trading.exchange.client.Client main
INFO: Client Connecting to exchange..

You are connected to the exchange, input(1/2/3):
1 : Place Order
2 : Cancel Order
3 : Quit
1
May 04, 2025 5:09:20 PM com.beta.signal.trading.exchange.client.Client handlePlaceOrder
INFO: Order Entry Format: [orderId],[B/S],[price],[quantity], e.g., 10006,B,105,16000
Type 'DONE' to finish or 'DISCONNECT' to exit.
10000,B,98,25500
10005,S,105,20000
10001,S,100,500
10002,S,100,10000
10003,B,99,50000
10004,S,103,100
10006,B,105,16000
DONE

C:\RANJAN KUMAR\RANJAN KUMAR\trading-exchange\exchange-matching-engine\build\libs>java -jar exchange-matching-engine-1.0.jar
Waiting on Client to connect and place new Order to be executed on Exchange!!
Starting Thread for Client - view-localhost


Order message from client view-localhost: [Ljava.lang.String;@5e4cdd77
trade 10006,10001,100,500
trade 10006,10002,100,10000
trade 10006,10004,103,100
trade 10006,10005,105,5400
50000 99        | 105 14600
25500 98        |


##########################
Scenario 3: Iceberg Orders
##########################
C:\RANJAN KUMAR\RANJAN KUMAR\trading-exchange\exchange-client\build\libs>java -jar exchange-client-1.0.jar
May 04, 2025 5:21:12 PM com.beta.signal.trading.exchange.client.Client main
INFO: Client Connecting to exchange..
May 04, 2025 5:21:12 PM com.beta.signal.trading.exchange.client.Client printOptions
INFO:
You are connected to the exchange, input(1/2/3):
1 : Place Order
2 : Cancel Order
3 : Quit

1
May 04, 2025 5:21:13 PM com.beta.signal.trading.exchange.client.Client handlePlaceOrder
INFO: Order Entry Format: [orderId],[B/S],[price],[quantity], e.g., 10006,B,105,16000
Type 'DONE' to finish or 'DISCONNECT' to exit.
10000,B,98,25500
10005,S,101,20000
10002,S,100,10000
10001,S,100,7500
10003,B,99,50000
ice1,B,100,100000,10000
DONE

C:\RANJAN KUMAR\RANJAN KUMAR\trading-exchange\exchange-matching-engine\build\libs>java -jar exchange-matching-engine-1.0.jar
Waiting on Client to connect and place new Order to be executed on Exchange!!
Starting Thread for Client - view-localhost


May 04, 2025 5:21:28 PM handler.com.beta.signal.trading.exchange.engine.OrderMessageHandler run
INFO: Order message from client view-localhost: [10000,B,98,25500, 10005,S,101,20000, 10002,S,100,10000, 10001,S,100,7500, 10003,B,99,50000, ice1,B,100,100000,10000]
trade ice1,10002,100,10000
trade ice1,10001,100,7500
10000 100       | 101 20000
50000 99        |
25500 98        |
```

## Project Structure
![Project Structure](/output/project-structure.jpg)

## Console Output
![Project Structure](/output/Screenshot 1.jpg)
## Project Structure
![Project Structure](/output/Screenshot 2.jpg)
## Project Structure
![Project Structure](/output/Screenshot 3.jpg)

## License

This project is licensed under the **beta signal**.