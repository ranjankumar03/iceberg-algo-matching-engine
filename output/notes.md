Refer - https://library.tradingtechnologies.com/trade/tto-iceberg-order.html

In this context with given inpit, an iceberg order is a type of order where only a portion of the total quantity (called the visible quantity) is displayed in the order book at any given time. The remaining quantity (called the hidden quantity) is kept hidden and is revealed incrementally as the visible portion is consumed through trades.

Key Points on Iceberg Orders in the Code solution:
Visible and Hidden Quantities:  
visibleQuantity: The portion of the order that is currently available for matching in the order book.
hiddenRemaining: The remaining quantity that is hidden and replenishes the visibleQuantity when it is fully consumed.
Matching Logic:  
When an iceberg order is matched, the visibleQuantity is reduced by the traded quantity.
If the visibleQuantity becomes zero and there is still hiddenRemaining, the iceberg order is "refreshed" by replenishing the visibleQuantity from the hiddenRemaining.
Refreshing Iceberg Orders:  
The refreshIceberg() method is called to replenish the visibleQuantity from the hiddenRemaining.
This ensures that the iceberg order continues to participate in the matching process until the entire order (both visible and hidden portions) is consumed.
Special Handling in Matching:  
For iceberg orders, the full visibleQuantity is treated as consumed after a trade, even if the trade quantity is less than the visibleQuantity. This ensures the order is refreshed immediately for subsequent trades.
Example:  
Suppose an iceberg order ice1 has:
visibleQuantity = 10,000
hiddenRemaining = 90,000
If a trade occurs for 7,500 units, the visibleQuantity is treated as fully consumed, and the order is refreshed with another 10,000 units from the hiddenRemaining, leaving hiddenRemaining = 80,000.
This behavior ensures that iceberg orders can execute large trades without revealing their full size in the order book, thus minimizing market impact.