package org.restaurant.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderNumber;
    private Long userId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderLineItems> orderLineItems;

}
