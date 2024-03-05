package org.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.restaurant.repository.OrderRepository;
import org.restaurant.request.OrderRequest;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public ResponseEntity<?> postOrder(OrderRequest orderRequest, Authentication authentication) {



        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
