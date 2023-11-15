package com.kusitms.jipbap.food;

import com.kusitms.jipbap.store.Store;
import com.kusitms.jipbap.store.StoreRepositoryExtension;
import com.kusitms.jipbap.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {

    List<Food> findAllByStore(Store store);

    List<Food> findAllByCategory(Category category);
}
