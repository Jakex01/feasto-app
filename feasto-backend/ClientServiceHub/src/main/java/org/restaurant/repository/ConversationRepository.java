package org.restaurant.repository;

import org.restaurant.model.ConversationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ConversationRepository extends JpaRepository<ConversationEntity, Long> {
    List<ConversationEntity> findByUserId(Long userId);

    @Query("SELECT DISTINCT c.restaurantId FROM ConversationEntity c WHERE c.userId = :userId")
    Set<Long> findDistinctRestaurantIdsByUserId(@Param("userId") Long userId);
}
