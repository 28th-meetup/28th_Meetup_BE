package com.kusitms.jipbap.food;

import com.kusitms.jipbap.common.response.CommonResponse;
import com.kusitms.jipbap.food.dto.FoodDto;
import com.kusitms.jipbap.food.dto.RegisterFoodRequestDto;
import com.kusitms.jipbap.security.Auth;
import com.kusitms.jipbap.security.AuthInfo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/food")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @Operation(summary = "가게 등록하기")
    @PostMapping
    public CommonResponse<FoodDto> registerFood(@Auth AuthInfo authInfo, RegisterFoodRequestDto dto) {
        return new CommonResponse<>(foodService.registerFood(authInfo.getEmail(), dto));
    }

}
