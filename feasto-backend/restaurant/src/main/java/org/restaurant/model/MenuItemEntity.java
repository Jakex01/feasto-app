package org.restaurant.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.restaurant.model.enums.FoodAdditive;
import org.restaurant.model.enums.FoodCategory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Data
@RequiredArgsConstructor
public class MenuItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuItemId;

    private String name;
    private String description;
    private boolean available;
    @Enumerated(EnumType.STRING)
    private FoodCategory category;

    @ElementCollection(targetClass = FoodAdditive.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "menu_item_food_additives", joinColumns = @JoinColumn(name = "menu_item_id"))
    @Column(name = "food_additive")
    List<FoodAdditive> foodAdditiveList;

    @ElementCollection
    @CollectionTable(name = "menu_item_sizes", joinColumns = @JoinColumn(name = "menu_item_id"))
    @MapKeyColumn(name = "size")
    @Column(name = "price")
    private Map<String, Double> sizesWithPrices = new HashMap<>();


}
