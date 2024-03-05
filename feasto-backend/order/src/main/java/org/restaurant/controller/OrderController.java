package org.restaurant.controller;

import lombok.RequiredArgsConstructor;
import org.restaurant.request.OrderRequest;
import org.restaurant.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> postOrder(@RequestBody OrderRequest orderRequest, Authentication principal){
        return orderService.postOrder(orderRequest, principal);
    }

}
