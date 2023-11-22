package com.kusitms.jipbap.order.repository;

import com.kusitms.jipbap.order.model.entity.OrderStatus;
import com.kusitms.jipbap.order.model.entity.Order;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository <Order, Long> {

    Optional<List<Order>> findByStore_IdAndStatus(Long storeId, OrderStatus status, Sort sort);

    Optional<List<Order>> findByUser_Id(Long userId);

    @Query("SELECT COUNT(o) FROM Order o WHERE DATE(o.createdAt) = :date")
    Long countOrdersByDate(@Param("date") LocalDate date);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.status = :status")
    Long countOrdersByStatus(@Param("status") OrderStatus status);

}
