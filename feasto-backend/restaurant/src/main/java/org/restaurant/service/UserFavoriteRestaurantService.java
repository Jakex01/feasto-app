package org.restaurant.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.restaurant.model.FavoriteRestaurantEntity;
import org.restaurant.repository.UserFavoriteRestaurantRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserFavoriteRestaurantService {

    private final UserFavoriteRestaurantRepository userFavoriteRestaurantRepository;

    @RabbitListener(queues = "userQueue")
    public ResponseEntity<?> addFavoriteRestaurant(Long restaurantId) {
        FavoriteRestaurantEntity favoriteRestaurantEntity = new FavoriteRestaurantEntity();
        favoriteRestaurantEntity.setRestaurantId(restaurantId);
        favoriteRestaurantEntity.setUserId(1L);
           userFavoriteRestaurantRepository.save(favoriteRestaurantEntity);

           return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @RabbitListener(queues = "userQueue")
    public Long processUserId(Long userId) {
        return userId;
    }

}
