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
import com.kusitms.jipbap.order.OrderDetail;
import com.kusitms.jipbap.order.OrderDetailRepository;
import com.kusitms.jipbap.order.OrderRepository;
import com.kusitms.jipbap.security.AuthInfo;
import com.kusitms.jipbap.store.Store;
import com.kusitms.jipbap.store.StoreRepository;
import com.kusitms.jipbap.store.exception.StoreNotExistsException;
import com.kusitms.jipbap.user.User;
import com.kusitms.jipbap.user.UserRepository;
import com.kusitms.jipbap.user.entity.GlobalRegion;
import com.kusitms.jipbap.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.*;
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
    private final FoodOptionRepository foodOptionRepository;
    private final OrderDetailRepository orderDetailRepository;

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
    public FoodDto registerFood(String email, RegisterFoodRequestDto dto, MultipartFile image, MultipartFile informationDescriptionImage) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("유저 정보가 존재하지 않습니다."));
        Store store = storeRepository.findByOwner(user).orElseThrow(()-> new StoreNotExistsException("해당 가게 id는 유효하지 않습니다."));
        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(()-> new CategoryNotExistsException("해당 카테고리 id는 유효하지 않습니다."));

        String imageUri = null;
        String informationDescriptionImageUri = null;

        // 이미지가 null이 아닌 경우 s3 업로드
        if(image!=null) {
            try {
                imageUri = S3Utils.saveFile(amazonS3, bucket, image);
            } catch (IOException e) {
                throw new S3RegisterFailureException("메뉴 이미지 저장 중 오류가 발생했습니다.");
            }
        }
        if(informationDescriptionImage != null){
            try {
                informationDescriptionImageUri = S3Utils.saveFile(amazonS3, bucket, informationDescriptionImage);
            } catch (IOException e) {
                throw new S3RegisterFailureException("메뉴 설명 이미지 저장 중 오류가 발생했습니다.");
            }
        }

        Food food = foodRepository.save(
                Food.builder()
                        .store(store)
                        .category(category)
                        .name(dto.getName())
                        .dollarPrice(dto.getDollarPrice())
                        .canadaPrice(dto.getCanadaPrice())
                        .image(imageUri)
                        .description(dto.getDescription())
                        .foodPackage(dto.getFoodPackage())
                        .informationDescription(informationDescriptionImageUri)
                        .ingredient(dto.getIngredient())
                        .recommendCount(0L)
                        .build()
        );

        // FoodOption 저장
        if (dto.getFoodOptionRequestList() != null && !dto.getFoodOptionRequestList().isEmpty()) {
            for (FoodOptionRequest foodOptionRequest : dto.getFoodOptionRequestList()) {
                FoodOption foodOption = FoodOption.builder()
                        .food(food)
                        .name(foodOptionRequest.getName())
                        .dollarPrice(foodOptionRequest.getDollarPrice())
                        .canadaPrice(foodOptionRequest.getCanadaPrice())
                        .build();

                foodOptionRepository.save(foodOption);
            }
        }

         return new FoodDto(food.getId(), store.getId(), category.getId(), food.getName(), food.getDollarPrice(), food.getCanadaPrice(), food.getDescription(), food.getImage());
    }

    public FoodDetailResponse getFoodDetail(Long foodId) {
        Food food = foodRepository.findById(foodId).orElseThrow(()-> new FoodNotExistsException("해당 음식 Id는 유효하지 않습니다."));
        List<FoodOptionResponse> foodOptionResponseList = foodOptionRepository.findAllByFood(food).stream()
                .map(foodOption -> new FoodOptionResponse(foodOption.getId(), foodOption.getName(), foodOption.getDollarPrice(), foodOption.getCanadaPrice()))
                .collect(Collectors.toList());

        return FoodDetailResponse.builder()
                .id(food.getId())
                .storeId(food.getStore().getId())
                .storeName(food.getStore().getName())
                .categoryId(food.getCategory().getId())
                .name(food.getName())
                .dollarPrice(food.getDollarPrice())
                .canadaPrice(food.getCanadaPrice())
                .image(food.getImage())
                .description(food.getDescription())
                .foodPackage(food.getFoodPackage())
                .informationDescription(food.getInformationDescription())
                .ingredient(food.getIngredient())
                .minOrderAmount(food.getStore().getMinOrderAmount())
                .foodOptionResponseList(foodOptionResponseList)
                .build();
    }

    public List<FoodOptionResponse> getFoodDetailByOption(Long foodId) {
        Food food = foodRepository.findById(foodId).orElseThrow(()-> new FoodNotExistsException("해당 음식 Id는 유효하지 않습니다."));
        List<FoodOptionResponse> foodOptionResponseList = foodOptionRepository.findAllByFood(food).stream()
                .map(foodOption -> new FoodOptionResponse(foodOption.getId(), foodOption.getName(), foodOption.getDollarPrice(), foodOption.getCanadaPrice()))
                .collect(Collectors.toList());
        return foodOptionResponseList;
    }

    public HomeResponseDto getInfoFromHome(String email){
        User user = userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("유저 정보가 존재하지 않습니다."));

        Long globalRegionId = user.getGlobalRegion().getId();
        List<FoodPreviewResponse> bestSellingFoodResponseList = getBestSellingFoodByRegion(globalRegionId);
        List<FoodPreviewResponse> latestSellingFoodResponseList = getLatestSellingFoodByRegion(globalRegionId);

        return new HomeResponseDto(globalRegionId, bestSellingFoodResponseList,latestSellingFoodResponseList);
    }

    private List<FoodPreviewResponse> getBestSellingFoodByRegion(Long globalRegionId){
        List<OrderDetail> orderDetails = orderDetailRepository.findTop10ByOrder_Store_GlobalRegion_IdOrderByOrderCountDesc(globalRegionId);

        Map<Food, Long> foodSalesMap = orderDetails.stream()
                .collect(Collectors.groupingBy(OrderDetail::getFood, Collectors.summingLong(OrderDetail::getOrderCount)));

        List<Map.Entry<Food, Long>> sortedFoodSales = new ArrayList<>(foodSalesMap.entrySet());
        sortedFoodSales.sort((entry1, entry2) -> Long.compare(entry2.getValue(), entry1.getValue()));

        List<Food> top10FoodList = sortedFoodSales.stream()
                .limit(10)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<FoodPreviewResponse> bestSellingFoodResponseList = top10FoodList.stream()
        .map(food -> new FoodPreviewResponse (
                food.getId(),
                food.getName(),
                food.getStore().getId(),
                food.getStore().getName(),
                food.getDollarPrice(),
                food.getCanadaPrice(),
                food.getImage(),
                food.getStore().getAvgRate()
        ))
        .collect(Collectors.toList());

//        List<Food> bestSellingFoodsInRegionList = orderRepository.findTop10BestSellingFoodsInRegion(globalRegionId);
//
//        List<FoodPreviewResponse> bestSellingFoodResponseList = bestSellingFoodsInRegionList.stream()
//                .map(food -> new FoodPreviewResponse (
//                        food.getId(),
//                        food.getName(),
//                        food.getStore().getId(),
//                        food.getStore().getName(),
//                        food.getDollarPrice(),
//                        food.getCanadaPrice(),
//                        food.getImage(),
//                        food.getStore().getAvgRate()
//                ))
//                .collect(Collectors.toList());
//
        return bestSellingFoodResponseList;
    }

    private List<FoodPreviewResponse> getLatestSellingFoodByRegion(Long globalRegionId){
        List<OrderDetail> orderDetails = orderDetailRepository.findTop4ByOrder_Store_GlobalRegion_IdOrderByOrder_CreatedAtDesc(globalRegionId);

        Map<Long, List<OrderDetail>> orderDetailsByRegion = orderDetails.stream()
                .collect(Collectors.groupingBy(orderDetail -> orderDetail.getOrder().getStore().getGlobalRegion().getId()));

        List<FoodPreviewResponse> latestSellingFoodResponseList = orderDetailsByRegion.values().stream()
                .flatMap(regionOrderDetails -> regionOrderDetails.stream())
                .map(OrderDetail::getFood)
                .distinct()
                .limit(4)
                .map(food -> new FoodPreviewResponse(
                        food.getId(),
                        food.getName(),
                        food.getStore().getId(),
                        food.getStore().getName(),
                        food.getDollarPrice(),
                        food.getCanadaPrice(),
                        food.getImage(),
                        food.getStore().getAvgRate()
                ))
                .collect(Collectors.toList());
        return latestSellingFoodResponseList;

//        List<Food> latestFoodsInRegionList = orderRepository.findLatestFoodsByRegionId(globalRegionId);
//
//        List<FoodPreviewResponse> latestSellingFoodResponseList = latestFoodsInRegionList.stream()
//                .map(food -> new FoodPreviewResponse(
//                        food.getId(),
//                        food.getName(),
//                        food.getStore().getId(),
//                        food.getStore().getName(),
//                        food.getDollarPrice(),
//                        food.getCanadaPrice(),
//                        food.getImage(),
//                        food.getStore().getAvgRate()
//                ))
//                .collect(Collectors.toList());
//
//        return latestSellingFoodResponseList;
    }

    public List<FoodPreviewResponse> getFoodByCategory(AuthInfo authInfo, Long categoryId, SortingType sortingType){
        User user = userRepository.findByEmail(authInfo.getEmail())
                .orElseThrow(()-> new UserNotFoundException("유저 정보가 존재하지 않습니다."));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new CategoryNotExistsException("해당 카테고리 Id는 유효하지 않습니다."));

        GlobalRegion globalRegion = user.getGlobalRegion();

        List<Food> foodList = foodRepository.findByStoreGlobalRegionAndCategory(globalRegion, category);

        System.out.println("sortingType" + sortingType);
        foodList = sortFoodList(foodList, sortingType);

        List<FoodPreviewResponse> foodDtoList = foodList.stream()
                .map(food -> new FoodPreviewResponse(
                        food.getId(),
                        food.getName(),
                        food.getStore().getId(),
                        food.getStore().getName(),
                        food.getDollarPrice(),
                        food.getCanadaPrice(),
                        food.getImage(),
                        food.getStore().getAvgRate()
                ))
                .collect(Collectors.toList());

        return foodDtoList;
    }

    private List<Food> sortFoodList(List<Food> foodList, SortingType sortingType) {
        switch (sortingType) {
            case REVIEW_HIGH:
                foodList.sort(Comparator.comparingLong(food -> -food.getStore().getReviewCount()));
                break;
            case RATING_HIGH:
                foodList.sort(Comparator.comparingDouble(food -> -food.getStore().getAvgRate()));
                break;
            case PRICE_HIGH:
                foodList.sort(Comparator.comparingLong(Food::getDollarPrice).reversed());
                break;
            case PRICE_LOW:
                foodList.sort(Comparator.comparingLong(Food::getDollarPrice));
                break;
            case RECENTLY_ADDED:
                foodList.sort(Comparator.comparing(Food::getCreatedAt).reversed());
                break;
            default:
                // 기본은 추천 순으로 정렬
                // 추천 순으로 정렬할 기준이 없다면 아무 처리도 하지 않습니다.
                foodList.sort(Comparator.comparingLong(Food::getRecommendCount).reversed());
                break;
        }
        return foodList;
    }

}
