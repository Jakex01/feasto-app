package org.restaurant.util;

import org.restaurant.model.RatingEntity;
import org.restaurant.model.RestaurantEntity;

import java.util.List;

public class RestaurantUtils {

    public static double calculateAverageRating(List<RatingEntity> ratings) {
        return ratings.stream()
                .mapToDouble(RatingEntity::getRating)
                .average()
                .orElse(4.0);
    }
    public static double calculateAveragePrice(RestaurantEntity restaurant) {
        return restaurant
                .getMenuItems()
                .stream()
                .flatMap(item -> item.getSizesWithPrices().values().stream())
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }
}
