package org.restaurant.service;

import io.micrometer.observation.annotation.Observed;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.restaurant.mapstruct.dto.MapStructMapper;
import org.restaurant.mapstruct.dto.RestaurantEntityDTO;
import org.restaurant.model.LocationEntity;
import org.restaurant.model.RestaurantEntity;

import org.restaurant.repository.RestaurantRepository;
import org.restaurant.request.CreateRestaurantRequest;
import org.restaurant.response.RestaurantResponse;
import org.restaurant.specification.RestaurantSpecification;
import org.restaurant.validators.ObjectsValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.Principal;
import java.util.List;

import static org.restaurant.util.RestaurantUtils.*;


@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final ObjectsValidator<CreateRestaurantRequest> objectsValidator;
    @Observed(name = "create.restaurant")
    public ResponseEntity<?> createRestaurant(CreateRestaurantRequest createRestaurantRequest) {
        objectsValidator.validate(createRestaurantRequest);

        LocationEntity locationEntity = MapStructMapper
                .INSTANCE.locationRequestToLocationEntity(createRestaurantRequest.address());

        RestaurantEntity restaurantEntity = MapStructMapper
                .INSTANCE.requestToEntity(createRestaurantRequest);

        locationEntity.setRestaurant(restaurantEntity);
        restaurantEntity.setLocations(List.of(locationEntity));

        restaurantRepository.save(restaurantEntity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @Observed(name = "get.restaurants")
    public ResponseEntity<List<RestaurantEntityDTO>> getAllRestaurants(){

                List<RestaurantEntityDTO> restaurantDTOs = restaurantRepository
                        .findAll()
                        .stream()
                        .map(MapStructMapper.INSTANCE::restaurantToRestaurantDto)
                        .toList();
        return ResponseEntity.ok(restaurantDTOs);
    }
    @Observed(name = "getById.restaurant")
    public ResponseEntity<RestaurantResponse> findRestaurantById(Long restaurantId) {

        return restaurantRepository.findById(restaurantId)
                .map(MapStructMapper.INSTANCE::restaurantEntityToRestaurantResponse)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found with id: " + restaurantId));
    }
    public ResponseEntity<List<RestaurantEntityDTO>> getRestaurantsFiltered(String foodType, Double rating, Integer priceRange){
        List<RestaurantEntityDTO> restaurantEntityDTOList = restaurantRepository
                .findAll(RestaurantSpecification.columnEqual(foodType, rating, priceRange))
                .stream()
                .map(MapStructMapper.INSTANCE::restaurantToRestaurantDto)
                .toList();

        return ResponseEntity.ok(restaurantEntityDTOList);
    }
    public void updateAverageRatingsAndPricing(){
        restaurantRepository
                .findAll()
                .forEach(restaurant -> {
            restaurant.setRating(calculateAverageRating(restaurant.getRatings()));
            restaurant.setPrices((int)calculateAveragePrice(restaurant));
            restaurant.setCommentsCount(calculateCountOfRatings(restaurant.getRatings()));
            restaurantRepository.save(restaurant);
            restaurantRepository.flush();
        });
    }
}
