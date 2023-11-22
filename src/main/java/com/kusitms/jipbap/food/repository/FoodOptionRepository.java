package com.kusitms.jipbap.food.repository;

import com.kusitms.jipbap.food.model.entity.Food;
import com.kusitms.jipbap.food.model.entity.FoodOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodOptionRepository extends JpaRepository<FoodOption, Long> {
    List<FoodOption> findAllByFood(Food food);
}
