package org.restaurant.util;

import lombok.RequiredArgsConstructor;
import org.restaurant.model.enums.DeliveryOption;
import org.restaurant.model.enums.OrderStatus;
import org.restaurant.repository.OrderRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Component
public class OrderUtil {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public int estimateDeliveryTime(Long restaurantId, String jwtToken, DeliveryOption deliveryOption) {
        try {
            if(deliveryOption == DeliveryOption.DELIVERY){
                List<OrderStatus> activeStatuses = Arrays.asList(OrderStatus.PENDING, OrderStatus.ACCEPTED, OrderStatus.IN_PREPARATION, OrderStatus.OUT_FOR_DELIVERY);
                int activeDeliveries = orderRepository.countBySpecificStatuses(activeStatuses);
                int delayPerOrder = 3;
                int delayDueToActiveDeliveries = activeDeliveries * delayPerOrder;
                String travelTime = webClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .scheme("http")
                                .host("localhost")
                                .port(8762) // do sprawdzenia port
                                .path("/api/google-maps/calculate-travel-time")
                                .queryParam("origin", origin)
                                .queryParam("destination", destination)
                                .build())
                        .header("Authorization", "Bearer " + jwtToken)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();

                int travelTimeMinutes = Integer.parseInt(travelTime.replaceAll("[^0-9]", ""));
                return travelTimeMinutes + delayDueToActiveDeliveries;
            }else{
                List<OrderStatus> activeStatuses = Arrays.asList(OrderStatus.PENDING, OrderStatus.ACCEPTED, OrderStatus.IN_PREPARATION);
                int activeDeliveries = orderRepository.countBySpecificStatuses(activeStatuses);
                int delayPerOrder = 3;
                return activeDeliveries * delayPerOrder;
            }
        } catch (Exception e) {
            System.err.println("Failed to estimate delivery time: " + e.getMessage());
            return -1;  // Symbolizuje błąd w oszacowaniu
        }
    }
}
