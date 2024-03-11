package org.restaurant.service;

import org.restaurant.request.OrderRequest;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    ResponseEntity<?> postOrder(OrderRequest orderRequest);
}
