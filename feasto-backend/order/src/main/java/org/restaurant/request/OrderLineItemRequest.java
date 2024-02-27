package org.restaurant.request;

import java.util.List;

public record OrderLineItemRequest(
        String name,
        int price,
        List<FoodAdditiveRequest> foodAdditivesList,
        int quantity
) {
}
