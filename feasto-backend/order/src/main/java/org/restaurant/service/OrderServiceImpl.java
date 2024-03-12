package org.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.restaurant.repository.OrderRepository;
import org.restaurant.request.OrderRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public ResponseEntity<?> postOrder(OrderRequest orderRequest) {


        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
