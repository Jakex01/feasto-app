package org.restaurant.request;


import java.util.List;

public record OrderRequest(
        List<OrderLineItemRequest> orderLineItems
) {
}
