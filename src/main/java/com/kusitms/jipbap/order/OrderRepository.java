package com.kusitms.jipbap.order;

import com.kusitms.jipbap.food.Food;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository <Order, Long> {

    @Query("SELECT o.food, SUM(o.orderCount) as totalSales FROM Order o WHERE o.regionId= :regionId GROUP BY o.food ORDER BY totalSales DESC, MAX(o.createdAt) DESC")
    List<Food> findTop10BestSellingFoodsInRegion(@Param("regionId") Long regionId);

    Optional<List<Order>> findByFood_Store_IdAndStatus(Long storeId, OrderStatus status);

}
