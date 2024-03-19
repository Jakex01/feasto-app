package org.restaurant.service;

import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;

import lombok.SneakyThrows;
import org.restaurant.config.RabbitMQConfig;
import org.restaurant.mapstruct.MapStructMapper;
import org.restaurant.model.MessageNotification;
import org.restaurant.model.OrderEntity;
import org.restaurant.repository.OrderRepository;
import org.restaurant.request.OrderRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final PdfServiceImpl pdfService;
    private final RabbitTemplate rabbitTemplate;
    private final WebClient webClient;
    @SneakyThrows
    public ResponseEntity<?> postOrder(OrderRequest orderRequest)  {


        OrderEntity order = MapStructMapper.INSTANCE.requestToEntity(orderRequest);

        orderRepository.save(order);

        SendPdfToNotification(orderRequest);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }
    @SneakyThrows
    public void SendPdfToNotification(OrderRequest orderRequest)  {

        String userEmail = webClient.get()
                .uri("http://localhost:8083/api/auth/user")
                .retrieve()
                .bodyToMono(String.class)
                .block();


        byte[] pdfContent = pdfService.generatePdf(orderRequest);

        MessageNotification messageNotification = MessageNotification.builder()
                .email(userEmail)
                .pdfContent(Base64.getEncoder().encodeToString(pdfContent))
                .build();

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, messageNotification);
    }





}
