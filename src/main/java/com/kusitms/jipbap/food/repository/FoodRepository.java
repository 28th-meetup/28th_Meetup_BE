package com.kusitms.jipbap.food.repository;

import com.kusitms.jipbap.food.model.entity.Category;
import com.kusitms.jipbap.food.model.entity.Food;
import com.kusitms.jipbap.store.model.entity.Store;
import com.kusitms.jipbap.user.model.entity.GlobalRegion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long>, FoodRepositoryExtension {

    List<Food> findAllByStore(Store store);
    List<Food> findAllByCategory(Category category);
    List<Food> findByStoreGlobalRegionAndCategory(GlobalRegion globalRegion, Category category);
}
