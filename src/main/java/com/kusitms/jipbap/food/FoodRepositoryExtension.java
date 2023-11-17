package com.kusitms.jipbap.food;

import com.kusitms.jipbap.user.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FoodRepositoryExtension {
    List<Food> searchByNameOrderBySort(User user, Pageable pageable, String keyword, String standard, String order);
}
