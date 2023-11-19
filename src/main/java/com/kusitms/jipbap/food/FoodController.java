package com.kusitms.jipbap.food;

import com.kusitms.jipbap.common.response.CommonResponse;
import com.kusitms.jipbap.food.dto.*;
import com.kusitms.jipbap.security.Auth;
import com.kusitms.jipbap.security.AuthInfo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/food")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @Operation(summary = "카테고리 등록하기")
    @PostMapping("/category")
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<CategoryDto> registerCategory(@RequestBody RegisterCategoryRequestDto dto) {
        return new CommonResponse<>(foodService.registerCategory(dto));
    }

    @Operation(summary = "메뉴 등록하기")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<FoodDto> registerFood(
            @Auth AuthInfo authInfo,
            @RequestPart(value = "dto") RegisterFoodRequestDto dto,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestPart(value = "informationImage", required = false) MultipartFile informationDescriptionImage
        ) {
        return new CommonResponse<>(foodService.registerFood(authInfo.getEmail(), dto, image, informationDescriptionImage));
    }

    @Operation(summary = "메뉴 하나 상세조회")
    @GetMapping("/{foodId}")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<FoodDetailResponse> getFoodDetail(@PathVariable Long foodId) {
        return new CommonResponse<>(foodService.getFoodDetail(foodId));
    }

    @Operation(summary = "메뉴 당 옵션 상세조회")
    @GetMapping("/{foodId}/option")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<List<FoodOptionResponse>> getFoodDetailByOption(@PathVariable Long foodId) {
        return new CommonResponse<>(foodService.getFoodDetailByOption(foodId));
    }


    @Operation(summary = "홈에서 데이터 조회하기")
    @GetMapping("/home")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<HomeResponseDto> getInfoFromHome(@Auth AuthInfo authInfo) {
        return new CommonResponse<>(foodService.getInfoFromHome(authInfo.getEmail()));
    }


    @Operation(summary = "특정 카테고리에 속하는 메뉴 조회하기")
    @GetMapping("/category/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<List<FoodPreviewResponse>> getFoodByCategory(@Auth AuthInfo authInfo, @PathVariable Long categoryId) {
        return new CommonResponse<>(foodService.getFoodByCategory(authInfo, categoryId));
    }
}


