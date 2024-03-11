package org.restaurant.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.restaurant.model.enums.FoodAdditives;
import org.restaurant.model.enums.FoodSize;
import java.util.Map;

@Entity
@Data
@RequiredArgsConstructor
public class MenuItemOrder {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuItemId;

    private String name;

    @Column(length = 1024)
    private String description;

    private boolean available;

    private String category;

    @ElementCollection
    @CollectionTable(name = "food_additive_prices", joinColumns = @JoinColumn(name = "menu_item_order_id"))
    @MapKeyColumn(name = "additive_name")
    @Column(name = "price")
    private Map<String, Double> foodAdditivePrices;

    private String selectedSize;

    private Double selectedPrice;

    private Double generalPrice;

    private int quantity;

    @Column(length = 1024) // Assuming the note can be lengthy
    private String note;
}
