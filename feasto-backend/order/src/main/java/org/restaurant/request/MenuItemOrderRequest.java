package org.restaurant.request;

import java.util.Map;

public record MenuItemOrderRequest(
     String name,

     String description,

     boolean available,

     String category,
     Map<String, Double> foodAdditivePrices,
     Double selectedPrice,
     Double selectedSize,
     String note
) {
}
