package com.kusitms.jipbap.order;

import com.kusitms.jipbap.food.Food;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository <Order, Long> {

    Optional<List<Order>> findByStore_IdAndStatus(Long storeId, OrderStatus status, Sort sort);

    Optional<List<Order>> findByUser_Id(Long userId);

}
