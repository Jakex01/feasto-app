package org.restaurant.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.restaurant.model.enums.FoodAdditives;

import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
public class OrderLineItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int price;
    List<FoodAdditives> foodAdditivesList;
    private int quantity;
}
