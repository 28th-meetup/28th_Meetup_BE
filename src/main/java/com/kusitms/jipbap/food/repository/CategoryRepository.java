package com.kusitms.jipbap.food.repository;

import com.kusitms.jipbap.food.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
