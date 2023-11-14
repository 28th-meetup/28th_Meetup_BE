package com.kusitms.jipbap.food;

import com.kusitms.jipbap.common.response.CommonResponse;
import com.kusitms.jipbap.food.dto.CategoryDto;
import com.kusitms.jipbap.food.dto.FoodDto;
import com.kusitms.jipbap.food.dto.RegisterCategoryRequestDto;
import com.kusitms.jipbap.food.dto.RegisterFoodRequestDto;
import com.kusitms.jipbap.security.Auth;
import com.kusitms.jipbap.security.AuthInfo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
            @RequestPart(value = "image", required = false) MultipartFile image
        ) {
        return new CommonResponse<>(foodService.registerFood(authInfo.getEmail(), dto, image));
    }

    @Operation(summary = "메뉴 하나 상세조회")
    @GetMapping("/{foodId}")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponse<FoodDto> getFoodDetail(@PathVariable Long foodId) {
        return new CommonResponse<>(foodService.getFoodDetail(foodId));
    }
}
