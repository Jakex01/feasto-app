package org.restaurant.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.restaurant.model.FavoriteRestaurantEntity;
import org.restaurant.repository.UserFavoriteRestaurantRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserFavoriteRestaurantService {

    private final UserFavoriteRestaurantRepository userFavoriteRestaurantRepository;
    private final WebClient webClient;


    public ResponseEntity<?> addFavoriteRestaurant(Long restaurantId)
    {
        Long userId = webClient.get()
                .uri("http://localhost:8083/api/auth/user")
                .retrieve()
                .bodyToMono(Long.class)
                .block();
        FavoriteRestaurantEntity favoriteRestaurantEntity = FavoriteRestaurantEntity
                .builder()
                .restaurantId(restaurantId)
                .userId(userId)
                .build();

        if(userId!=null){
            userFavoriteRestaurantRepository.saveAndFlush(favoriteRestaurantEntity);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }else {
            throw new IllegalArgumentException("UserId can't be null");
        }
    }

    public ResponseEntity<?> deleteFavoriteRestaurant(Long restaurantId) {
        userFavoriteRestaurantRepository.findById(restaurantId)
                .ifPresent(userFavoriteRestaurantRepository::delete);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);

    }
    public ResponseEntity<List<Long>> getFavourites(){

        Long userId = webClient.get()
                .uri("http://localhost:8083/api/auth/user")
                .retrieve()
                .bodyToMono(Long.class)
                .block();
        if(userId!=null) {
            List<Long> restaurantIds = userFavoriteRestaurantRepository
                    .findAllByUserId(userId)
                    .stream()
                    .map(FavoriteRestaurantEntity::getRestaurantId)
                    .toList();
            return ResponseEntity.ok(restaurantIds);
        }else{
            throw new IllegalArgumentException("UserId can't be null");
        }
    }

}
