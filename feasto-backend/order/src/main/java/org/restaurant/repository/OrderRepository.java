package org.restaurant.repository;

import org.restaurant.model.OrderEntity;
import org.restaurant.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query("SELECT COUNT(o) FROM OrderEntity o WHERE o.orderStatus IN :statuses")
    int countBySpecificStatuses(@Param("statuses") List<OrderStatus> statuses);
}
