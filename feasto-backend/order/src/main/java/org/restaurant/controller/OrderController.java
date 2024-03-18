package org.restaurant.controller;

import com.itextpdf.text.DocumentException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.restaurant.request.OrderRequest;
import org.restaurant.service.OrderService;
import org.restaurant.service.PdfService;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/order")
public class OrderController {

    private final OrderService orderService;


    @PostMapping
    public ResponseEntity<?> postOrder(@RequestBody OrderRequest orderRequest) throws DocumentException, IOException, URISyntaxException {
        return orderService.postOrder(orderRequest);
    }



}
