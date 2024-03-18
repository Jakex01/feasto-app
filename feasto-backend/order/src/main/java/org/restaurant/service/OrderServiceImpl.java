package org.restaurant.service;

import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;

import org.restaurant.config.RabbitMQConfig;
import org.restaurant.mapstruct.MapStructMapper;
import org.restaurant.model.OrderEntity;
import org.restaurant.repository.OrderRepository;
import org.restaurant.request.OrderRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Base64;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final PdfServiceImpl pdfService;
    private final RabbitTemplate rabbitTemplate;

    public ResponseEntity<?> postOrder(OrderRequest orderRequest) throws DocumentException, IOException, URISyntaxException {


        OrderEntity order = MapStructMapper.INSTANCE.requestToEntity(orderRequest);

        orderRepository.save(order);

        byte[] pdfContent = pdfService.generatePdf(orderRequest);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, Base64.getEncoder().encodeToString(pdfContent));

        return ResponseEntity.ok(HttpStatus.CREATED);
    }





}
