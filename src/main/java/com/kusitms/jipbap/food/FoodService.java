package com.kusitms.jipbap.food;

import com.kusitms.jipbap.food.dto.*;
import com.amazonaws.services.s3.AmazonS3;
import com.kusitms.jipbap.common.exception.S3RegisterFailureException;
import com.kusitms.jipbap.common.utils.S3Utils;
import com.kusitms.jipbap.food.dto.CategoryDto;
import com.kusitms.jipbap.food.dto.FoodDto;
import com.kusitms.jipbap.food.dto.RegisterCategoryRequestDto;
import com.kusitms.jipbap.food.dto.RegisterFoodRequestDto;
import com.kusitms.jipbap.food.exception.CategoryNotExistsException;
import com.kusitms.jipbap.food.exception.FoodNotExistsException;
import com.kusitms.jipbap.order.OrderRepository;
import com.kusitms.jipbap.store.Store;
import com.kusitms.jipbap.store.StoreRepository;
import com.kusitms.jipbap.store.exception.StoreNotExistsException;
import com.kusitms.jipbap.user.UserRepository;
import com.kusitms.jipbap.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class FoodService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final FoodRepository foodRepository;
    private final CategoryRepository categoryRepository;
    private final OrderRepository orderRepository;

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public CategoryDto registerCategory(RegisterCategoryRequestDto dto) {
        Category category = categoryRepository.save(
                new Category(null, dto.getName(), dto.getImage())
        );
        return new CategoryDto(category.getId(), category.getName(), category.getImage());
    }

    @Transactional
    public FoodDto registerFood(String email, RegisterFoodRequestDto dto, MultipartFile image) {
        userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("유저 정보가 존재하지 않습니다."));
        Store store = storeRepository.findById(dto.getStoreId()).orElseThrow(()-> new StoreNotExistsException("해당 가게 id는 유효하지 않습니다."));
        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(()-> new CategoryNotExistsException("해당 카테고리 id는 유효하지 않습니다."));

        String imageUri = null;

        // 이미지가 null이 아닌 경우 s3 업로드
        if(image!=null) {
            try {
                imageUri = S3Utils.saveFile(amazonS3, bucket, image);
            } catch (IOException e) {
                throw new S3RegisterFailureException("메뉴 이미지 저장 중 오류가 발생했습니다.");
            }
        }

        Food food = foodRepository.save(
                new Food(null, store, category, dto.getName(), dto.getPrice(), dto.getDescription(), 0L, imageUri)
        );
        return new FoodDto(food.getId(), store.getId(), category.getId(), food.getName(), food.getPrice(), food.getDescription(), food.getImage());
    }

    public FoodDto getFoodDetail(Long foodId) {
        Food food = foodRepository.findById(foodId).orElseThrow(()-> new FoodNotExistsException("해당 음식 Id는 유효하지 않습니다."));
        return new FoodDto(food.getId(), food.getStore().getId(), food.getCategory().getId(), food.getName(), food.getPrice(), food.getDescription(), food.getImage());
    }

    public List<BestSellingFoodResponse> getBestSellingFoodByRegion(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("유저 정보가 존재하지 않습니다."));

        List<Food> bestSellingFoodsInRegionList = orderRepository.findTop10BestSellingFoodsInRegion(user.getGlobalRegion().getId());

        List<BestSellingFoodResponse> bestSellingFoodResponseList = bestSellingFoodsInRegionList.stream()
                .map(food -> new BestSellingFoodResponse(
                        food.getName(),
                        food.getStore().getName(),
                        food.getPrice()
                ))
                .collect(Collectors.toList());

        return bestSellingFoodResponseList;
    }

    public List<FoodDto> getFoodByCategory(Long categoryId){
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new CategoryNotExistsException("해당 카테고리 Id는 유효하지 않습니다."));

        List<Food> foodList = foodRepository.findAllByCategory(category);

        List<FoodDto> foodDtoList = foodList.stream()
                .map(food -> new FoodDto(
                        food.getId(),
                        food.getStore().getId(),
                        food.getCategory().getId(),
                        food.getName(),
                        food.getPrice(),
                        food.getDescription()
                ))
                .collect(Collectors.toList());

        return foodDtoList;
    }
}
