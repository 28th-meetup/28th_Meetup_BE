package com.kusitms.jipbap.food;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodOptionRepository extends JpaRepository<FoodOption, Long> {
    List<FoodOption> findAllByFood(Food food);
}
