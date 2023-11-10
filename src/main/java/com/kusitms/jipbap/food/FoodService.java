package com.kusitms.jipbap.food;

import com.kusitms.jipbap.food.dto.FoodDto;
import com.kusitms.jipbap.food.dto.RegisterFoodRequestDto;
import com.kusitms.jipbap.food.exception.CategoryNotExistsException;
import com.kusitms.jipbap.store.Store;
import com.kusitms.jipbap.store.StoreRepository;
import com.kusitms.jipbap.store.exception.StoreNotExistsException;
import com.kusitms.jipbap.user.User;
import com.kusitms.jipbap.user.UserRepository;
import com.kusitms.jipbap.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoodService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final FoodRepository foodRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public FoodDto registerFood(String email, RegisterFoodRequestDto dto) {
        userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("유저 정보가 존재하지 않습니다."));
        Store store = storeRepository.findById(dto.getStoreId()).orElseThrow(()-> new StoreNotExistsException("해당 가게 id는 유효하지 않습니다."));
        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(()-> new CategoryNotExistsException("해당 카테고리 id는 유효하지 않습니다."));

        Food food = foodRepository.save(
                new Food(null, store, category, dto.getName(), dto.getPrice(), dto.getDescription(), 0L)
        );
        return new FoodDto(food.getId(), store.getId(), category.getId(), food.getName(), food.getPrice(), food.getDescription());
    }
}
