package org.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.restaurant.mapstruct.dto.MapStructMapper;
import org.restaurant.mapstruct.dto.RestaurantEntityDTO;
import org.restaurant.model.RestaurantEntity;
import org.restaurant.repository.RestaurantRepository;
import org.restaurant.request.CreateRestaurantRequest;
import org.restaurant.request.PostLocationRequest;
import org.restaurant.validators.ObjectsValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {
//
//    @Mock
//    private RestaurantRepository restaurantRepository;
//
//    private AutoCloseable autoCloseable;
//
//    private ObjectsValidator<CreateRestaurantRequest> objectsValidator;
//
//    @InjectMocks
//    private RestaurantService restaurantService;
//    @BeforeEach
//    void setUp() {
//        autoCloseable = MockitoAnnotations.openMocks(this);
//        restaurantService = new RestaurantService(restaurantRepository, objectsValidator);
//    }
//
//    @AfterEach
//    void tearDown() throws Exception {
//        autoCloseable.close();
//    }
//
//    @Test
//    void createRestaurant() {
//        PostLocationRequest address = new PostLocationRequest(
//                "City",
//                "Street",
//                "123",
//                "Country"
//        );
//
//        CreateRestaurantRequest request = new CreateRestaurantRequest(
//                "Test Restaurant",
//                address,
//                "Test Description",
//                "1234567890",
//                "10:00-22:00",
//                "Italian",
//                "image.jpg"
//        );
//
//        // Optionally mock the behavior of objectsValidator if the service method uses it
//        when(objectsValidator.validate(any())).thenReturn(true); // Assuming validate returns a boolean or similar
//
//        ResponseEntity<?> responseEntity = restaurantService.createRestaurant(request);
//
//        verify(restaurantRepository).save(any(RestaurantEntity.class));
//        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//    }
//
//    @Test
//    void getAllRestaurants() {
//        List<RestaurantEntity> restaurants = List.of(new RestaurantEntity());
//        when(restaurantRepository.findAll()).thenReturn(restaurants);
//
//        List<RestaurantEntityDTO> expectedDTOs = List.of(new RestaurantEntityDTO());
//        when(MapStructMapper.INSTANCE.restaurantToRestaurantDto(any(RestaurantEntity.class))).thenReturn(expectedDTOs.get(0));
//
//        ResponseEntity<List<RestaurantEntityDTO>> response = restaurantService.getAllRestaurants();
//
//
//        verify(restaurantRepository).findAll();
//
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(expectedDTOs.size(), Objects.requireNonNull(response.getBody()).size());
//    }
//
//    @Test
//    void findRestaurantById() {
//    }
//
//    @Test
//    void getRestaurantsFiltered() {
//    }
}