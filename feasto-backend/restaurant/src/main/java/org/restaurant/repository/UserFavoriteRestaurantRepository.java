package org.restaurant.repository;

import org.restaurant.model.FavoriteRestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFavoriteRestaurantRepository extends JpaRepository<FavoriteRestaurantEntity, Long> {
}
