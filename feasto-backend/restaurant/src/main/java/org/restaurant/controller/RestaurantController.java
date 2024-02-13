package org.restaurant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.restaurant.mapstruct.dto.RestaurantEntityDTO;
import org.restaurant.request.CreateRestaurantRequest;
import org.restaurant.response.RestaurantResponse;
import org.restaurant.service.RestaurantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
@RequestMapping("/api/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<?> addRestaurant(@RequestBody CreateRestaurantRequest createRestaurantRequest){
       return restaurantService.createRestaurant(createRestaurantRequest);
    }
    @GetMapping
    public ResponseEntity<List<RestaurantEntityDTO>> getAllRestaurants(){
        return restaurantService.getAllRestaurants();
    }
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> getRestaurantById(@PathVariable("id") @Valid Long restaurantId){
        return restaurantService.findRestaurantById(restaurantId);
    }
    @GetMapping("/filter")
    public ResponseEntity<List<RestaurantEntityDTO>> getRestaurantsByFilter(
            @RequestParam(required = false) String foodType,
            @RequestParam(required = false) Double rating,
            @RequestParam(required = false) Integer priceRange
    ){
     return restaurantService.getRestaurantsFiltered(foodType, rating, priceRange);
    }
}
