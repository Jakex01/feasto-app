package org.restaurant.response;

public record RatingResponse(
        Long id,
        double rating,
        String review,
        Long restaurantId
) {
}
