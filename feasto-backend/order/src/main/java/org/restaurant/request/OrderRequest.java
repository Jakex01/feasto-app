package org.restaurant.request;


import org.restaurant.model.MenuItemOrder;

import java.util.List;

public record OrderRequest(
        List<MenuItemOrderRequest> items,
        Double totalPrice,
        Long restaurantId,
        String restaurantName,
        Double tip,
        Double deliveryFee
) {
}
