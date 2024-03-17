package org.restaurant.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.restaurant.service.UserFavoriteRestaurantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/restaurant-like")
public class UserFavoriteRestaurantsController {

    private final UserFavoriteRestaurantService userFavoriteRestaurantService;

    @PostMapping()
    public ResponseEntity<?> addFavoriteRestaurant(@RequestParam @NonNull Long restaurantId){
    return userFavoriteRestaurantService.addFavoriteRestaurant(restaurantId);
    }
    @DeleteMapping()
    public ResponseEntity<?> deleteFavoriteRestaurant(@RequestParam @NonNull Long restaurantId){
        return userFavoriteRestaurantService.deleteFavoriteRestaurant(restaurantId);
    }
    @GetMapping
    public ResponseEntity<?> getFavourites(){
        return userFavoriteRestaurantService.getFavourites();
    }

}
