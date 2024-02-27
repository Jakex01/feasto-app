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
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public ResponseEntity<?> postOrder(OrderRequest orderRequest) {

        Long userId = webClient.get()
                .uri("http://localhost:8082/api/auth")
                .retrieve()
                .bodyToMono(Long.class)
                .block();

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
