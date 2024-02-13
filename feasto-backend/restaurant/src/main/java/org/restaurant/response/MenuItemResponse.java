package org.restaurant.response;

import org.restaurant.model.enums.FoodAdditive;
import org.restaurant.model.enums.FoodCategory;

import java.util.List;
import java.util.Map;

public record MenuItemResponse(
        Long menuItemId,
        String name,
        String description,
        boolean available,
        FoodCategory category
) {
}
