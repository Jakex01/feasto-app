package org.restaurant.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.restaurant.config.RabbitMQConfig;
import org.restaurant.mapstruct.MapStructMapper;
import org.restaurant.model.MessageNotification;
import org.restaurant.model.OrderEntity;
import org.restaurant.repository.OrderRepository;
import org.restaurant.request.OrderRequest;
import org.restaurant.request.OrderUpdateRequest;
import org.restaurant.util.JwtUtil;
import org.restaurant.util.OrderUtil;
import org.restaurant.validators.ObjectsValidator;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;



@Service
@CrossOrigin
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final PdfServiceImpl pdfService;
    private final RabbitTemplate rabbitTemplate;
    private final WebClient webClient;
    private final ObjectsValidator<OrderRequest> orderRequestObjectsValidator;
    private final OrderUtil orderUtil;

    @SneakyThrows
    public ResponseEntity<?> postOrder(OrderRequest orderRequest, String token)  {
        orderRequestObjectsValidator.validate(orderRequest);
        String jwtToken = JwtUtil.extractToken(token);
        OrderEntity order = MapStructMapper.INSTANCE.requestToEntity(orderRequest);
        orderRepository.save(order);
        SendPdfToNotification(orderRequest, token);
       int deliveryTime = orderUtil.estimateDeliveryTime(order.getRestaurantId(),jwtToken, order.getDeliveryOption());
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> updateOrder(OrderUpdateRequest orderUpdateRequest) {
        OrderEntity order = orderRepository.findByRestaurantId(orderUpdateRequest.restaurantId());
        order.setOrderStatus(orderUpdateRequest.orderStatus());
        orderRepository.save(order);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }
    @SneakyThrows
    public void SendPdfToNotification(OrderRequest orderRequest, String token)  {

        String jwtToken = JwtUtil.extractToken(token);

        String userEmail = webClient.get()
                .uri("http://localhost:8762/api/auth/user")
                .header("Authorization", "Bearer " + jwtToken)
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
