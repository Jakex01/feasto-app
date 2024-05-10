package org.restaurant.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.restaurant.model.RestaurantEntity;
import org.restaurant.repository.RestaurantRepository;
import org.restaurant.request.CreateRestaurantRequest;
import org.restaurant.request.PostLocationRequest;
import org.restaurant.response.RestaurantResponse;
import org.restaurant.validators.ObjectsValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

import java.time.LocalDateTime;
import java.util.Optional;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static reactor.core.publisher.Mono.when;


@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

//    @Mock
//    private RestaurantRepository restaurantRepository;
//
//    @Mock
//    private  ObjectsValidator<CreateRestaurantRequest> objectsValidator;
//
//    @InjectMocks
//    private RestaurantService restaurantService;
//
//    @BeforeEach
//     void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void  createRestaurant(){
//        PostLocationRequest postLocationRequest = new PostLocationRequest(
//                "London",
//                "Bakery street",
//                "34",
//                    "England"
//        );
//
//        CreateRestaurantRequest createRestaurantRequest = new CreateRestaurantRequest(
//                "restaurant",
//                postLocationRequest,
//                "italian food",
//                "111222333",
//                "11:00-19:00",
//                "",
//                null
//        );
//
//        ResponseEntity<?> response = restaurantService.createRestaurant(createRestaurantRequest);
//
//        // Assert
//        assertEquals("The response status should be CREATED.", HttpStatus.CREATED,
//                response.getStatusCode());
//
//
//    }
//    @DisplayName("Junit test for findRestaurantById")
//    @Test
//    public void findRestaurantById_success() {
//        // Arrange
//        Long restaurantId = 1L;
//        RestaurantEntity mockRestaurantEntity = new RestaurantEntity();
//        given(restaurantRepository.findById(restaurantId)).willReturn(Optional.of(mockRestaurantEntity));
//
//        // Mock additional dependencies required for the transformation from entity to response, if any
//
//        // Act
//        RestaurantResponse result = restaurantService.findRestaurantById(restaurantId).getBody();
//
//        // Assert
//        assertNotNull(result, "The result should not be null");
//        assertEquals(restaurantId.toString(), result.restaurantId(), "The restaurant ID should match the requested ID");
//        // Add additional assertions as needed to validate the fields are correctly mapped and returned
//    }
}