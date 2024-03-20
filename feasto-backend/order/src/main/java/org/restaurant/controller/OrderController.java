package org.restaurant.controller;

import com.itextpdf.text.DocumentException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.restaurant.request.OrderRequest;
import org.restaurant.service.OrderService;
import org.restaurant.service.PdfService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/order",
produces = MediaType.APPLICATION_JSON_VALUE,
consumes = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    private final OrderService orderService;


    @PostMapping()
    public ResponseEntity<?> postOrder(@RequestBody OrderRequest orderRequest, @RequestHeader(value = "Authorization") String token) throws DocumentException, IOException, URISyntaxException {
        return orderService.postOrder(orderRequest, token);
    }





}
