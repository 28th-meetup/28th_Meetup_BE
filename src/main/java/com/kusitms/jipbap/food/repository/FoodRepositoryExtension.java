package com.kusitms.jipbap.food.repository;

import com.kusitms.jipbap.food.model.entity.Food;
import com.kusitms.jipbap.user.model.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FoodRepositoryExtension {
    List<Food> searchByNameOrderBySort(User user, Pageable pageable, String keyword, String standard, String order);
}
