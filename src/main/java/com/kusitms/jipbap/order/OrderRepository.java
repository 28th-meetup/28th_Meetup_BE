package com.kusitms.jipbap.order;

import com.kusitms.jipbap.food.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository <Order, Long> {
}
