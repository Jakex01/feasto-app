package org.restaurant.request;
import jakarta.validation.constraints.NotBlank;
import org.restaurant.model.LocationEntity;

public record CreateRestaurantRequest(
        @NotBlank(message = "restaurant name can't be blank")
        String name,
        PostLocationRequest address,
        @NotBlank(message = "description can't be blank")
        String description,
        @NotBlank(message = "phone number can't be blank")
        String phoneNumber,
        @NotBlank(message = "opening hours can't be blank")
        String openingHours,
        @NotBlank(message = "food type can't be blank")
        String foodType,
        String image
) {
}
