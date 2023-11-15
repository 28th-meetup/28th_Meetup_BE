package com.kusitms.jipbap.order;

import com.kusitms.jipbap.food.Food;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

public interface OrderRepository extends JpaRepository <Order, Long> {

    @Query("SELECT o.food, SUM(o.orderCount) as totalSales FROM Order o WHERE o.regionId= :regionId GROUP BY o.food ORDER BY totalSales DESC, MAX(o.createdAt) DESC")
    List<Food> findTop10BestSellingFoodsInRegion(@Param("regionId") Long regionId);

}
